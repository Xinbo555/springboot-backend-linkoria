package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.GetFriendshipsUseCase;
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

    public GetFriendshipsUseCaseImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public List<Friendship> getFriends(UUID userId) {
        List<Friendship> allAcepted = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
        List<Friendship> blockedByOthers = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.BLOCKED)
                .stream().filter(fs -> !fs.getBlockedBy().equals(userId)).toList();

        List<Friendship> allFriends = new ArrayList<>(allAcepted);
        allFriends.addAll(blockedByOthers);

        return allFriends;
    }

    @Override
    public List<Friendship> getPendingReceived(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream().filter(fs -> fs.getReceiverId().equals(userId)).toList();
    }

    @Override
    public List<Friendship> getPendingSent(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream().filter(fs -> fs.getSenderId().equals(userId)).toList();
    }

    @Override
    public List<Friendship> getBlockedByMe(UUID userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.BLOCKED)
                .stream().filter(fs -> fs.getBlockedBy().equals(userId)).toList();
    }
}
