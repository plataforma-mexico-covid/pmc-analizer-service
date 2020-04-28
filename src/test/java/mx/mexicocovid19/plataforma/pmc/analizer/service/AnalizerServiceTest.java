package mx.mexicocovid19.plataforma.pmc.analizer.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiStatus;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerRequestDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerResponseDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;

@RunWith(MockitoJUnitRunner.class)
public class AnalizerServiceTest {

	@InjectMocks
	private AnalizerService analizerService;
	@Mock
	private MatcherService matcherService;
	@Mock
	private AyudaRepository ayudaRepository;
	
	private AnalizerRequestDTO request = null;
	
	@Test
	public void testAnalizeSingle() throws InterruptedException, ExecutionException {
		this.request = new AnalizerRequestDTO("single", Long.valueOf(1));
		when(ayudaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Ayuda()));
		when(matcherService.findMatches(Mockito.any())).thenReturn(ApiStatus.COMPLETED);
		CompletableFuture<AnalizerResponseDTO> response = analizerService.analize(request);
		assertThat(response).isNotNull();
		assertThat(response.get()).isNotNull();
		assertThat(response.get().getStatus()).isEqualTo(ApiStatus.COMPLETED);
	}

	@Test
	public void testAnalizeCron() throws InterruptedException, ExecutionException {
		this.request = new AnalizerRequestDTO("cron", Long.valueOf(1));
		when(matcherService.findMatches()).thenReturn(ApiStatus.COMPLETED);
		CompletableFuture<AnalizerResponseDTO> response = analizerService.analize(request);
		assertThat(response).isNotNull();
		assertThat(response.get()).isNotNull();
		assertThat(response.get().getStatus()).isEqualTo(ApiStatus.COMPLETED);
	}
	
	@Test
	public void testAnalizeNullRequest() throws InterruptedException, ExecutionException {
		CompletableFuture<AnalizerResponseDTO> response = analizerService.analize(null);
		assertThat(response).isNotNull();
		assertThat(response.get()).isNotNull();
		assertThat(response.get().getStatus()).isEqualTo(ApiStatus.REJECTED);
	}
	
	@Test
	public void testAnalizeOther() throws InterruptedException, ExecutionException {
		this.request = new AnalizerRequestDTO("test", Long.valueOf(1));
		CompletableFuture<AnalizerResponseDTO> response = analizerService.analize(request);
		assertThat(response).isNotNull();
		assertThat(response.get()).isNotNull();
		assertThat(response.get().getStatus()).isEqualTo(ApiStatus.REJECTED);
	}
	
	@Test
	public void testAnalizeSingleNOT_FUOUND() throws InterruptedException, ExecutionException {
		this.request = new AnalizerRequestDTO("single", Long.valueOf(0));
		when(ayudaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		CompletableFuture<AnalizerResponseDTO> response = analizerService.analize(request);
		assertThat(response).isNotNull();
		assertThat(response.get()).isNotNull();
		assertThat(response.get().getStatus()).isEqualTo(ApiStatus.INVALID);
	}

}
