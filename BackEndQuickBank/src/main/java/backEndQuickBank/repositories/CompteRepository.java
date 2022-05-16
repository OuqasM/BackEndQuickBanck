package backEndQuickBank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backEndQuickBank.entities.Client;
import backEndQuickBank.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long>{
	Compte findByNumCompte(String numCompte);
	List<Compte> findByClient(Client client);
	List<Compte> findByDamandeSuppression(boolean mustBeTrue);
	List<Compte> findByDamandeCarte(boolean mustBeTrue);
	
}
