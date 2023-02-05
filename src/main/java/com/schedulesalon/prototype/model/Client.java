package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@RequiredArgsConstructor
public class Client {

    @Id
    @SequenceGenerator(name = "seq_client", sequenceName = "seq_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_client")
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "client")
    private List<Task> tasks;


}
