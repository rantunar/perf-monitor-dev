package com.file.upload.okta.service;

import com.file.upload.okta.advices.TrackExecutionTime;
import com.file.upload.okta.client.OktaClient;
import com.file.upload.okta.restmodel.UploadApiRequest;
import com.file.upload.okta.restmodel.UploadApiResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

  @NonNull private final OktaClient oktaClient;

  @TrackExecutionTime
  @Override
  public UploadApiResponse fileUpload(UploadApiRequest uploadApiRequest) {
    oktaClient.methodA();
    var path = new FileSystemResource("").getFile().getAbsolutePath();
    try (FileOutputStream fileOutputStream =
        new FileOutputStream(path)) {
      fileOutputStream.write(uploadApiRequest.getFile().getBytes());
      return UploadApiResponse.builder().status(true).message("File uploaded successfully").build();
    } catch (final IOException e) {
      return UploadApiResponse.builder().status(false).message(e.getMessage()).build();
    }
  }
}
