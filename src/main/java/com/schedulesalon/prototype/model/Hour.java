package com.schedulesalon.prototype.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class Hour {
    @Id
    @SequenceGenerator(name = "seq_hour", sequenceName = "seq_hour")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_hour")
    private Long id;

    private LocalDateTime begin;

    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @NotNull
    private String status; //TODO: An Enum

    @NotNull
    private Long confirmTimeLimit; //TODO: In minutes

    public Hour (LocalDateTime begin, LocalDateTime end, Professional professional) {
        this.begin = begin;
        this.end = end;
        this.professional = professional;
    }
}
