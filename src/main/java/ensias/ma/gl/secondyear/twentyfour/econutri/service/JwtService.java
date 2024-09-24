package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.Date;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import ensias.ma.gl.secondyear.twentyfour.econutri.exception.EmailNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.IncorrectPasswordException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.JwtCreationRequest;


@Component
public class JwtService {

    private String signingKey;

    private Long jwtLifespanInHours;
    
    private UserService userService;
    

    public JwtService(UserService userService, 
                      @Value("${jwt.lifespan}") Long jwtLifespan,
                      @Value("${jwt.signingKey}") String signingKey) {
        
        this.signingKey = signingKey;
        this.jwtLifespanInHours = jwtLifespan;
        this.userService = userService;

    }


    public String createJwt(JwtCreationRequest jwtRequest) throws EmailNotFoundException, IncorrectPasswordException {
        String email = jwtRequest.getEmail();
        String password = jwtRequest.getPassword();

        User user = this.userService.findUserByEmailAndPassword(email, password);

        Date currentDate = new Date();
        Date jwtExpirationDate = new Date(currentDate.getTime() + jwtLifespanInHours * 3600 * 1000);

        String jwt = Jwts.builder()
            .issuer("www.econutri.com")
            .issuedAt(currentDate)
            .expiration(jwtExpirationDate)
            .subject(email)
            .claim("role", "role_placeholder") // to be retrieved later from user
            .signWith(Keys.hmacShaKeyFor(this.signingKey.getBytes(StandardCharsets.UTF_8)))
            .compact();

        return jwt;
    }
}
