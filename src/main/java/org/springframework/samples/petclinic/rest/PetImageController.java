package org.springframework.samples.petclinic.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PetImageController {

    private static final Logger log = LoggerFactory.getLogger(PetImageController.class);

    @Autowired
    private ClinicService clinicService;

    @PostMapping("/uploadPhoto/{petId}")
    public UploadFileResponse uploadFile(@PathVariable("petId") int petId, @RequestParam("photo") MultipartFile file) {
        String fileName = clinicService.addPetPhoto(petId, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
            file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = new ByteArrayResource(clinicService.loadPetImage(fileName));
        String contentType = "image/png";

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "cat.png" + "\"")
            .body(resource);
    }
}
