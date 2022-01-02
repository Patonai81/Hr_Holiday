package hu.webuni.hrholiday.szabi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {

    private String secret;
    private String algorythm;
    private Long validity;
    private String issuer;

}
