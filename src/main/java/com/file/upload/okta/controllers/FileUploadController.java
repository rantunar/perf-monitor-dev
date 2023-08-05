package com.file.upload.okta.controllers;

import com.file.upload.okta.client.OktaClient;
import com.file.upload.okta.restmodel.UploadApiRequest;
import com.file.upload.okta.restmodel.UploadApiResponse;
import com.file.upload.okta.service.FileUploadService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/file")
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {

  @NonNull private FileUploadService fileUploadService;
  @NonNull private OktaClient oktaClient;

  @PostMapping(value = "/upload")
  public ResponseEntity<UploadApiResponse> uploadFile(
      @Valid @ModelAttribute UploadApiRequest uploadApiRequest, BindingResult bindingResult)
      throws IOException {
    if (bindingResult.hasErrors()) {
      Map<String, String> errors = new HashMap<>();
      bindingResult
          .getAllErrors()
          .forEach(
              (error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
              });
      UploadApiResponse uploadApiResponse =
          UploadApiResponse.builder().status(false).message(errors.toString()).build();
      return new ResponseEntity<>(uploadApiResponse, HttpStatus.BAD_REQUEST);
    }
    if (uploadApiRequest.getFile().getBytes().length == 0) {
      UploadApiResponse uploadApiResponse =
          UploadApiResponse.builder().status(false).message("File is invalid").build();
      return new ResponseEntity<>(uploadApiResponse, HttpStatus.BAD_REQUEST);
    }
    UploadApiResponse uploadApiResponse = fileUploadService.fileUpload(uploadApiRequest);
    if (uploadApiResponse.getStatus())
      return new ResponseEntity<>(uploadApiResponse, HttpStatus.OK);
    else return new ResponseEntity<>(uploadApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping(value = "/token")
  public ResponseEntity<Map<String,String>> getAccessToken(@RequestParam String sessionToken) {
    return new ResponseEntity<>(oktaClient.getToken(sessionToken), HttpStatus.OK);
  }
}
