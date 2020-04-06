package mx.mexicocovid19.plataforma.pmc.analizer.util;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiStatus;
import mx.mexicocovid19.plataforma.pmc.analizer.constants.TipoEmailEnum;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerResponseDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;

public class AnalizerUtils {

	public static AnalizerResponseDTO createDefaultResponse() {
		AnalizerResponseDTO response = new AnalizerResponseDTO();
		response.setStartTime(LocalDateTime.now());
		response.setStatus(ApiStatus.ACCEPTED);
		return response;
	}

	public static String[] extractEmails(List<Ayuda> ayudasList) {
		if (!ayudasList.isEmpty()) {
			return ayudasList.stream()
					.map(aid -> aid.getCiudadano().getUser().getUsername())
					.toArray(n -> new String[n]);
		}
		return null;
	}
	
	public static Map<String, Object> createEmailVariables(TipoEmailEnum tipoEmail, Ayuda ayuda, List<Ayuda> cercanos) {
		
		if(tipoEmail == null || ayuda == null || cercanos == null || cercanos.isEmpty())
			return null;
		
		Map<String, Object> variables = new HashMap<>();
		
		variables.put("nombre", ayuda.getCiudadano().getNombre());
		variables.put("tipoAyuda", ayuda.getTipoAyuda().getNombre().toUpperCase());
		variables.put("cercanosTotal", cercanos.size());
		variables.put("cercanosList", cercanos);
		
		if (tipoEmail == TipoEmailEnum.OFRECE_AYUDA_REMINDER) {
			variables.put("HEADER", "");
		} else {
			
		}
		
		return variables;
	}

}
