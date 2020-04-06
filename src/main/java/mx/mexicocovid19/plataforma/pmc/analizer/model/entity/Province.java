package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
    private Set<Municipality> municipalities;
}
