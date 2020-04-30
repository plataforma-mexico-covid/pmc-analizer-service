package mx.mexicocovid19.plataforma.pmc.analizer.service;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.*;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.CiudadanoRepository;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.PeticionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultNotificationService implements NotificationService {

    private static final Logger logger = LogManager.getLogger(DefaultNotificationService.class);

    @Autowired
    private TextMessageService textMessageService;
    @Autowired
    private AyudaRepository ayudaRepository;
    @Autowired
    private CiudadanoRepository ciudadanoRepository;
    @Autowired
    private PeticionRepository peticionRepository;

    @Value("${plataforma.whatsapp.notificarSolicita}")
    private Boolean notificarSolicita;

    @Override
    public void sendNotificationMatch(final Integer ayudaId, final Integer ciudadanoId) {
        Ayuda ayuda = ayudaRepository.getOne(ayudaId);
        Ciudadano ciudadano = ciudadanoRepository.getOne(ciudadanoId);
        String message = getMessageMatch(ayuda, ciudadano);
        Ciudadano ofrece = ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ayuda.getCiudadano() : ciudadano;
        Ciudadano solicita = ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ciudadano : ayuda.getCiudadano();
        String numeroOfrece = getNumero(ofrece);
        String numeroSolicita = getNumero(solicita);
        savePeticion(ayuda, TipoMatch.MANUAL, ciudadano, null);
        if (numeroOfrece != null && !numeroOfrece.isEmpty()){
            textMessageService.sendWhatsAppMessage(message, numeroOfrece);
        }
        if (notificarSolicita && numeroSolicita != null && !numeroSolicita.isEmpty()){
            textMessageService.sendWhatsAppMessage(message, numeroSolicita);
        }
    }

    @Override
    public void sendNotificationCercanos(Ayuda ayuda, List<Ayuda> cercanos) {
        for (Ayuda cercano: cercanos) {
            String numero = getNumero(ayuda.getCiudadano());
            if (numero != null){
                String mensaje = getMessage(ayuda, cercano);
                textMessageService.sendWhatsAppMessage(mensaje, numero);
            }
            savePeticion(ayuda, TipoMatch.AUTOMATIC, null, cercano);
            if (notificarSolicita){
                numero = getNumero(cercano.getCiudadano());
                if (numero != null) {
                    String mensaje = getMessage(cercano, ayuda);
                    textMessageService.sendWhatsAppMessage(mensaje, numero);
                }
            }
        }
    }

    private void savePeticion(final Ayuda ayuda, TipoMatch tipoMatch, final Ciudadano ciudadano, final Ayuda ayudaMatch){
        try{
            Peticion peticion = new Peticion();
            peticion.setAyuda(ayuda);
            peticion.setTipoMatch(tipoMatch);
            peticion.setCiudadano(ciudadano);
            peticion.setAyudaMatch(ayudaMatch);
            peticion.setFechaPeticion(LocalDateTime.now());
            peticionRepository.save(peticion);
        } catch (Exception ex) {
            logger.error("Fail to try save the peticion: " + ex.getMessage());
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
        Ayuda ofrece = ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ayuda : ayudaMatch;
        Ayuda solicita = ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ayudaMatch : ayuda;

        Ciudadano cOfrece = ofrece.getCiudadano();
        Ciudadano cSolicita = solicita.getCiudadano();

        String ccOfrece = cOfrece.getInfoContacto();
        String ccSolicita = cSolicita.getInfoContacto();
        switch (ayuda.getOrigenAyuda()){
            case OFRECE:
                return String.format("Hola [%s] encontramos que tu ayuda [%s] puede ser muy util para [%s] quien solicita [%s] puedes contactarlo en [%s]",
                        new String[]{cOfrece.getNombreCompleto(), truncateDescription(ofrece.getDescripcion()),
                                cSolicita.getNombreCompleto(), truncateDescription(solicita.getDescripcion()), ccSolicita});
            case SOLICITA:
                return String.format("Hola [%s] encontramos que tu solicitud [%s] puede ser atendida por [%s] quien ofrece [%s] puedes contactarlo en [%s]",
                        new String[]{cSolicita.getNombreCompleto(), truncateDescription(solicita.getDescripcion()),
                                cOfrece.getNombreCompleto(), truncateDescription(ofrece.getDescripcion()), ccOfrece});
        }
        return null;
    }

    private String getMessageMatch(final Ayuda ayuda, final Ciudadano ciudadano){
        Ciudadano ofrece = ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ayuda.getCiudadano() : ciudadano;
        Ciudadano solicita = ayuda.getOrigenAyuda() == OrigenAyuda.OFRECE ? ciudadano : ayuda.getCiudadano();

        String contactoOfrece = ofrece.getInfoContacto();
        String contactoSolicita = solicita.getInfoContacto();
        String descripcion = truncateDescription(ayuda.getDescripcion());
        String ofreceName = ofrece.getNombreCompleto();
        String solicitaName = solicita.getNombreCompleto();

        return String.format("Descripcion de la ayuda: [%s]. Ahora pueden contactarse, los datos de contacto  [%s]: [%s] | [%s]: [%s]",
                new String[]{descripcion, ofreceName, contactoOfrece, solicitaName, contactoSolicita});
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
