package com.schedulesalon.prototype.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@RequiredArgsConstructor
public class Professional {

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

    @OneToMany(mappedBy = "professional")
    private List<Job> jobs;

    @NotNull
    private Boolean allowScheduleInTime;
}
