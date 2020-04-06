package mx.mexicocovid19.plataforma.pmc.analizer.controller;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiResourceConstants;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerRequestDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerResponseDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.service.AnalizerService;

@Controller
@RequestMapping(value = ApiResourceConstants.API_PATH_PRIVATE)
@Validated
public class PmcAnalizerController {

	private static final Logger logger = LogManager.getLogger(PmcAnalizerController.class);
	
	@Autowired
	private AnalizerService analizerService;
	
	@PostMapping(value="/analizer/reminder", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public AnalizerResponseDTO singleReminder(
			@RequestBody AnalizerRequestDTO request) throws InterruptedException, ExecutionException {
		logger.info("***************Reciving request for PMC Analizer single reminder.***************");
		logger.info("***************Request: {}", request);
		CompletableFuture<AnalizerResponseDTO> analizerFuture = analizerService.analize(request);
        CompletableFuture.allOf(analizerFuture).join();
        AnalizerResponseDTO response = analizerFuture.get();
        logger.info("***************Single Reminder finalized on: {}, Response: {}", LocalDateTime.now(), response);
        return response;
	}
}
