package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.dto.response.GetUserResponse;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import com.iase24.crazy_task_tracker_api.security.entity.numentity.Role;
import org.springframework.stereotype.Component;

@Component
public class GetUserResponseMapper {

    public GetUserResponse responseMapperInfo(User user) {
        GetUserResponse getUserResponse = new GetUserResponse();

        if (user.getRole().equals(Role.ROLE_USER)) {
            getUserResponse = GetUserResponse.builder()
                    .uuid(user.getId())
                    .firstName(user.getFirstName())
                    .email(user.getUsername())
                    .password(user.getPassword())
                    .role(user.getRole().toString())
                    .createDateTimeUser(user.getDateCreateUser().toLocalDateTime())
                    .build();
        } else if (user.getRole().equals(Role.ROLE_EMPLOYEE)) {
            getUserResponse = GetUserResponse.builder()
                    .uuid(user.getId())
                    .firstName(user.getFirstName())
                    .email(user.getWorkEmail())
                    .password(user.getPassword())
                    .role(user.getRole().toString())
                    .createDateTimeUser(user.getDateCreateUser().toLocalDateTime())
                    .build();
        }
        return getUserResponse;
    }
}
