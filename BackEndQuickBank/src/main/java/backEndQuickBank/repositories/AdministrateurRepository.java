package backEndQuickBank.repositories;

import org.springframework.stereotype.Repository;

import backEndQuickBank.entities.Administrateur;


@Repository
public interface AdministrateurRepository extends UtilisateurRepository{
	Administrateur findByUsername(String username);
}
