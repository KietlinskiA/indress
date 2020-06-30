package pl.kietlinski.indress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IndressApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndressApplication.class, args);
    }

}
