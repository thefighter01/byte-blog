package com.fighter.byteblog;

import com.fighter.byteblog.domain.entities.User;
import com.fighter.byteblog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class ByteblogApplication implements CommandLineRunner {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepo;


	public static void main(String[] args) {
		SpringApplication.run(ByteblogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String email = "user@test.com";
		userRepo.findByEmail(email).orElseGet(()-> {
			User newUser = User.builder().email(email).username("test user").password(passwordEncoder.encode("password")).build();
			return userRepo.save(newUser);
		});

	}
}
