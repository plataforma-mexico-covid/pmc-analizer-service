package mx.mexicocovid19.plataforma.pmc.analizer.util;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ciudadano;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.EstatusAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.GeoLocation;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.OrigenAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.TipoAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.User;

public class AyudaTestUtil {

	public static Ayuda createAyuda(OrigenAyuda origenAyuda) {
		Ayuda ayudaAux = new Ayuda();
		ayudaAux.setOrigenAyuda(origenAyuda);
		Ciudadano ciudadano = new Ciudadano();
		User usuario = new User("TEST_"+origenAyuda.toString());
		ciudadano.setUser(usuario);
		ayudaAux.setCiudadano(ciudadano);
		GeoLocation ubicacion = new GeoLocation();
		ubicacion.setLatitude(99.1);
		ubicacion.setLongitude(104.2);
		ayudaAux.setUbicacion(ubicacion);
		ayudaAux.setEstatusAyuda(EstatusAyuda.NUEVA);
		TipoAyuda tipoAyuda = new TipoAyuda();
		tipoAyuda.setActive(true);
		tipoAyuda.setNombre("TEST");
		ayudaAux.setTipoAyuda(tipoAyuda);
		return ayudaAux;
	}
	
}
