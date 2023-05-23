package com.gasto.gasto;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class GastoApplication {

	@Autowired
	private GestorService gestorService;
	public static void main(String[] args) {
		SpringApplication.run(GastoApplication.class, args);
	}

	@PostConstruct
	public void precargarDatos() {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		Gestor gestor = Gestor.builder()
				.id(99999L)
				.rol("Administrador")
				.nombre("Administrador")
				.email("admin@gasto.com").build();
		gestor.setPassword(argon2.hash(1,1024,1, "99999"));
		if(gestorService.findById(gestor.getId()).isPresent()){
			gestorService.deleteById(gestor.getId());
		}
		gestorService.save(gestor);

	}

}
