package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(	name = "notificaciones" )
public class BitacoraNotificaciones {
 
 	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
 	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ayuda_id")
	private Ayuda ayuda;
	@Column(name = "notification_counter", nullable = false)
	private Integer notification_counter;
	@Column(name = "status", nullable = false)
	private String status;
 
	public BitacoraNotificaciones() {
	}

	public BitacoraNotificaciones(Ayuda ayuda, Integer notification_counter, String status) {
		super();
		this.ayuda = ayuda;
		this.notification_counter = notification_counter;
		this.status = status;
	}

	@Override
	public String toString() {
		return "BitacoraNotificaciones [id=" + id + ", ayuda=" + (ayuda!=null?ayuda.getId():null) + ", notification_counter="
				+ notification_counter + ", status=" + status + "]";
	}
	
	
}