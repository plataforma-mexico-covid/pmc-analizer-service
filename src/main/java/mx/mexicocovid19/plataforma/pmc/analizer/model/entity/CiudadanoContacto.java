package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ciudadano_contacto")
public class CiudadanoContacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CONTACTO")
    private TipoContacto tipoContacto;
    @Column(name = "CONTACTO")
    private String contacto;
}