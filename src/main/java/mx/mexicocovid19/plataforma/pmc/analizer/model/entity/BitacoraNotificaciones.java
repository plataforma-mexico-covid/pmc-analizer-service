package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(	name = "notificaciones" )
public class BitacoraNotificaciones {
 
 	@Id
 	@GeneratedValue
 	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
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