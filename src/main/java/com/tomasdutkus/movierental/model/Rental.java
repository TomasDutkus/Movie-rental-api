package com.tomasdutkus.movierental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Rental")
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rental extends BaseEntity {

    private static final long serialVersionUID = 5215493394633887909L;

    @Column(name = "userId")
    @NotNull
    private Long userId;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalItem> rentalItems = new ArrayList<>();

    @NotNull
    @Column(name = "price")
    private Double price;

    @Column(name = "surcharge")
    private Double surcharge;

    @Column(name = "renteddate")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime renteddate;

    @Column(name = "isOrderReturned")
    private boolean isOrderReturned;

    public void setRentalItems(List<RentalItem> rentalItems) {
        // initializing the object instance in Children class (Owner side)
        // so that it is not a null and PK can be created
        rentalItems.forEach(item -> {
            item.setRental(this);
        });

        this.rentalItems = rentalItems;
    }
}
