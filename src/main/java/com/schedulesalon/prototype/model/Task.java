package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
public class Task {

    @Id
    private Long id;

    private Long scheduleId;

    private Long clientId;

    private Long serviceId;

    private String description;
}
