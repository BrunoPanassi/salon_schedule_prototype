package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;

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
    @JoinColumn(name = "job_type_id")
    private JobType jobType;
}
