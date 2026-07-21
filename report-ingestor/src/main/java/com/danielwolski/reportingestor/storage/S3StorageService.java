package com.danielwolski.reportingestor.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3StorageService implements StorageService {

    @Override
    public String store(MultipartFile file) {
        return "https://test-aws-s3-bucket.com/";
    }
}
