package mx.mexicocovid19.plataforma.pmc.analizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "mx.mexicocovid19.plataforma.pmc.analizer")
public class PlataformaAnalizerApp {
	
	public static void main(String[] args) {
		SpringApplication.run(PlataformaAnalizerApp.class, args);
	}
}
