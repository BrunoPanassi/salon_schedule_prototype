package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class Schedules {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    private Long taskId;

    private Date begin;

    private Date end;

    private Boolean confirm;
}
