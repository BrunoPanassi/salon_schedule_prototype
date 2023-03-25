package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
public class SalonType {

    @Id
    @SequenceGenerator(name = "seq_salon_type", sequenceName = "seq_salon_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_salon_type")
    private Long id;

    private String description;

    @OneToMany(mappedBy = "salonType")
    private List<Salon> salons;

    @ManyToMany
    private Collection<Job> job = new ArrayList<>();

    public SalonType(String description) {
        this.description = description;
    }
}
