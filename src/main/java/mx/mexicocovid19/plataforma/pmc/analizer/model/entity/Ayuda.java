package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ayuda")
public class Ayuda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CIUDADANO_ID", nullable = false)
    private Ciudadano ciudadano;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GEO_LOCATION_ID", nullable = false)
    private GeoLocation ubicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TIPO_AYUDA_ID", nullable = false)
    private TipoAyuda tipoAyuda;
    @Enumerated(EnumType.STRING)
    @Column(name = "ORIGEN_AYUDA")
    private OrigenAyuda origenAyuda;
    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;
    @Column(name = "ACTIVE")
    private Boolean active;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS_AYUDA")
    private EstatusAyuda status;
    @OneToOne(mappedBy = "ayuda", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private BitacoraNotificaciones notificaciones;
	
    @Override
	public String toString() {
		return "Ayuda [id=" + id + ", descripcion=" + descripcion + ", ciudadano=" + ciudadano + ", ubicacion="
				+ ubicacion + ", tipoAyuda=" + tipoAyuda + ", origenAyuda=" + origenAyuda + ", fechaRegistro="
				+ fechaRegistro + ", active=" + active + ", status=" + status + ", notificaciones=" + (notificaciones!=null?notificaciones.toString():null)
				+ "]";
	}
    
    
}