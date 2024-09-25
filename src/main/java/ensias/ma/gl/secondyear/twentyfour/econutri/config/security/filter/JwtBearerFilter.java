package ensias.ma.gl.secondyear.twentyfour.econutri.config.security.filter;

import java.util.List;
import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.JwtService;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserRoleGetter;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.JwtVerificationException;


@Component
public class JwtBearerFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private UserRoleGetter userRoleGetter;

    public JwtBearerFilter(JwtService jwtService, UserRoleGetter userRoleGetter) {
        this.jwtService = jwtService;
        this.userRoleGetter = userRoleGetter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring("Bearer".length());
        try {
            User user = this.jwtService.extractUserFromJwt(jwt);

            var authorities = List.of((GrantedAuthority) () -> "ROLE_" + userRoleGetter.getUserRole(user));
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, jwt, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch(JwtVerificationException exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().print(exception.getMessage());

            return;
        }
    }

    
    
}
