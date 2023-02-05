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
public class ServiceType {
    @Id
    @SequenceGenerator(name = "seq_service_type", sequenceName = "seq_service_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_service_type")
    private Long id;

    private String description;

    @OneToMany(mappedBy = "serviceType")
    private List<Service> services;

    @ManyToOne
    @JoinColumn(name = "job_type_id")
    private JobType job;
}
