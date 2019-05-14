package com.tomasdutkus.movierental.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Movie extends BaseEntity {

    private static final long serialVersionUID = -1773347496688821996L;

    @Column(name = "title", unique = true)
    @NotNull
    @Size(min = MIN_LENGTH, max = DEFAULT_STRING_MAX_LENGTH)
    private String name;

    @Column(name = "movieType")
    private MovieTypes movieType;

}