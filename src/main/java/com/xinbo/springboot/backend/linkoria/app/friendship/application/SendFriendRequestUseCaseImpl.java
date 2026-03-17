package com.xinbo.springboot.backend.linkoria.app.friendship.application;

import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.SendFriendRequestUseCase;
import com.xinbo.springboot.backend.linkoria.app.shared.user.UserValidationPort;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipRepository;
import com.xinbo.springboot.backend.linkoria.app.friendship.domain.FriendshipStatus;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipAlreadyExistsException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.ReceiverIdNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        if (!userValidationPort.existsById(command.receiverId())) {
            throw new ReceiverIdNotFoundException("User Id not found");
        }

        if (command.senderId().equals(command.receiverId())) {
            throw new FriendshipAlreadyExistsException("You can not send a request to your self");
        }

        if (friendshipRepository.existsByUsersAndStatusIn(command.senderId(), command.receiverId(),
                List.of(FriendshipStatus.PENDING, FriendshipStatus.ACCEPTED))) {
            throw new FriendshipAlreadyExistsException("There is a friendship between these users");
        }

        return friendshipRepository.save(
                friendshipRepository.findBySenderReceiverId(command.senderId(), command.receiverId())
                        .or(() -> friendshipRepository.findBySenderReceiverId(command.receiverId(), command.senderId()))
                        .map(existing -> Friendship.reconstitute(
                                existing.getId(),
                                existing.getSenderId(),
                                existing.getReceiverId(),
                                FriendshipStatus.PENDING,
                                existing.getCreatedAt(),
                                Instant.now()
                        ))
                        .orElse(Friendship.create(command.senderId(), command.receiverId()))
        );
    }
}
