package mx.mexicocovid19.plataforma.pmc.analizer.service;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;

import java.util.List;

public interface NotificationService {
    void sendNotificationMatch(final Integer ayudaId, final Integer ciudadanoId);
    void sendNotificationCercanos(final Ayuda ayuda, final List<Ayuda> cercanos);

    void sendNotificationMessage(final String numero, final String message);
}
