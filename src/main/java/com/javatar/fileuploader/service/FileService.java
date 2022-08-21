package com.javatar.fileuploader.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.tempuri.Error;
import org.tempuri.*;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {

    private final Path fileStorageLocation;

    public FileService() {
        this.fileStorageLocation = Paths.get("file")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            //
        }
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace(); //TODO
        }
        return fileName;
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                // TODO
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseDTO startValidationService(MultipartFile file,
                                              String fileName,
                                              String fileFormat,
                                              String issueTracking,
                                              String extorgCode,
                                              String extorgName,
                                              String protocolName,
                                              String userName) {
        DCMSServicePortBindingQSServiceLocator ser = new DCMSServicePortBindingQSServiceLocator();
        ResponseDTO rtdo = new ResponseDTO();
        try {
            DCMSService x = ser.getDCMSServicePortBindingQSPort();
            byte[] fileBytes = file.getBytes();
            rtdo = x.startValidationService(
                    fileBytes,
                    fileName,
                    FileFormatEnum.fromString(fileFormat),
                    issueTracking == null ? Long.parseLong(issueTracking) : null,
                    extorgCode,
                    extorgName,
                    protocolName,
                    userName);
            System.out.println("response status is :" + rtdo.getStatus());
            System.out.println("issuetracking is :" + rtdo.getIssueTracking());
            if (rtdo.getErrors() != null) {
                System.out.println("errors :" + rtdo.getErrors().length);
                org.tempuri.Error[] var9;
                int var8 = (var9 = rtdo.getErrors()).length;

                for (int var7 = 0; var7 < var8; ++var7) {
                    Error er = var9[var7];
                    System.out.println(" error type :" + er.getErrorType());
                    System.out.println(" error table name:" + er.getErrorTableName());
                    System.out.println(" error record number :" + er.getErrorRecordNumber());
                    System.out.println(" error title:" + er.getErrorTitle());
                }
            }
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }

        return rtdo;
    }
}
