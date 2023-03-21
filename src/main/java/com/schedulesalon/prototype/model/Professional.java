package com.schedulesalon.prototype.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@RequiredArgsConstructor
public class Professional {

    public static final String objectName = "Profissional";

    @Id
    @SequenceGenerator(name = "seq_professional", sequenceName = "seq_professional")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_professional")
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @OneToMany(mappedBy = "professional")
    private List<Hour> hours;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Job> jobs = new ArrayList<>();

    @NotNull
    private Boolean allowScheduleInTime;

    public Professional(Person person) {
        this.person = person;
    }
}
