package com.file.upload.okta.restmodel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class UploadApiRequest {

  @NotNull @NotEmpty private String clientIdentifier;

  @NotNull @NotEmpty private String rmNumber;

  @NotNull MultipartFile file;
}
