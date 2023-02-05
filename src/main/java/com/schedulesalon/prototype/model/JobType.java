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
public class JobType {
    @Id
    @SequenceGenerator(name = "seq_job_type", sequenceName = "seq_job_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_job_type")
    private Long id;

    private String description;

    @OneToMany(mappedBy = "jobType")
    private List<Job> jobs;

    @OneToMany(mappedBy = "job")
    private List<ServiceType> serviceTypes;
}
