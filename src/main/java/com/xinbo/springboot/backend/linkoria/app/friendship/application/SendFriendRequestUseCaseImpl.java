package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.SendFriendRequestUseCase;
import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.out.UserValidationPort;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipAlreadyExistsException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.ReceiverIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendFriendRequestUseCaseImpl implements SendFriendRequestUseCase {
    private final FriendshipRepository friendshipRepository;
    private final UserValidationPort userValidationPort;

    public SendFriendRequestUseCaseImpl(FriendshipRepository friendshipRepository, UserValidationPort userValidationPort) {
        this.friendshipRepository = friendshipRepository;
        this.userValidationPort = userValidationPort;
    }

    @Override
    public Friendship send(SendCommand command) {
        if (!userValidationPort.existsById(command.receiverId())){
            throw new ReceiverIdNotFoundException("User Id not found");
        }

        if(friendshipRepository.existsByUsersAndStatusIn(command.senderId(),command.receiverId(),
                List.of(FriendshipStatus.PENDING, FriendshipStatus.ACCEPTED, FriendshipStatus.BLOCKED))) {
            throw new FriendshipAlreadyExistsException("There is a friendship between these users");
        }

        return friendshipRepository.save(Friendship.create(command.senderId(),command.receiverId()));
    }
}
