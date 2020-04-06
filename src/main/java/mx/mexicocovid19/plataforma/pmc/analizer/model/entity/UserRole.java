package mx.mexicocovid19.plataforma.pmc.analizer.model.entity;


import javax.persistence.*;

@Entity
@Table(	name = "user_role",
		uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "username" }))
public class UserRole{
 	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private User user;
	@Column(name = "role", nullable = false, length = 45)
	private String role;
 
	public UserRole() {
	}
 
	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
	
	public Long id() {
		return this.id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return this.user;
	}
 
	public void setUser(User user) {
		this.user = user;
	}
 
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole{" +
				"id=" + id +
				", user=" + user +
				", role='" + role + '\'' +
				'}';
	}
}