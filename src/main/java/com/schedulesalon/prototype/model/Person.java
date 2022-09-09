package com.schedulesalon.prototype.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    private Long id;

    private String name;

    private String password;

    private Long age;

    private String sex;

    public Person(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
