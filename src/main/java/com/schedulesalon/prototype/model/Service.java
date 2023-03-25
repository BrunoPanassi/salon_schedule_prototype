package com.schedulesalon.prototype.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    @SequenceGenerator(name = "seq_generator", sequenceName = "seq_generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_generator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private String description;

    private Double price;

    @NotNull
    private Long timeExecutionAverage; //TO DO: In minutes
}
