package br.com.studies.rabbitmqstart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqStartApplication {

	@Profile("usage_message")
	@Bean
	public CommandLineRunner usage() {
		return args -> {
			System.out.println("Este app usa Spring Profiles " +
					"para controlar seu comportamento.\n");
			System.out.println("Uso: java -jar" +
					"build/libs/rabbitmq-start-0.0.1-SNAPSHOT.jar" +
					"--spring.profiles.active=hello-world, sender");
		};
	}

	@Profile("!usage_message")
	@Bean
	public CommandLineRunner tutorial() {
		return new RabbitmqStartRunner();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitmqStartApplication.class, args);
	}

}
