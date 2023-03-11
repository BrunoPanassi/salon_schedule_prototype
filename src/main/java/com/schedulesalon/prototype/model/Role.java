package com.schedulesalon.prototype.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    public enum TypeRole {
        CLIENT("CLIENT"),
        PROFESSIONAL("PROFESSIONAL"),
        ATTENDANT("ATTENDANT"),
        MANAGER("MANAGER");

        private String type;

        TypeRole(String type) {
            this.type = type;
        }

        public String getTypeRole() {
            return type;
        }
    }

    @Id
    @SequenceGenerator(name = "seq_role", sequenceName = "seq_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_role")
    private Long id;

    private String type;

    public Role (TypeRole type) {
        this.type = type.getTypeRole();
    }

}
