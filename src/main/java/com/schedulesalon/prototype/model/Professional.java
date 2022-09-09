package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Professional extends Person{

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @OneToMany(mappedBy = "professional")
    private List<Jobs> jobs;

    @OneToMany(mappedBy = "professional")
    private List<Schedules> schedules;

    public Professional(String name, String password) {
        super(name, password);
    }
}
