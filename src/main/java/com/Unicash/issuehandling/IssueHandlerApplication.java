package com.Unicash.issuehandling;

import com.Unicash.issuehandling.security.other.RoleEnum;
import com.Unicash.issuehandling.security.other.UserInfo;
import com.Unicash.issuehandling.security.other.UserInfoUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RequiredArgsConstructor
public class IssueHandlerApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(IssueHandlerApplication.class, args);
	}


	@Bean
	CommandLineRunner run(	BCryptPasswordEncoder passwordEncoder, UserInfoUserDetailsService userDetailsService) {
		return args -> {
			try {

				userDetailsService.saveUser(new UserInfo(1L, "dev1", "0911223344", "dev@me.com","pass", RoleEnum.DEVELOPER));
				userDetailsService.saveUser(new UserInfo(2L, "admin1", "0922223355", "admin@me.com","pass", RoleEnum.ADMIN));
				userDetailsService.saveUser(new UserInfo(3L, "sup1", "0933223366", "sup@me.com","pass", RoleEnum.SUPPORT));
				System.out.println("-----nnn");
			} catch (Exception e) {
				System.out.println("---------------Init populating problem.------------------");

			}
		};
	}

}
