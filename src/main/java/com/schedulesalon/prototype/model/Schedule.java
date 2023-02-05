package com.schedulesalon.prototype.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class Schedule {

    @Id
    @SequenceGenerator(name = "seq_schedule", sequenceName = "seq_schedule")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_schedule")
    private Long id;

    @OneToOne(mappedBy = "schedule")
    private Hour hour;

    @OneToOne(mappedBy = "schedule")
    private Task task;

    @NotNull
    private Boolean clientConfirm;

    @NotNull
    private Boolean professionalConfirm;
}
