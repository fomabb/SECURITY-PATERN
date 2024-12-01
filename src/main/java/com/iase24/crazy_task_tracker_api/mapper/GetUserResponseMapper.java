package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.dto.response.GetUserResponse;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import org.springframework.stereotype.Component;

@Component
public class GetUserResponseMapper {

    public GetUserResponse responseMapperInfo(User user) {
        return GetUserResponse.builder()
                .uuid(user.getId())
                .firstName(user.getFirstName())
                .email(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().toString())
//                .dateTime(user.getDateCreateUser())
                .build();
    }
}
