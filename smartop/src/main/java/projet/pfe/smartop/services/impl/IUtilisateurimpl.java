package projet.pfe.smartop.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projet.pfe.smartop.config.CustomModelMapper;
import projet.pfe.smartop.daos.UtilisateurDAO;
import projet.pfe.smartop.dtos.UtilisateurDTO;
import projet.pfe.smartop.entites.Utilisateur;
import projet.pfe.smartop.exceptions.BusinessException;
import projet.pfe.smartop.services.IUtilisateurservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IUtilisateurimpl implements IUtilisateurservice {

    private final CustomModelMapper customModelMapper;
    private final UtilisateurDAO utilisateurDAO;

    @Override
    public UtilisateurDTO adduser(UtilisateurDTO utilisateurDTO) {
          try{
              Utilisateur utilisateurbo= this.customModelMapper.modelMapper().map(utilisateurDTO,Utilisateur.class);
              var savedUtilisateurBo=this.utilisateurDAO.save(utilisateurbo);
              return customModelMapper.modelMapper().map(savedUtilisateurBo,UtilisateurDTO.class);
          }catch (Exception e){
              return null;
          }

    }

    @Override
    public UtilisateurDTO updateuser(Integer id, UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateurbo= this.customModelMapper.modelMapper().map(utilisateurDTO,Utilisateur.class);
        Utilisateur utilisateurFound = utilisateurDAO.findAll().stream().filter(bo -> bo.getId().equals(id)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No Customer with identity [%s] exist !", id))
        );
        utilisateurbo.setId(utilisateurFound.getId());
        utilisateurbo.setId(id);
        UtilisateurDTO updateutilisateurResponse = this.customModelMapper.modelMapper().map(utilisateurDAO.save(utilisateurbo), UtilisateurDTO.class);
        return updateutilisateurResponse;
    }

    @Override
    public List<UtilisateurDTO> getusers() {
        return utilisateurDAO.findAll().stream().
                map(user -> this.customModelMapper.modelMapper().map(user, UtilisateurDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public String deleteuser(Integer id) {
        if (id == null)
            throw new BusinessException("Enter a correct identity customer");
        Utilisateur userFound = utilisateurDAO.findAll().stream().filter(user -> user.getId().equals(id)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No user with identity %s exist in database", id))
        );
        utilisateurDAO.delete(userFound);
        return String.format("Customer with identity %s is deleted with success", id);
    }

    @Override
    public Optional<UtilisateurDTO> seconnecter(String login, String password) {
        return Optional.empty();
    }

    @Override
    public UtilisateurDTO finduserbymatricule(String matricule) {
        Utilisateur utilisateurFound = utilisateurDAO.findAll().stream().filter(bo -> bo.getMatricule().equals(matricule)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No user with matricul [%s] exist !", matricule)));

       return  this.customModelMapper.modelMapper().map(utilisateurFound,UtilisateurDTO.class);
    }

    @Override
    public  UtilisateurDTO chercherUtilisateurParId(Integer id) {
        Optional<Utilisateur> optionalUtilisateur = this.utilisateurDAO.findById(id);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateurBo = optionalUtilisateur.get();
            return customModelMapper.modelMapper().map(utilisateurBo, UtilisateurDTO.class);
        }
        return null;
    }


}
