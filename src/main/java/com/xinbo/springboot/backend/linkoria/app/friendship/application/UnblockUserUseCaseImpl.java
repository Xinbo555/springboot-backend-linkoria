package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.BlockUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.UnblockUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipStatusException;
import org.springframework.stereotype.Service;

@Service
public class UnblockUserUseCaseImpl implements UnblockUserUseCase {
    private final FriendshipRepository friendshipRepository;

    public UnblockUserUseCaseImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship unblock(UnblockCommand command) {
        Friendship friendship = friendshipRepository.findBySenderReceiverId(command.requesterId(),command.targetId())
                .or(() -> friendshipRepository.findBySenderReceiverId(command.targetId(),command.requesterId()))
                .orElseThrow(() -> new FriendshipNotFoundException("No friend request found"));

        if(friendship.getStatus() != FriendshipStatus.BLOCKED) {
            throw new FriendshipStatusException("The status in this friendship must be \"BLOCKED\"");
        }

        Friendship unblockedFriendship = friendship.unblock();
        return friendshipRepository.save(unblockedFriendship);
    }
}
