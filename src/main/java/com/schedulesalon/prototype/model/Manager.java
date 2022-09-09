package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Manager extends Person {
    private Long roleId;

    private List<Salon> salons;
}
