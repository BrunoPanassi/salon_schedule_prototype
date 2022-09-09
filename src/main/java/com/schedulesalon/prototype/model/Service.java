package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    @Id
    private Long id;

    private Long jobId;

    private Long salonId;

    private String description;
}
