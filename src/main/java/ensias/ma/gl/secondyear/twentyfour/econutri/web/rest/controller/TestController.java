package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ensias.ma.gl.secondyear.twentyfour.econutri.request.OfferCreationRequest;


@RestController
@RequestMapping("/tests")
public class TestController {

    private String imageStorageDirectoryPath ="/home/mehdi/econutri-images";
    
    @PostMapping
    public ResponseEntity<?> test(
        OfferCreationRequest offerRequest,
        @RequestParam("image") MultipartFile image) {

        try {
            File destinationFile = new File(imageStorageDirectoryPath + "/" + image.getOriginalFilename());
            image.transferTo(destinationFile);

        } catch(IOException exception) {
            exception.printStackTrace();
        }

    
        return new ResponseEntity<>(offerRequest, HttpStatus.ALREADY_REPORTED);
    }
}
