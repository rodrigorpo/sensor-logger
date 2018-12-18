package com.br.rodrigo.pereira.sensorlogger.model.domain.mappers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.*;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserCreateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserDeleteRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserIdentityUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.requests.UserUpdateRequest;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    List<UserResponse> userListToUserResponseList(List<User> userList);

    UserResponse userToUserResponse(User user);

    UserCreateData userCreateRequestToUserCreateData(UserCreateRequest userCreateRequest);

    UserDeleteData userDeleteRequestToUserDeleteData(UserDeleteRequest userDeleteRequest);

    default UserUpdateData userUpdateDataFromUserUpdate(UserUpdateRequest userUpdateRequest, String username) {
        return UserUpdateData.builder()
                .name(userUpdateRequest.getName())
                .course(userUpdateRequest.getCourse())
                .birthday(userUpdateRequest.getBirthday())
                .username(username)
                .password(userUpdateRequest.getPassword()).build();
    }

    default UserIdentityUpdateData userIdentityUpdateRequestToUserIdentityUpdateData
            (UserIdentityUpdateRequest userIdentityUpdateRequest, String oldUsername) {
        return UserIdentityUpdateData.builder()
                .oldUsername(oldUsername)
                .newUsername(userIdentityUpdateRequest.getNewUsername())
                .oldPassword(userIdentityUpdateRequest.getOldPassword())
                .newPassword(userIdentityUpdateRequest.getNewPassword())
                .userStatus(userIdentityUpdateRequest.getUserStatus()).build();
    }

    default UserMinimalData userToUserMinimalData(User user) {
        return UserMinimalData.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .username(user.getUsername())
                .privileges(user.getPrivileges()).build();
    }
}