package mx.mexicocovid19.plataforma.pmc.analizer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import mx.mexicocovid19.plataforma.pmc.analizer.constants.ApiResourceConstants;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = { "mx.mexicocovid19.plataforma.controller" })
public class SwaggerConfig {

	@Bean
	public Docket publicServices() {
		final Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("public").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex(ApiResourceConstants.API_PATH_PRIVATE + "/.*")).build()
				.useDefaultResponseMessages(false);
		return docket;
	}

	@Bean
	public Docket privateServices() {
		final List<Parameter> list = new ArrayList<Parameter>();
		list.add(new ParameterBuilder().name("X-Auth-Token").description("Header para tokenId")
				.modelRef(new ModelRef("string")).parameterType("header").required(true).build());
		final Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("private").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex(ApiResourceConstants.API_PATH_PRIVATE + "/.*")).build()
				.globalOperationParameters(list);
		return docket;
	}

	private ApiInfo apiInfo() {
		final String apiTitle = "Overview";
		final String apiDescription = "PMC Analizer";
		final String apiVersion = "0.1";
		final String terminsOfService = "terminos de servicio url";
		final Contact contactInformation = new Contact("Ciudadanos Ayudandose", "https://mexicocovid19.mx", "contacto@mexicocovid19.mx");
		final String license = "License";
		final String licenseUrl = "https://mexicocovid19.mx/license";
		return new ApiInfo(apiTitle, apiDescription, apiVersion, terminsOfService, contactInformation, license,
				licenseUrl);
	}

}
