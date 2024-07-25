package marko.gdv.transformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GdvTransformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GdvTransformerApplication.class, args);
    }

}
