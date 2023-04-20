package com.gopal.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gopal.blog.Repositories.RoleRepo;
import com.gopal.blog.config.AppConstants;
import com.gopal.blog.entities.Role;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		try {

			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setRole("ROLE_ADMIN");
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setRole("ROLE_NORMAL");

			List<Role> roles = List.of(role1, role2);
			List<Role> AllRoles = roleRepo.saveAll(roles);

			AllRoles.forEach(e -> {
				System.out.println(e.getRole());
			}

			);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
