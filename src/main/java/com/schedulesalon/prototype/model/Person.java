package com.schedulesalon.prototype.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @SequenceGenerator(name = "seq_person", sequenceName = "seq_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_person")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    private Date birthday;

    private String email;

    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public Person(String name, String password, String phoneNUmber, String email) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNUmber;
        this.email = email;
    }
}
