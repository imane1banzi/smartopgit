package projet.pfe.smartop.services;

import projet.pfe.smartop.dtos.UtilisateurDTO;

import java.util.List;
import java.util.Optional;

public interface IUtilisateurservice {
    UtilisateurDTO adduser(UtilisateurDTO utilisateurDTO);
    UtilisateurDTO updateuser(Integer id, UtilisateurDTO utilisateurDTO);
    List<UtilisateurDTO> getusers();
    String deleteuser(Integer id);
   Optional<UtilisateurDTO> seconnecter(String login,String password);
   UtilisateurDTO finduserbymatricule(String matricule);
   UtilisateurDTO chercherUtilisateurParId(Integer id);

}
