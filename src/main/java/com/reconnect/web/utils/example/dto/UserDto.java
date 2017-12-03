package com.reconnect.web.utils.example.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements BaseDto {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String title;

    private boolean avatarUploaded;

    private LocalDate birth;

    private MultipartFile avatar;
}
