package mx.mexicocovid19.plataforma.pmc.analizer.service;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Profile("whatsappon")
public class WhatsAppTextMessageService implements TextMessageService {

    @Value("${plataforma.whatsapp.url}")
    private String url;

    @Value("${plataforma.whatsapp.token}")
    private String token;

    RestTemplate restTemplate = new RestTemplate();

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void sendWhatsAppMessage(final String mensaje, final String numero){
        try{
            Map<String, String> request = createRequest(mensaje, numero);
            ResponseEntity<String> response
                    = restTemplate.postForEntity(this.url, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK)
                return;
            String result = response.getBody();
            if (!result.equals("ok")){
                logger.error("Error al enviar mensaje al numero: " + request.get("phone")
                        + " mensaje: " + request.get("msg"));
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private Map<String, String> createRequest(final String mensaje, final String numero){
        Map<String, String> request = new HashMap<>();
        request.put("phone", numero);
        request.put("msg", mensaje);
        request.put("token", token);
        return request;
    }
}
