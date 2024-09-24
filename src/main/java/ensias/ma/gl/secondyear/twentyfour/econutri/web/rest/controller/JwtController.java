package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.request.JwtCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.JwtService;


@RestController
@RequestMapping("/jwts")
public class JwtController {
    
    private JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping
    public ResponseEntity<?> createJwt(@RequestBody JwtCreationRequest jwtRequest) throws Exception {
        String jwt = jwtService.createJwt(jwtRequest);
        
        return new ResponseEntity<>(jwt, HttpStatus.CREATED);
    }
}
