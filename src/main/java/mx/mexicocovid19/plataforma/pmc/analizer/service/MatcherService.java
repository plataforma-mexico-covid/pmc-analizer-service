package mx.mexicocovid19.plataforma.pmc.analizer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiStatus;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.EstatusAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;

@Service
public class MatcherService {
	
	private static final Logger logger = LogManager.getLogger(MatcherService.class);
	
	@Autowired
	private AyudaRepository ayudaRepository;
	
	@Autowired
	private ReminderService reminderService;
	
	public ApiStatus findMatches() {
		ApiStatus status = ApiStatus.PROCESSING;
		logger.info("***************Welcome to MATCHER SERVICE - Looking for all AYUDAS by status.");
		List<Ayuda> ayudas = ayudaRepository.findAllByEstatus(new ArrayList<>(Arrays.asList(EstatusAyuda.NUEVA, EstatusAyuda.PENDIENTE)));
		logger.info("***************Total AYUDAS found: {}", ayudas.size());
		if (!ayudas.isEmpty()) {
			ayudas.stream().forEach(a -> reminderService.generateReminders(a)); 
			status = ApiStatus.COMPLETED;
		} else {
			status = ApiStatus.REJECTED;
		}
		logger.info("Matches completed!!");
		return status;
	}
	
	public ApiStatus findMatches(Ayuda ayuda) {
		logger.info("***************Finding matches for single AYUDA...");
		reminderService.generateReminders(ayuda);
		return ApiStatus.COMPLETED;
	}

}
