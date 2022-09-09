package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Salon {

    @Id
    private Long id;

    private String name;

    private String description;

    private String address;

    private Long typeId;

    private Double rangePrice;

    private List<Professional> professionals;

    private Long managerId;
}
