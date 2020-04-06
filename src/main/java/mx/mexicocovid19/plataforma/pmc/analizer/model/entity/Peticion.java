package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "peticion")
public class Peticion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AYUDA_ID", nullable = false)
    private Ayuda ayuda;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @Column(name = "FECHA_PETICION")
    private LocalDateTime fechaPeticion;
}