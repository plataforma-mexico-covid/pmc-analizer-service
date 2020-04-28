package mx.mexicocovid19.plataforma.pmc.analizer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiStatus;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.EstatusAyuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.repositories.AyudaRepository;

@RunWith(MockitoJUnitRunner.class)
public class MatcherServiceTest {

	@InjectMocks
	private MatcherService matcherService;
	@Mock
	private AyudaRepository ayudaRepository;
	@Mock
	private ReminderService reminderService;
	private List<Ayuda> matches = new ArrayList<>();
	
	@Test
	public void testFindMatches() {
		matches.add(new Ayuda());
		when(ayudaRepository.findAllByEstatus(Mockito.anyListOf(EstatusAyuda.class))).thenReturn(matches);
		ApiStatus status = matcherService.findMatches();
		assertThat(status).isEqualTo(ApiStatus.COMPLETED);
	}
	
	@Test
	public void testFindMatchesEmpty() {
		when(ayudaRepository.findAllByEstatus(Mockito.anyListOf(EstatusAyuda.class))).thenReturn(ListUtils.EMPTY_LIST);
		ApiStatus status = matcherService.findMatches();
		assertThat(status).isEqualTo(ApiStatus.REJECTED);
	}
	
	@Test
	public void testFindMatchesSingle() {
		ApiStatus status = matcherService.findMatches(new Ayuda());
		assertThat(status).isEqualTo(ApiStatus.COMPLETED);
	}
}
