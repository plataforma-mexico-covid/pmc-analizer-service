package mx.mexicocovid19.plataforma.pmc.analizer.controller;

import lombok.extern.log4j.Log4j2;
import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiResourceConstants;
import mx.mexicocovid19.plataforma.pmc.analizer.service.TextMessageService;
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
public class PublishMessageController {
    @Autowired
    TextMessageService textMessageService;

    @PostMapping(value="/publish/message", produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void singleReminder(
            @RequestBody Map<String, String> request) {
        log.info("***************Reciving request for PMC Analizer Send message.***************");
        log.info("***************Request: {}", request);
        textMessageService.sendWhatsAppMessage(request.get("mensaje"), request.get("numbero"));
        log.info("***************Send message finalized on: {}", LocalDateTime.now());
    }
}
