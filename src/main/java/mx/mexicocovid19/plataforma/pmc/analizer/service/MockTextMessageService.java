package mx.mexicocovid19.plataforma.pmc.analizer.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("whatsappoff")
public class MockTextMessageService implements TextMessageService {
    @Override
    public void sendWhatsAppMessage(final String mensaje, final String numero) {

    }
}
