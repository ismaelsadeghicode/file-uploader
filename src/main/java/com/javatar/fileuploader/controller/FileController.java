package com.javatar.fileuploader.controller;

import com.javatar.fileuploader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tempuri.ResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@CrossOrigin(origins = "*", maxAge = 3600)
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

//    @PostMapping(value = "/upload")
//    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
//
//        String fileName = fileService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//
//        return new UploadFileResponse(
//                fileName,
//                fileDownloadUri,
//                file.getContentType(),
//                file.getSize());
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileService.loadFileAsResource(fileName);
        String contentType = null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename='\"" + resource.getFilename() + "'\"")
                .body(resource);
    }

}












