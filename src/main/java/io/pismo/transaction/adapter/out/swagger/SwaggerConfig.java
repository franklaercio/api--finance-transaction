package io.pismo.transaction.adapter.out.swagger;

import java.util.function.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

  private final String SWAGGER_NAME = "Service Transaction Pismo";

  private final String SWAGGER_DESCRIPTION = "Project created for test";

  private final String SWAGGER_VERSION = "1.0.0";

  private final String SWAGGER_PACKAGE = "io.pismo.transaction";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .select()
        .apis(apis())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(info());
  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .docExpansion(DocExpansion.LIST) // or DocExpansion.NONE or DocExpansion.FULL
        .build();
  }

  private Predicate<RequestHandler> apis() {
    return RequestHandlerSelectors.basePackage(SWAGGER_PACKAGE);
  }

  public ApiInfo info() {
    return new ApiInfoBuilder().title(SWAGGER_NAME)
        .contact(new Contact("Frank Laércio", "https://github.com/franklaercio", ""))
        .description(SWAGGER_DESCRIPTION)
        .version(SWAGGER_VERSION).build();
  }
}
