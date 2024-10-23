package projet.pfe.smartop.controlleurs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projet.pfe.smartop.dtos.UtilisateurDTO;
import projet.pfe.smartop.services.IUtilisateurservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("SmartOP/")
@Slf4j
@RequiredArgsConstructor
public class UtilisateurRestController {

    private final IUtilisateurservice iUtilisateurservice;

    @PostMapping("nouveauUser")
    void creerNouveauuser(@RequestBody UtilisateurDTO utilisateurDto){


        UtilisateurDTO utilisateur = iUtilisateurservice.adduser(utilisateurDto);
    }
    @GetMapping("listUsers")
    public  List<Map<String, Object>> getListusers() {
        log.info("Liste users ");
    List<UtilisateurDTO> utilisateurDTOList =this.iUtilisateurservice.getusers();
        List<Map<String, Object>> result = new ArrayList<>();
        for (UtilisateurDTO utilisateurDTO : utilisateurDTOList) {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("id", utilisateurDTO.getId());
            jsonObject.put("nom", utilisateurDTO.getNom());
            jsonObject.put("prenom", utilisateurDTO.getPrenom());
            jsonObject.put("adresseMail", utilisateurDTO.getAdresseMail());
            jsonObject.put("matricule", utilisateurDTO.getMatricule());
            jsonObject.put("enumType_utilisateur", utilisateurDTO.getEnumType_utilisateur());
            result.add(jsonObject);
        }
        return result;

    }
    @PutMapping("updateUser")
    void updateuser(@RequestBody UtilisateurDTO utilisateurDTO, @RequestParam Integer id){
        UtilisateurDTO utilisateur = iUtilisateurservice.updateuser(id,utilisateurDTO);
    }
    @DeleteMapping("deleteUser")
    public String deleteOP(@RequestParam Integer id) {
        return this.iUtilisateurservice.deleteuser(id);

    }


}
