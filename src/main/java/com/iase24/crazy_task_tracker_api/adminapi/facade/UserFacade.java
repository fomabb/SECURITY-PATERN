package com.iase24.crazy_task_tracker_api.adminapi.facade;

import com.iase24.crazy_task_tracker_api.adminapi.service.UserService;
import com.iase24.crazy_task_tracker_api.dto.response.DeleteUserResponse;
import com.iase24.crazy_task_tracker_api.dto.response.GetUserResponse;
import com.iase24.crazy_task_tracker_api.entity.ArchiveDeletedUser;
import com.iase24.crazy_task_tracker_api.mapper.GetUserResponseMapper;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.entity.numentity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final GetUserResponseMapper userResponseMapper;

    public List<GetUserResponse> getAllUser() {
        log.info("Попытка вывести список пользователей");
        return userService.getAllUser().stream()
                .map(userResponseMapper::responseMapperInfo).toList();
    }

    public GetUserResponse getUserById(UUID uuid) {
        log.info("Попытка вызвать пользователя с ID: {}", uuid);
        User userId = userService.getUserById(uuid);
        log.info("Пользователь по имени {}, с ID: {}", userId.getUsername(), userId.getId());
        if (userId.getRole().equals(Role.ROLE_ADMIN)) {
            return new GetUserResponse(
                    userId.getId(), userId.getFirstName(), userId.getWorkEmail(), userId.getPassword(), userId.getRole().toString());
        } else {
            return new GetUserResponse(
                    userId.getId(), userId.getFirstName(), userId.getUsername(), userId.getPassword(), userId.getRole().toString());
        }
    }

    public DeleteUserResponse deleteByUserId(UUID uuid) {
        log.info("Начало работы с пользователем с ID: {}", uuid);
        User user = userService.getUserById(uuid);
        String messageAfterDelete = "Пользователь по имени: ===%s=== с идентификационным номером: *%s* был удален |%s|"
                .formatted(user.getUsername(), uuid, LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        ArchiveDeletedUser message = ArchiveDeletedUser.builder()
                .message(messageAfterDelete)
                .addDateTimeToArchive(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .build();
        log.info("Попытка добавить message в архив об пользователе с ID: {} по имени {}", user.getId(), user.getUsername());
        userService.addToArchiveDeleted(message);
        log.info("Попытка удалить пользователя с ID: {}", user.getId());
        userService.deleteByUserId(user.getId());
        log.info("Пользователь по имени {} с ID: {} был удален", user.getUsername(), user.getId());
        return new DeleteUserResponse(uuid, message.getMessage());
    }
}
