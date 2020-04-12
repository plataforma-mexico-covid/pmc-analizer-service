package mx.mexicocovid19.plataforma.pmc.analizer.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.Ayuda;
import mx.mexicocovid19.plataforma.pmc.analizer.model.entity.OrigenAyuda;

@RunWith(MockitoJUnitRunner.class)
public class AnalizerUtilsTest {

	private List<Ayuda> ayudas = new ArrayList<Ayuda>();
	
	@Test
	public void testExtractEmails() {
	ayudas.add(AyudaTestUtil.createAyuda(OrigenAyuda.OFRECE));
	ayudas.add(AyudaTestUtil.createAyuda(OrigenAyuda.SOLICITA));
	String[] emails = AnalizerUtils.extractEmails(ayudas);
	assertThat(emails).isNotNull();
	assertThat(emails.length).isEqualTo(2);
	}
	
	@Test
	public void testExtractEmailsNULL() {
	ayudas.clear();
	String[] emails = AnalizerUtils.extractEmails(ayudas);
	assertThat(emails).isNull();
	}

}
