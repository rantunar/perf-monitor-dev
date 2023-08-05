package com.file.upload.okta.restmodel;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class UploadApiResponse {
  @NonNull Boolean status;
  @NonNull String message;
}
