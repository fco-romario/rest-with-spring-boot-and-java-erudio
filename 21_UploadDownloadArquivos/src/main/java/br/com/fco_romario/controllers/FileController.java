package br.com.fco_romario.controllers;

import br.com.fco_romario.controllers.docs.FileControllerDocs;
import br.com.fco_romario.data.dto.UploadFileResponseDTO;
import br.com.fco_romario.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService service;

    @Override
    public UploadFileResponseDTO uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    public List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] file) {
        return List.of();
    }

    @Override
    public ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
