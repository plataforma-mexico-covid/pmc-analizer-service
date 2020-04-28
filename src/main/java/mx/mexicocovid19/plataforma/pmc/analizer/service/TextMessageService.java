package mx.mexicocovid19.plataforma.pmc.analizer.service;

public interface TextMessageService {
    void sendWhatsAppMessage(final String mensaje, final String numero);
}
