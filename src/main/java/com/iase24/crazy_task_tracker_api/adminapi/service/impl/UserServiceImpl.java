package com.iase24.crazy_task_tracker_api.adminapi.service.impl;

import com.iase24.crazy_task_tracker_api.adminapi.service.UserService;
import com.iase24.crazy_task_tracker_api.dto.UserDetailsDto;
import com.iase24.crazy_task_tracker_api.entity.ArchiveDeletedUser;
import com.iase24.crazy_task_tracker_api.mapper.UserMapper;
import com.iase24.crazy_task_tracker_api.repository.ArchiveDeletedUserRepository;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ArchiveDeletedUserRepository archiveRepository;


    @Override
    public User getUserById(UUID uuid) {
        log.info("Попытка получить сведения с базы данных о пользователе с Id: {}", uuid);
        return userRepository.findById(uuid)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID: {} не найден", uuid);
                    return new EntityNotFoundException("User with id: %s not found".formatted(uuid));
                });
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID uuid) {
        User userId = getUserById(uuid);
        userRepository.delete(userId);
    }

    @Override
    @Transactional
    public void addToArchiveDeleted(ArchiveDeletedUser archiveDeletedUser) {
        ArchiveDeletedUser archiveSaveData = ArchiveDeletedUser.builder()
                .message(archiveDeletedUser.getMessage())
                .addDateTimeToArchive(archiveDeletedUser.getAddDateTimeToArchive())
                .build();
        archiveRepository.save(archiveSaveData);
    }


    @Override
    public List<ArchiveDeletedUser> getAllArchives() {
        return archiveRepository.findAll();
    }

    @Override
    public List<ArchiveDeletedUser> getMessageBySearch(String text) {
        return archiveRepository.findMessageBySearch(text);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAllUserByRoleEmployeeAndRoleUser();
    }

    /**
     * Эффективно получает данные для нескольких пользователей за один вызов.
     * <p>
     * Этот метод выполняет один запрос к базе данных для получения всех пользователей
     * по списку их ID, избегая проблемы "N+1 запроса". Затем он преобразует
     * найденные сущности в DTO и возвращает их в виде карты для удобного доступа.
     *
     * @param userIds Набор ID пользователей, для которых нужно получить информацию.
     * @return Карта (Map), где ключ - это ID пользователя, а значение - его DTO.
     * Если какой-то ID из входного набора не найден в БД, он просто
     * не будет включен в итоговую карту.
     */
    @Override
    public Map<UUID, UserDetailsDto> getUsersByIds(Set<UUID> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyMap();
        }

        return userRepository.findAllById(userIds)
                .stream()
                .collect(Collectors.toMap(
                        User::getId,
                        userMapper::toDetailsDto
                ));
    }
}
