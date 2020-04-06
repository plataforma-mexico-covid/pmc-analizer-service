package mx.mexicocovid19.plataforma.pmc.analizer.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiStatus;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerRequestDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerResponseDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;
import mx.mexicocovid19.plataforma.pmc.analizer.util.AnalizerUtils;

@Service
public class AnalizerService {

	private static final Logger logger = LogManager.getLogger(AnalizerService.class);

	@Autowired
	private MatcherService matcherService;
	@Autowired
	private AyudaRepository ayudaRepository;

	@Async("asyncExecutor")
	public CompletableFuture<AnalizerResponseDTO> analize(AnalizerRequestDTO request) {
		logger.info("***************Starting ANALIZER for request.");
		AnalizerResponseDTO response = AnalizerUtils.createDefaultResponse();
		ApiStatus statusResponse = null;
		
		if (!isValidRequest(request, response))
			return CompletableFuture.completedFuture(response);
		
		if ("single".equalsIgnoreCase(request.getReminderType())) {
			// SINGLE REQUEST
			logger.info("***************Processing SINGLE request...");
			statusResponse = processSingleRequest(request);
		} else if ("cron".equalsIgnoreCase(request.getReminderType())) {
			// FIND MATCHES
			logger.info("***************Processing CRON request...");
			statusResponse = matcherService.findMatches();
		} else {
			statusResponse = ApiStatus.REJECTED;
		}
		
		response.setEndTime(LocalDateTime.now());
		response.setStatus(statusResponse);
		return CompletableFuture.completedFuture(response);
	}

	private boolean isValidRequest(AnalizerRequestDTO request, AnalizerResponseDTO response) {
		if (request == null || StringUtils.isBlank(request.getReminderType())) {
			response.setStatus(ApiStatus.REJECTED);
			response.setEndTime(LocalDateTime.now());
			return false;
		} else {
			response.setStatus(ApiStatus.CREATED);
			return true;
		}
	}
	
	private ApiStatus processSingleRequest(AnalizerRequestDTO request) {
		Optional<Ayuda> ayuda = ayudaRepository.findById(Integer.valueOf(String.valueOf(request.getAyudaId())));
		if (ayuda.isPresent()) {
			return matcherService.findMatches(ayuda.get());
		} else {
			return ApiStatus.INVALID;
		}
	}
}
