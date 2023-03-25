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
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @SequenceGenerator(name = "seq_job", sequenceName = "seq_job")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_job")
    private Long id;

    private String description;

    @OneToMany(mappedBy = "job")
    private List<Service> services;

    public Job(String description) {
        this.description = description;
    }
}
