package com.reconnect.web.utils.example.mappers;

import com.reconnect.web.utils.example.dto.UserDto;
import com.reconnect.web.utils.example.models.User;
import com.reconnect.web.utils.mapper.Mapper;
import com.reconnect.web.utils.mapper.MapperComponent;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
@MapperComponent(ignoreToEntity = {"id", "username"}, ignoreToDto = "password")
public class UserMapper implements Mapper<UserDto, User> {
}
