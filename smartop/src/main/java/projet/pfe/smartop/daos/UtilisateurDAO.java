package projet.pfe.smartop.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.pfe.smartop.entites.Utilisateur;

import java.util.Optional;

public interface UtilisateurDAO extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findByMatricule(String matricule);
}
