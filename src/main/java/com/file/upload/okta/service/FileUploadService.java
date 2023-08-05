package com.file.upload.okta.service;

import com.file.upload.okta.advices.TrackExecutionTime;
import com.file.upload.okta.restmodel.UploadApiRequest;
import com.file.upload.okta.restmodel.UploadApiResponse;

public interface FileUploadService {

  UploadApiResponse fileUpload(UploadApiRequest uploadApiRequest);
}
