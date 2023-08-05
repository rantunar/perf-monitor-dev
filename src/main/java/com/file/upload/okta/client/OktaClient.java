package com.file.upload.okta.client;

import com.file.upload.okta.advices.TrackExecutionTime;
import com.file.upload.okta.configuration.OktaConfiguration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class OktaClient {

  @NonNull private final RestTemplate restTemplate;
  @NonNull private final OktaConfiguration config;

  public Map<String,String> getToken(final String sessionToken) {

    final Map<String,String> map = new HashMap<>();
    final HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE);
    final HttpEntity<?> entity = new HttpEntity<>(headers);

    final String urlTemplate = UriComponentsBuilder.fromHttpUrl(config.getUrl())
        .queryParam("client_id", config.getClientId())
        .queryParam("response_type", config.getResponseType())
        .queryParam("redirect_uri", config.getRedirectUri())
        .queryParam("state", "state-296bc9a0-a2a2-4a57-be1a-d0e2fd9bb601")
        .queryParam("sessionToken", sessionToken)
        .queryParam("response_mode", config.getResponseMode())
        .queryParam("nonce", "asdads")
        .queryParam("scope", config.getScope())
        .toUriString();

    final ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class);
    map.put("access_token", (String) response.getBody());
    return map;
  }

  @TrackExecutionTime
  public void methodA() {
    log.info("Method A is called");
  }
}
