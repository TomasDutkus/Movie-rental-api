package com.tomasdutkus.movierental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "RentalItem")
@Table(name = "rental_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RentalItem extends BaseEntity {

    private static final long serialVersionUID = 8312336935414115585L;

    @Column(name = "movieId")
    @NotNull
    private Long movieId;

    @Column(name = "bookingdate")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate bookingdate;

    @Column(name = "returningDate")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returningDate;

    @Column(name = "actualReturnedDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate actualReturnedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Rental rental;

}
