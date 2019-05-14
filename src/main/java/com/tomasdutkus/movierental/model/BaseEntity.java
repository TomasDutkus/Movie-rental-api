package com.tomasdutkus.movierental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2092223790276246143L;

    // Default string length
    public static final int DEFAULT_STRING_MAX_LENGTH = 255;

    // Default min length
    public static final int MIN_LENGTH = 1;

    // Default password length
    public static final int DEFAULT_PASSWORD_LENGTH = 16;

    // Default phone length
    public static final int DEFAULT_PHONE_LENGTH = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    protected Long id;

}
