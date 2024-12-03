package com.iase24.crazy_task_tracker_api.adminapi.service.impl;

import com.iase24.crazy_task_tracker_api.entity.ArchiveDeletedUser;
import com.iase24.crazy_task_tracker_api.repository.ArchiveDeletedUserRepository;
import com.iase24.crazy_task_tracker_api.security.repository.UserRepository;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.adminapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

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
}
