package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Client extends Person{

    private String lastVisitedSalon;

    private List<Schedules> schedules;

    public Client(String name, String password) {
        super(name, password);
    }
}
