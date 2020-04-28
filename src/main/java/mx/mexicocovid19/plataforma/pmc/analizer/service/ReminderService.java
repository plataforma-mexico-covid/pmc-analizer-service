package mx.mexicocovid19.plataforma.pmc.analizer.service;

import java.util.List;

import javax.transaction.Transactional;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	@Autowired
	private TextMessageService textMessageService;

	@Value("${plataforma.whatsapp.notificarSolicita}")
	private Boolean notificarSolicita;
	@Value("${plataforma.whatsapp.kilometros}")
	private double kilometros;
	@Value("${plataforma.whatsapp.mensaje.ofrece}")
	private String mensajeOfrece;
	@Value("${plataforma.whatsapp.mensaje.solicita}")
	private String mensajeSolicita;

	@Transactional
	public void generateReminders(Ayuda ayuda) {

		logger.info("***************Generating REMINDERS. Ayuda: {}", ayuda);

		BitacoraNotificaciones notificacion = bitacoraRepository.findByAyuda(ayuda);

		if (notificacion == null)
			notificacion = new BitacoraNotificaciones(ayuda, 0, ayuda.getEstatusAyuda().toString());
		
		if (validateBitacora(notificacion, ayuda)) { // Valido para notificar
			// Validar si OFRECIO o SOLICITO
			OrigenAyuda origenContrario = OrigenAyuda.OFRECE == ayuda.getOrigenAyuda() ? OrigenAyuda.SOLICITA
					: OrigenAyuda.OFRECE;

			List<Ayuda> cercanos = ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(
					ayuda.getUbicacion().getLatitude(), ayuda.getUbicacion().getLongitude(), this.kilometros, origenContrario, ayuda.getCiudadano());
			
			logger.info("*************** Ayudas cercanas: {}, {}", cercanos.size(), ArrayUtils.toString(cercanos));
			
			if (!cercanos.isEmpty()) {
				mailService.sendNotification(ayuda, cercanos);
				sendNotification(ayuda, cercanos);
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
				&& notificacion.getNotification_counter() < 3) {
			notificacion.setNotification_counter(notificacion.getNotification_counter()+1);
			return true; 
		} else if(OrigenAyuda.SOLICITA == notificacion.getAyuda().getOrigenAyuda()
				&& notificacion.getNotification_counter() < 5) {
			notificacion.setNotification_counter(notificacion.getNotification_counter()+1);
			return true;
		} else {
			return false;
		}
	}

	private void sendNotification(final Ayuda ayuda, final List<Ayuda> cercanos) {
		for (Ayuda cercano: cercanos) {
			String numero = getNumero(ayuda.getCiudadano());
			if (numero != null){
				String mensaje = getMessage(ayuda, cercano);
				textMessageService.sendWhatsAppMessage(mensaje, numero);
			}
			if (notificarSolicita){
				numero = getNumero(cercano.getCiudadano());
				if (numero != null) {
					String mensaje = getMessage(cercano, ayuda);
					textMessageService.sendWhatsAppMessage(mensaje, numero);
				}
			}
		}
	}

	private String getNumero(final Ciudadano ciudadano){
		for (CiudadanoContacto contacto : ciudadano.getContactos()) {
			if (TipoContacto.TELEFONO_MOVIL == contacto.getTipoContacto() ||
					TipoContacto.WHATSAPP == contacto.getTipoContacto()) {
				return contacto.getContacto();
			}
		}
		return null;
	}

	private String getMessage(final Ayuda ayuda, Ayuda ayudaMatch){
		Ciudadano c1 = ayuda.getCiudadano();
		Ciudadano c2 = ayudaMatch.getCiudadano();
		switch (ayuda.getOrigenAyuda()){
			case SOLICITA:
				return String.format(mensajeSolicita,
						new String[]{c1.getNombreCompleto(), truncateDescription(ayuda.getDescripcion()),
								c2.getNombreCompleto(), truncateDescription(ayudaMatch.getDescripcion())});
			case OFRECE:
				return String.format(mensajeOfrece,
						new String[]{c1.getNombreCompleto(), truncateDescription(ayuda.getDescripcion()),
						c2.getNombreCompleto(), truncateDescription(ayudaMatch.getDescripcion())});
		}
		return null;
	}

	private String truncateDescription(final String description){
		if (description == null)
			return "";
		if(description.length() > 200){
			return description.substring(0, 196) + "...";
		}
		return description;
	}
}
