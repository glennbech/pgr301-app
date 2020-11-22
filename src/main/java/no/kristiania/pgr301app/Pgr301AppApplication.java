package no.kristiania.pgr301app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Pgr301AppApplication {
	public static void main(String[] args) {
		SpringApplication.run(Pgr301AppApplication.class, args);
	}

}
