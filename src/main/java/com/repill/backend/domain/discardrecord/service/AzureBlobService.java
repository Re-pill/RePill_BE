package com.repill.backend.domain.discardrecord.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AzureBlobService {

    private final BlobServiceClient blobServiceClient;

    private BlobContainerClient getContainerClient(String containerName) {
        return blobServiceClient.getBlobContainerClient(containerName);
    }

    public String uploadFile(String containerName, MultipartFile file) throws IOException {
        BlobContainerClient containerClient = getContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        return blobClient.getBlobUrl();
    }

    public byte[] downloadFile(String containerName, String fileName) {
        BlobContainerClient containerClient = getContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        return blobClient.downloadContent().toBytes();
    }
}
