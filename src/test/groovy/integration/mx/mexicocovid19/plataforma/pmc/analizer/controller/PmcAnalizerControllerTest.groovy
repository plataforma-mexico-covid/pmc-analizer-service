package integration.mx.mexicocovid19.plataforma.pmc.analizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import groovy.json.JsonSlurper
import mx.mexicocovid19.plataforma.pmc.analizer.PlataformaAnalizerApp;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerRequestDTO;
import mx.mexicocovid19.plataforma.pmc.analizer.model.dto.AnalizerResponseDTO;
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = PlataformaAnalizerApp.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
@Profile(["mailoff", "localdb"])
public class PmcAnalizerControllerTest extends Specification {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Unroll("Escenario numero #index se intenta generar un reminder para la ayuda #ayuda y se recive el estatus #status")
	def "Validar single reminders" (int index, String remiderType, int ayuda, HttpStatus status) {	
		expect:

		def uri = "http://localhost:" + port + "/api/v1/internal/analizer/reminder"
		def headers = new HttpHeaders()
		headers.setContentType(MediaType.APPLICATION_JSON)
		def request = new AnalizerRequestDTO(remiderType,Long.parseLong(String.valueOf(ayuda)))
		def requestEntity = new HttpEntity(request, headers)
		
		println requestEntity
		
		ResponseEntity<AnalizerResponseDTO> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, AnalizerResponseDTO.class)
		status == result.statusCode

		where:
		index   | remiderType	| ayuda		| status
		0       | "single" 		| 1 		| HttpStatus.CREATED
		1       | "single"		| 2			| HttpStatus.CREATED
		2       | "single"		| 1000 		| HttpStatus.CREATED
		3		| "chron"		| 0			| HttpStatus.CREATED	
	}
}
