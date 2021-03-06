package backEndQuickBank.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Administrateur extends Utilisateur{
	
	private String nomComplet;
	@OneToMany(mappedBy = "administrateur")
	private Collection<Client> clients;
}
