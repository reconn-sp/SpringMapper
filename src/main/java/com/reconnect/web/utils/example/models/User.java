package com.reconnect.web.utils.example.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * This entity contains information about user and their role.
 *
 * @author s.vareyko
 * @since 03.12.17
 */
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "USERNAME"),
                @UniqueConstraint(columnNames = "EMAIL")
        },
        indexes = {
                @Index(columnList = "USERNAME"),
                @Index(columnList = "EMAIL")
        })
public class User implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BIRTH")
    private LocalDate birth;

    @Column(name = "AVATAR")
    private boolean avatarUploaded;
}