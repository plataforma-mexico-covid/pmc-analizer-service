package mx.mexicocovid19.plataforma.pmc.analizer.service;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;

import java.util.List;

public interface MailService {
    public void sendNotification(Ayuda ayuda, List<Ayuda> cercanos);
}
