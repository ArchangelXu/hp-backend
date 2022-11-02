package tech.mingxi.hp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication


@EnableScheduling
@ConfigurationPropertiesScan("tech.mingxi.hp.backend.properties")
public class HpBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HpBackendApplication.class, args);
	}

}
