package com.javatar.fileuploader.controller;

import com.javatar.fileuploader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tempuri.ResponseDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload")
    public ResponseDTO uploadFile(@RequestParam("file") MultipartFile file,
                                  @RequestParam("fileName") String fileName,
                                  @RequestParam("fileFormat") String fileFormat,
                                  @RequestParam("issueTracking") String issueTracking,
                                  @RequestParam("extorgCode") String extorgCode,
                                  @RequestParam("extorgName") String extorgName,
                                  @RequestParam("protocolName") String protocolName,
                                  @RequestParam("userName") String userName) {
        System.out.println("::::::::::::"); // TODO

        return fileService.startValidationService(
                file,
                fileName,
                fileFormat,
                issueTracking,
                extorgCode,
                extorgName,
                protocolName,
                userName);
    }

}












