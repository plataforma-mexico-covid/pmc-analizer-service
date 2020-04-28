package mx.mexicocovid19.plataforma.pmc.analizer.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.OrigenAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.util.AyudaTestUtil;

@RunWith(MockitoJUnitRunner.class)
public class VelocityMailServiceTest {

	@InjectMocks
	private VelocityMailService valocityMailService;
	@Mock
	private JavaMailSender mailSender;
	@Mock
	private VelocityEngine velocityEngine;
	@Mock
	private SimpleMailMessage templateMessage;
	private Ayuda ayuda;
	private List<Ayuda> cercanos = new ArrayList<>();
	
	@Test
	public void testSendNotification() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE);
		this.cercanos.add(AyudaTestUtil.createAyuda(OrigenAyuda.SOLICITA));
		velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, this);
		Velocity.init();
		when(velocityEngine.getTemplate(Mockito.anyString())).thenReturn(Velocity.getTemplate("src/test/resources/email/test.vm"));
		valocityMailService.sendNotification(ayuda, cercanos);
		Mockito.verify(velocityEngine).getTemplate(Mockito.anyString());
	}
	
	@Test
	public void testSendNotificationAyuda() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE);
		this.cercanos.add(AyudaTestUtil.createAyuda(OrigenAyuda.SOLICITA));
		valocityMailService.sendNotification(ayuda);
		Mockito.validateMockitoUsage();
	}
	
	@Test
	public void testSendNotificationEXCEPTION() {
		this.ayuda = AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE);
		this.cercanos.add(AyudaTestUtil.createAyuda(OrigenAyuda.SOLICITA));
		velocityEngine.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, this);
		Velocity.init();
		when(velocityEngine.getTemplate(Mockito.anyString())).thenReturn(Velocity.getTemplate("src/test/resources/email/test.vm"));
		valocityMailService.sendNotification(ayuda, cercanos);
		Mockito.verify(velocityEngine).getTemplate(Mockito.anyString());
	}

}
