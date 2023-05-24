package com.gasto.gasto;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Service.GestorService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Objects;

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
		Gestor superSu = Gestor.builder()
				.id(1010064467L)
				.rol("Administrador")
				.nombre("SuperSu")
				.email("super@gasto.com").build();
		superSu.setPassword(argon2.hash(1,1024,1, "1010064467"));
		if (gestorService.findById(superSu.getId()).isPresent()){
			gestorService.deleteById(superSu.getId());
		}
		gestorService.save(superSu);
		Gestor gestor = Gestor.builder()
				.id(99999L)
				.rol("Administrador")
				.nombre("Administrador")
				.email("admin@gasto.com").build();
		gestor.setPassword(argon2.hash(1,1024,1, "99999"));
		if(gestorService.findById(gestor.getId()).isPresent()){
			if (!Objects.equals(gestorService.findById(gestor.getId()).get().getEmail(), "admin@gasto.com")){
				gestorService.deleteById(gestor.getId());
				gestorService.save(gestor);
			}
			else {
			}

		}
		else {
			gestorService.save(gestor);
		}
	}

}
