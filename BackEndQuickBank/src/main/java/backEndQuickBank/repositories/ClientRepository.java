package backEndQuickBank.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import backEndQuickBank.entities.Client;
@Repository
public interface ClientRepository extends UtilisateurRepository {
	Client findByUsername(String username);
	List<Client> findByDemandeSuppression(boolean etat);
}
