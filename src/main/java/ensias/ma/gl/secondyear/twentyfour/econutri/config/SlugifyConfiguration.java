package ensias.ma.gl.secondyear.twentyfour.econutri.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.slugify.Slugify;


@Configuration
public class SlugifyConfiguration {
    
    @Bean
    public Slugify slugify() {
        return Slugify.builder().build();
    }
}
