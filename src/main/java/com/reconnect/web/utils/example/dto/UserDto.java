package com.reconnect.web.utils.example.dto;

import com.reconnect.web.utils.mapper.MappedDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * @author s.vareyko
 * @since 03.12.17
 */
public class UserDto implements MappedDto {

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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public boolean isAvatarUploaded() {
        return avatarUploaded;
    }

    public void setAvatarUploaded(final boolean avatarUploaded) {
        this.avatarUploaded = avatarUploaded;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(final LocalDate birth) {
        this.birth = birth;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(final MultipartFile avatar) {
        this.avatar = avatar;
    }
}
