package backEndQuickBank.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import backEndQuickBank.requests.TypeCompte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Compte {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numCompte;
	private Date dateCreation;
	private double solde=0;
	private TypeCompte tyepCompte;
	private boolean damandeSuppression=false;
	private boolean damandeCarte=false;
	@ManyToOne
	private Client client;

}
