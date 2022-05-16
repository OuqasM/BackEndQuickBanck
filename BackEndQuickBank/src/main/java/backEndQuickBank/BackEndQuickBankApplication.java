package backEndQuickBank;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import backEndQuickBank.requests.AdministrateurRequests;
import backEndQuickBank.requests.ClientRequest;
import backEndQuickBank.services.AdministrateurService;
import backEndQuickBank.services.ClientService;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true ,securedEnabled = true)
public class BackEndQuickBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndQuickBankApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	CommandLineRunner start(ClientService cs , AdministrateurService as) {
		return args -> {
			ClientRequest c = new ClientRequest();
			c.setMail("mehdi@ouqas");
			c.setNom("Ouqas");
			c.setUsername("username");
			c.setMotdepasse("pwd");
			c.setPrenom("el mahdi");
			
			cs.ajouterClient(c);
			
			
			AdministrateurRequests a = new AdministrateurRequests();
			a.setNomComplet("El mahdi ouqas");
			a.setUsername("admin");
			a.setMotdepasse("pwd");
			as.ajouterAdministrateur(a);
		};
	}
}
