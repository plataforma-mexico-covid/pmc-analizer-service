package mx.mexicocovid19.plataforma.pmc.analizer.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;

@RunWith(MockitoJUnitRunner.class)
public class MockMailServiceImplTest {

	@InjectMocks
	private MockMailServiceImpl mockMailService;
	
	@Test
	public void testSendNotification() {
		mockMailService.sendNotification(new Ayuda(), new ArrayList<Ayuda>());
		Mockito.validateMockitoUsage();
	}

}
