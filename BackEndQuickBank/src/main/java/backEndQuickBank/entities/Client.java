package backEndQuickBank.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Client extends Utilisateur{

	private String nom;
	private String prenom;
	private String mail;
	private boolean confirme=false;
	private boolean demandeSuppression=false;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	private Administrateur administrateur;
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private Collection<Compte> comptes;
	@OneToMany(mappedBy = "client")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<Post> postes;
}
