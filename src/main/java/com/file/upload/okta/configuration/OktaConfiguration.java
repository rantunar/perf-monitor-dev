package com.file.upload.okta.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "okta.sdk")
public class OktaConfiguration {

  @NotNull
  private String url;
  @NotNull
  private String responseType;
  @NotNull
  private String redirectUri;
  @NotNull
  private String responseMode;
  @NotNull
  private String scope;
  @NotNull
  private String clientId;
}
