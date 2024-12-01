package com.iase24.crazy_task_tracker_api.adminapi.service;

import com.iase24.crazy_task_tracker_api.entity.ArchiveDeletedUser;
import com.iase24.crazy_task_tracker_api.security.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUser();

    User getUserById(UUID uuid);

    void deleteByUserId(UUID uuid);

    void addToArchiveDeleted(ArchiveDeletedUser archiveDeletedUser);

    List<ArchiveDeletedUser> getAllArchives();

    List<ArchiveDeletedUser> getMessageBySearch(String text);
}
