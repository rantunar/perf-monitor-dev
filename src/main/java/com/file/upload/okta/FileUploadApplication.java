package com.file.upload.okta;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FileUploadApplication {

  @Autowired
  ConfigurableEnvironment environment;

  public static void main(String[] args) {
    SpringApplication.run(FileUploadApplication.class, args);
  }

  @Bean
  public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(30))
        .setReadTimeout(Duration.ofSeconds(30))
        .build();
  }

  @PostConstruct
  public void init() {
    var profiles = environment.getProperty("okta.oauth2.client-id");
    // Create map for properites and add first (important)
    Map<String, Object> myProperties = new HashMap<>();
    myProperties.put("okta.oauth2.client-id", profiles+"-custom-value");
    environment.getPropertySources().addFirst(
        new MapPropertySource("decrypt-prop", myProperties));
  }
}
