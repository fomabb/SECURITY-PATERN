package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.dto.UserDetailsDto;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDetailsDto toDetailsDto(User user);
}
