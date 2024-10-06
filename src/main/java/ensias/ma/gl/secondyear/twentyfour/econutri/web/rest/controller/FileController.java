package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.service.StorageService;


@RestController
public class FileController {

    
    private StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }
    

    @GetMapping("/{filename}")
    public ResponseEntity<?> getFile(@PathVariable("filename") String filename) {
        var file = this.storageService.getFile(filename);
        Resource fileAsResource = new FileSystemResource(file);

        MediaType mediaType = MediaTypeFactory.getMediaType(fileAsResource)
            .orElse(MediaType.APPLICATION_OCTET_STREAM);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        return new ResponseEntity<>(fileAsResource, headers, HttpStatus.OK);

    }
    
}
