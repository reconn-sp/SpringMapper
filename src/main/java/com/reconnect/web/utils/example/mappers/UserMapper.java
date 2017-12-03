package com.reconnect.web.utils.example.mappers;

import com.reconnect.web.utils.example.dto.UserDto;
import com.reconnect.web.utils.example.models.User;
import com.reconnect.web.utils.mapper.IgnorableMapper;
import com.reconnect.web.utils.mapper.annotation.Mapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
@Component
@Mapper(target = UserDto.class)
public class UserMapper implements IgnorableMapper<UserDto, User> {

    private final List<String> ignored = Arrays.asList("id", "password", "username");

    @Override
    public List<String> getIgnored() {
        return ignored;
    }
}
