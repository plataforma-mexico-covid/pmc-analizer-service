package mx.mexicocovid19.plataforma.pmc.analizer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.BitacoraNotificaciones;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.OrigenAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.BitacoraNotificacionesRepository;

@Service
public class ReminderService {

	private static final Logger logger = LogManager.getLogger(ReminderService.class);

	@Autowired
	private BitacoraNotificacionesRepository bitacoraRepository;
	@Autowired
	private AyudaRepository ayudaRepository;
	@Autowired
	private MailService mailService;

	@Transactional
	public void generateReminders(Ayuda ayuda) {

		logger.info("***************Generating REMINDERS. Ayuda: {}", ayuda);

		BitacoraNotificaciones notificacion = bitacoraRepository.findByAyuda(ayuda);

		if (notificacion == null)
			notificacion = new BitacoraNotificaciones(ayuda, 0, ayuda.getStatus().toString());
		
		if (validateBitacora(notificacion, ayuda)) { // Valido para notificar
			// Validar si OFRECIO o SOLICITO
			OrigenAyuda origenContrario = OrigenAyuda.OFRECE == ayuda.getOrigenAyuda() ? OrigenAyuda.SOLICITA
					: OrigenAyuda.OFRECE;

			List<Ayuda> cercanos = ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(
					ayuda.getUbicacion().getLatitude(), ayuda.getUbicacion().getLongitude(), 20, origenContrario, ayuda.getCiudadano());
			
			logger.info("*************** Ayudas cercanas: {}, {}", cercanos.size(), ArrayUtils.toString(cercanos));
			
			if (!cercanos.isEmpty()) {
				mailService.sendNotification(ayuda, cercanos);
				System.out.println(ayuda.toString());
				System.out.println(notificacion.toString());
				bitacoraRepository.saveAndFlush(notificacion);
				ayuda.setNotificaciones(notificacion);
				ayudaRepository.saveAndFlush(ayuda);
			}
		} else {
			logger.info("#########################################");
			logger.info("El ofertante/beneficiario ya alcanzo su limite diario de notificaciones: {}", ayuda.getCiudadano().getUser().getUsername());
		}
	}

	private boolean validateBitacora(BitacoraNotificaciones notificacion, Ayuda ayuda) {
		logger.info("***************Validando que no se han enviado mas de 3 correos a la ayuda.");
		// Si OFERTANTE no mas de 3, Si SOLICITANTE no mas de 5
		if (OrigenAyuda.OFRECE == notificacion.getAyuda().getOrigenAyuda()
				&& notificacion.getNotification_counter() < 1) {
			notificacion.setNotification_counter(notificacion.getNotification_counter()+1);
			return true; 
		} else if(OrigenAyuda.SOLICITA == notificacion.getAyuda().getOrigenAyuda()
				&& notificacion.getNotification_counter() < 2) {
			notificacion.setNotification_counter(notificacion.getNotification_counter()+1);
			return true;
		} else {
			return false;
		}
	}
}
