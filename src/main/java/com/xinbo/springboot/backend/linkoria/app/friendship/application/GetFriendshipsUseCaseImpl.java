package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.GetFriendshipsUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out.FriendshipUserDataPort;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GetFriendshipsUseCaseImpl implements GetFriendshipsUseCase {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipUserDataPort friendshipUserDataPort;

    public GetFriendshipsUseCaseImpl(FriendshipRepository friendshipRepository, FriendshipUserDataPort friendshipUserDataPort) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipUserDataPort = friendshipUserDataPort;
    }

    @Override
    public List<GetFriendshipResponse> getFriends(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED)
                .stream()
                .map(friendship -> {

                    UUID friendId = userId.equals(friendship.getSenderId())
                            ? friendship.getReceiverId()
                            : friendship.getSenderId();

                    FriendshipUserDataPort.FriendUserData friendUserData =
                            friendshipUserDataPort.getUserData(friendId);

                    return GetFriendshipResponse.from(
                            friendship,
                            friendId,
                            friendUserData.username(),
                            friendUserData.avatarUrl()
                    );
                })
                .toList();
    }

    @Override
    public List<GetFriendshipResponse> getFriendships(UUID userId) {
        List<Friendship> pendings = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING);
        List<Friendship> accepted = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
        List<Friendship> declined = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.DECLINED);
        List<Friendship> removed = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.REMOVED);

        return Stream.of(pendings, accepted, declined, removed)
                .flatMap(Collection::stream)
                .map(friendship -> {

                    UUID friendId = userId.equals(friendship.getSenderId())
                            ? friendship.getReceiverId()
                            : friendship.getSenderId();

                    FriendshipUserDataPort.FriendUserData friendUserData =
                            friendshipUserDataPort.getUserData(friendId);

                    return GetFriendshipResponse.from(
                            friendship,
                            friendId,
                            friendUserData.username(),
                            friendUserData.avatarUrl()
                    );
                })
                .toList();
    }

    @Override
    public List<GetFriendshipResponse> getPendingReceived(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream()
                .filter(fs -> fs.getReceiverId().equals(userId))
                .map(friendship -> {

                    UUID friendId = friendship.getSenderId();

                    FriendshipUserDataPort.FriendUserData friendUserData =
                            friendshipUserDataPort.getUserData(friendId);

                    return GetFriendshipResponse.from(
                            friendship,
                            friendId,
                            friendUserData.username(),
                            friendUserData.avatarUrl()
                    );
                })
                .toList();
    }

    @Override
    public List<GetFriendshipResponse> getPendingSent(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream()
                .filter(fs -> fs.getSenderId().equals(userId))
                .map(friendship -> {

                    UUID friendId = friendship.getReceiverId();

                    FriendshipUserDataPort.FriendUserData friendUserData =
                            friendshipUserDataPort.getUserData(friendId);

                    return GetFriendshipResponse.from(
                            friendship,
                            friendId,
                            friendUserData.username(),
                            friendUserData.avatarUrl()
                    );
                })
                .toList();
    }
}
