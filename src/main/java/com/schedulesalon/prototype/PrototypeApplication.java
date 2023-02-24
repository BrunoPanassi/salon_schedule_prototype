package com.schedulesalon.prototype;

import com.schedulesalon.prototype.model.Manager;
import com.schedulesalon.prototype.model.Person;
import com.schedulesalon.prototype.model.Role;
import com.schedulesalon.prototype.service.ManagerService;
import com.schedulesalon.prototype.service.PersonService;
import com.schedulesalon.prototype.service.PersonServiceImpl;
import com.schedulesalon.prototype.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrototypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrototypeApplication.class, args);
	}

	//@Bean
	CommandLineRunner run(PersonService personService, RoleService roleService){
		return args -> {
			/*
			roleService.saveRole(new Role("USER"));
			roleService.saveRole(new Role("PROFESSIONAL"));
			roleService.saveRole(new Role("MANAGER"));
			roleService.saveRole(new Role("ADMIN"));

			personService.savePerson(new Person("Bruno", "senha123", "18 997", "bruno@email.com"));
			personService.savePerson(new Person("Ronaldo", "corinthians", "55 997", "ronaldo@email.com"));
			 */
			//personService.addRoleToPerson("Ronaldo", "55 997", "ronaldo@email.com", "PROFESSIONAL");
			//personService.addRoleToPerson("Ronaldo", "55 997", "ronaldo@email.com","MANAGER");
		};
	}

}
