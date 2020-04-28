package mx.mexicocovid19.plataforma.pmc.analizer.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.BitacoraNotificaciones;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.EstatusAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.OrigenAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.BitacoraNotificacionesRepository;
import mx.mexicocovid19.plataforma.pmc.analizer.util.AyudaTestUtil;

@RunWith(MockitoJUnitRunner.class)
public class ReminderServiceTest {

	@InjectMocks
	private ReminderService reminderService;
	@Mock
	private BitacoraNotificacionesRepository bitacoraRepository;
	@Mock
	private AyudaRepository ayudaRepository;
	@Mock
	private MailService mailService;
	private Ayuda ayuda;
	private BitacoraNotificaciones bitacora;
	private List<Ayuda> cercanos = new ArrayList<Ayuda>();

	
	@Test
	public void testGenerateRemindersOFRECE() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE);
		this.bitacora = new BitacoraNotificaciones(ayuda, 1, EstatusAyuda.PENDIENTE.toString());
		cercanos.add(new Ayuda());
		when(bitacoraRepository.findByAyuda(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any())).thenReturn(cercanos);
		when(bitacoraRepository.saveAndFlush(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.saveAndFlush(Mockito.any())).thenReturn(ayuda);
		reminderService.generateReminders(ayuda);
		Mockito.verify(bitacoraRepository).findByAyuda(Mockito.any());
		Mockito.verify(ayudaRepository).findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any());
		Mockito.verify(bitacoraRepository).saveAndFlush(Mockito.any());
		Mockito.verify(ayudaRepository).saveAndFlush(Mockito.any());
	}

	@Test
	public void testGenerateRemindersSOLICITA() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.SOLICITA);
		this.bitacora = new BitacoraNotificaciones(ayuda, 1, EstatusAyuda.PENDIENTE.toString());
		cercanos.add(new Ayuda());
		when(bitacoraRepository.findByAyuda(Mockito.any())).thenReturn(this.bitacora);
		when(ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any())).thenReturn(cercanos);
		when(bitacoraRepository.saveAndFlush(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.saveAndFlush(Mockito.any())).thenReturn(ayuda);
		reminderService.generateReminders(ayuda);
		Mockito.verify(bitacoraRepository).findByAyuda(Mockito.any());
		Mockito.verify(ayudaRepository).findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any());
		Mockito.verify(bitacoraRepository).saveAndFlush(Mockito.any());
		Mockito.verify(ayudaRepository).saveAndFlush(Mockito.any());
	}

	
	@Test
	public void testGenerateRemindersOFRECE_LIMIT() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE);
		this.bitacora = new BitacoraNotificaciones(ayuda, 3, EstatusAyuda.PENDIENTE.toString());
		cercanos.add(new Ayuda());
		when(bitacoraRepository.findByAyuda(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any())).thenReturn(cercanos);
		when(bitacoraRepository.saveAndFlush(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.saveAndFlush(Mockito.any())).thenReturn(ayuda);
		reminderService.generateReminders(ayuda);
		Mockito.verify(bitacoraRepository).findByAyuda(Mockito.any());
	}
	
	@Test
	public void testGenerateRemindersSOLICITA_LIMIT() {		
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.SOLICITA);
		this.bitacora = new BitacoraNotificaciones(ayuda, 5, EstatusAyuda.PENDIENTE.toString());
		cercanos.add(new Ayuda());
		when(bitacoraRepository.findByAyuda(Mockito.any())).thenReturn(this.bitacora);
		when(ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any())).thenReturn(cercanos);
		when(bitacoraRepository.saveAndFlush(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.saveAndFlush(Mockito.any())).thenReturn(ayuda);
		reminderService.generateReminders(ayuda);
		Mockito.verify(bitacoraRepository).findByAyuda(Mockito.any());
	}
	
	
	@Test
	public void testGenerateRemindersNO_BITACORA() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE);
		cercanos.add(new Ayuda());
		when(bitacoraRepository.findByAyuda(Mockito.any())).thenReturn(null);
		when(ayudaRepository.findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any())).thenReturn(cercanos);
		when(bitacoraRepository.saveAndFlush(Mockito.any())).thenReturn(bitacora);
		when(ayudaRepository.saveAndFlush(Mockito.any())).thenReturn(ayuda);
		reminderService.generateReminders(ayuda);
		Mockito.verify(bitacoraRepository).findByAyuda(Mockito.any());
		Mockito.verify(ayudaRepository).findByAllInsideOfKilometersByOrigenAyuda(Mockito.anyDouble(), Mockito.anyDouble(),
				Mockito.anyDouble(), Mockito.any(), Mockito.any());
		Mockito.verify(bitacoraRepository).saveAndFlush(Mockito.any());
		Mockito.verify(ayudaRepository).saveAndFlush(Mockito.any());
	}
}
