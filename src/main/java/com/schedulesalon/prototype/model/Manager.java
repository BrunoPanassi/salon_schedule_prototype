package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@RequiredArgsConstructor
public class Manager {

    @Id
    @SequenceGenerator(name = "seq_manager", sequenceName = "seq_manager")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_manager")
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "manager")
    private List<Salon> salons;

    public Manager(Person person) {
        this.person = person;
    }
}
