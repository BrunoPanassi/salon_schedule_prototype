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
public class Job {

    @Id
    @SequenceGenerator(name = "seq_job", sequenceName = "seq_job")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_job")
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @ManyToOne
    @JoinColumn(name = "job_type_id")
    private JobType jobType;
}
