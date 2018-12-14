package com.br.rodrigo.pereira.sensorlogger.model.domain.mappers;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import com.br.rodrigo.pereira.sensorlogger.model.domain.responses.UserResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    List<UserResponse> userListToUserResponseList(List<User> userList);

    UserResponse userToUserResponse(User user);
}
