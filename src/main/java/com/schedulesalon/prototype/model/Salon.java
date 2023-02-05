package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Salon {

    @Id
    @SequenceGenerator(name = "seq_salon", sequenceName = "seq_salon")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_salon")
    private Long id;

    private String name;

    private String description;

    private String address;

    @ManyToOne
    @JoinColumn(name = "salon_type_id")
    private SalonType salonType;

    @OneToMany(mappedBy = "salon")
    private List<Professional> professionals;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private  Manager manager;
}
