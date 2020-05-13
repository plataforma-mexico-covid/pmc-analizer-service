package mx.mexicocovid19.plataforma.pmc.analizer.controller;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiResourceConstants;
import mx.mexicocovid19.plataforma.pmc.analizer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping(value = ApiResourceConstants.API_PATH_PUBLIC)
@Validated
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PostMapping(value="/notification/match", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void singleReminder(@RequestBody Map<String, Integer> request) {
        log.info("***************Reciving request for PMC Analizer Match ayuda.***************");
        log.info("***************Request: {}", request);
        notificationService.sendNotificationMatch(request.get("ayudaId"), request.get("ciudadanoId"));
        log.info("***************Match ayuda finalized on: {}", LocalDateTime.now());
    }

    @PostMapping(value="/notification/send", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestBody Map<String, String> request) {
        log.info("***************Reciving request for PMC Analizer send message.***************");
        log.info("***************Request: {}", request);
        notificationService.sendNotificationMessage(request.get("numero"), request.get("message"));
        log.info("***************Send message finalized on: {}", LocalDateTime.now());
    }
}
