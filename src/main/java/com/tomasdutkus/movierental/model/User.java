package com.tomasdutkus.movierental.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "unique_user_email_idx")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    private static final long serialVersionUID = 2094189998367926553L;

    @Column(name = "name", unique = true)
    @NotNull
    @Size(min = MIN_LENGTH, max = DEFAULT_STRING_MAX_LENGTH)
    private String name;

    @Column(name = "email", unique = true)
    @NotNull
    @Size(min = MIN_LENGTH, max = DEFAULT_STRING_MAX_LENGTH)
    private String email;

    @Column(name = "password", length = DEFAULT_PASSWORD_LENGTH)
    @NotNull
    @Size(min = MIN_LENGTH, max = DEFAULT_PASSWORD_LENGTH)
    private String password;

    @Column(name = "phone_number", length = DEFAULT_PHONE_LENGTH)
    @NotNull
    @Size(min = MIN_LENGTH, max = DEFAULT_PHONE_LENGTH)
    private String phoneNumber = null;

    @Column(name = "enabled")
    @NotNull
    private Boolean enabled;

}
