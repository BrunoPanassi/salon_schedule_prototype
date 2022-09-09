package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jobs {

    @Id
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;
}
