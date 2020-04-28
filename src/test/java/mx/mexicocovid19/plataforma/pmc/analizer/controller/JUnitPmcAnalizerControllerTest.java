package mx.mexicocovid19.plataforma.pmc.analizer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerRequestDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerResponseDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.service.AnalizerService;

@RunWith(MockitoJUnitRunner.class)
public class JUnitPmcAnalizerControllerTest {

	@InjectMocks
	private PmcAnalizerController controller;

	@Mock
	private AnalizerService analizerService;

	private AnalizerRequestDTO request;

	@Before
	public void init() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSingleReminder() throws InterruptedException, ExecutionException {
		when(analizerService.analize(Mockito.any()))
				.thenReturn(CompletableFuture.completedFuture(new AnalizerResponseDTO()));
		request = new AnalizerRequestDTO("single", Long.valueOf(1));
		assertThat(controller.singleReminder(request)).isNotNull();
	}

	@Test
	public void testSingleReminderChron() throws InterruptedException, ExecutionException {
		when(analizerService.analize(Mockito.any()))
				.thenReturn(CompletableFuture.completedFuture(new AnalizerResponseDTO()));
		request = new AnalizerRequestDTO("chron", Long.valueOf(1));
		assertThat(controller.singleReminder(request)).isNotNull();
	}

	@Test
	public void testSingleReminderNotFound() throws InterruptedException, ExecutionException {
		when(analizerService.analize(Mockito.any()))
				.thenReturn(CompletableFuture.completedFuture(new AnalizerResponseDTO()));
		request = new AnalizerRequestDTO("other", Long.valueOf(1));
		assertThat(controller.singleReminder(request)).isNotNull();
	}
}
