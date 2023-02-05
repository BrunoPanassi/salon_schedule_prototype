package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalonType {

    @Id
    @SequenceGenerator(name = "seq_salon_type", sequenceName = "seq_salon_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_salon_type")
    private Long id;

    private String description;

    @OneToMany(mappedBy = "salonType")
    private List<Salon> salons;
}
