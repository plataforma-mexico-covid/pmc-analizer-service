package mx.mexicocovid19.plataforma.pmc.analizer.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnalizerRequestDTO {
	
	String reminderType;	
	Long ayudaId;
	
	public AnalizerRequestDTO(){}
	public AnalizerRequestDTO(String reminderType, Long ayudaId) {
		super();
		this.reminderType = reminderType;
		this.ayudaId = ayudaId;
	}	
}