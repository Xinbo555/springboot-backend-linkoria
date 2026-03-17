package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.DeclineFriendRequestUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipStatusException;
import org.springframework.stereotype.Service;

@Service
public class DeclineFriendRequestUseCaseImpl implements DeclineFriendRequestUseCase {

    private final FriendshipRepository friendshipRepository;

    public DeclineFriendRequestUseCaseImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship decline(DeclineCommand command) {
        Friendship friendship = friendshipRepository.findBySenderReceiverId(command.senderId(), command.receiverId())
                .orElseThrow(() -> new FriendshipNotFoundException("No friend request found"));

        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new FriendshipStatusException("The status in this friendship is not \"PENDING\"");
        }

        Friendship declinedFriendShip = friendship.decline();

        return friendshipRepository.save(declinedFriendShip);
    }
}
