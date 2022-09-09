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
public class Type {

    @Id
    private Long id;

    private String description;
}
