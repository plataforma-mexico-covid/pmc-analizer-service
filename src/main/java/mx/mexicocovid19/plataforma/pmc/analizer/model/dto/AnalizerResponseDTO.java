package mx.mexicocovid19.plataforma.pmc.analizer.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiStatus;

@Getter
@Setter
@ToString
public class AnalizerResponseDTO {

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ApiStatus status;
}
