package davidev.transacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class StartUp {
	public static void main(String[] args) {
		SpringApplication.run(StartUp.class, args);
	}
}
