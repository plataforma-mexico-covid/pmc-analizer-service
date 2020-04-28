package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_token")
public class UserToken {

	@Id
	@Column(name = "token", nullable = false, length = 45)
	private String token;
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private User user;
	@Column(name = "validated", nullable = false)
	private boolean validated;
	@Column(name = "expiration_date")
	private Date expirationDate;
	@Column(name = "type", nullable = false, length = 15)
	private String type;
}