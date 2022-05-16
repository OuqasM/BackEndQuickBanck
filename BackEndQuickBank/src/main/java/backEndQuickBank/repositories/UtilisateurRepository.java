package backEndQuickBank.repositories;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backEndQuickBank.entities.RoleEnum;
import backEndQuickBank.entities.Utilisateur;
@Repository
@Primary
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{

	List<Utilisateur> findByRole(RoleEnum role);
	Utilisateur findByUsername(String username);
}
