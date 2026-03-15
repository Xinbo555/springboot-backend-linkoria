package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.BlockUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipStatusException;
import org.springframework.stereotype.Service;

@Service
public class BlockUserUseCaseImpl implements BlockUserUseCase {
    private final FriendshipRepository friendshipRepository;

    public BlockUserUseCaseImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship block(BlockCommand command) {
        Friendship friendship = friendshipRepository.findBySenderReceiverId(command.requesterId(), command.targetId())
                .or(() -> friendshipRepository.findBySenderReceiverId(command.targetId(), command.requesterId()))
                .orElseThrow(() -> new FriendshipNotFoundException("No friend request found"));

        //ignorar silenciosamente
        if (friendship.getStatus() == FriendshipStatus.BLOCKED) {
            return friendship;
        }

        if (!(friendship.getStatus() == FriendshipStatus.ACCEPTED)) {
            throw new FriendshipStatusException("The status in this friendship must be \"ACCEPTED\"");
        }

        Friendship blockedFriendship = friendship.block(command.requesterId());
        return friendshipRepository.save(blockedFriendship);
    }
}
