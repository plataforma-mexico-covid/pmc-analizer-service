package mx.mexicocovid19.plataforma.pmc.analizer.service;


import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("mailoff")
public class MockMailServiceImpl implements MailService {

    @Override
    public void sendNotification(Ayuda ayuda, List<Ayuda> cercanos) {

    }
}
