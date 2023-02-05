package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Task {

    @Id
    @SequenceGenerator(name = "seq_task", sequenceName = "seq_task")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_task")
    private Long id;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "task")
    private List<Service> services;
}
