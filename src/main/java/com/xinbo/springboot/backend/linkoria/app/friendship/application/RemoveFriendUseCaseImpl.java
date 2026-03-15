package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.RemoveFriendUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipStatusException;
import org.springframework.stereotype.Service;

@Service
public class RemoveFriendUseCaseImpl implements RemoveFriendUseCase {

    private final FriendshipRepository friendshipRepository;

    public RemoveFriendUseCaseImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship remove(RemoveCommand command) {
        Friendship friendship = friendshipRepository.findBySenderReceiverId(command.requesterId(), command.targetId())
                .or(() -> friendshipRepository.findBySenderReceiverId(command.requesterId(), command.targetId()))
                .orElseThrow(() -> new FriendshipNotFoundException("No friend request found"));

        if(!(friendship.getStatus() == FriendshipStatus.ACCEPTED || friendship.getStatus() == FriendshipStatus.BLOCKED)) {
            throw new FriendshipStatusException("The status in this friendship must be \"ACCEPTED\" or \"BLOCKED\"");
        }

        Friendship removedFriendship = friendship.remove();

        return friendshipRepository.save(removedFriendship);
    }
}
