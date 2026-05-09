package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.GetFriendshipsUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out.FriendshipUserDataPort;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<Friendship> friendships = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
        List<GetFriendshipResponse> friendshipResponseList = new ArrayList<>();
        friendships.forEach(friendship -> {
            UUID friendId = userId.equals(friendship.getSenderId())? friendship.getReceiverId() : friendship.getSenderId();
            FriendshipUserDataPort.FriendUserData friendUserData = friendshipUserDataPort.getUserData(friendId);
            friendshipResponseList.add(GetFriendshipResponse.from(friendship, friendId, friendUserData.username(), friendUserData.avatarUrl()));
        });
        return friendshipResponseList;
    }

    @Override
    public List<GetFriendshipResponse> getPendingReceived(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream().filter(fs -> fs.getReceiverId().equals(userId)).toList();
    }

    @Override
    public List<GetFriendshipResponse> getPendingSent(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream().filter(fs -> fs.getSenderId().equals(userId)).toList();
    }
}
