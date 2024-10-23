package projet.pfe.smartop.controlleurs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projet.pfe.smartop.dtos.OperationDTO;
import projet.pfe.smartop.dtos.UtilisateurDTO;
import projet.pfe.smartop.services.IOperationservice;
import projet.pfe.smartop.services.IUtilisateurservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("SmartOP/")
@Slf4j
@RequiredArgsConstructor
public class OperationRestController {

    private final IOperationservice iOperationservice;
    private final IUtilisateurservice iUtilisateurservice;

    @PostMapping("nouvelleOP")
    void creerNouvelleOP(@RequestBody OperationDTO operationDTO){

        UtilisateurDTO utilisateurDto= this.iUtilisateurservice
                .finduserbymatricule(operationDTO.getMatricule());
        operationDTO.setMatricule(operationDTO.getMatricule());
        operationDTO.setUtilisateur(utilisateurDto);
        OperationDTO operation = iOperationservice.addOP(operationDTO);
    }
    @GetMapping("listOP")
    public  List<Map<String, Object>> getListOP() {
        log.info("Liste OP ");
        List<OperationDTO> operationDTOList =this.iOperationservice.getOP();
        List<Map<String, Object>> result = new ArrayList<>();
        for (OperationDTO operationDTO : operationDTOList) {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("titre", operationDTO.getTitre());
            jsonObject.put("dept", operationDTO.getDept());
            jsonObject.put("equipementImpacte", operationDTO.getEquipementImpacte());
            jsonObject.put("demandeur", operationDTO.getDemandeur());
            jsonObject.put("categorie", operationDTO.getCategorie());
            jsonObject.put("codeOP", operationDTO.getCodeOP());
            jsonObject.put("dateDemande", operationDTO.getDateDemande());
            jsonObject.put("datePrevisionelle", operationDTO.getDatePrevisionelle());
            jsonObject.put("derniereModification", operationDTO.getDerniereModification());
            jsonObject.put("commentaire", operationDTO.getCommentaire());
            jsonObject.put("enumStatutOP", operationDTO.getEnumStatutOP());
            jsonObject.put("matricule", operationDTO.getMatricule());
            jsonObject.put("id", operationDTO.getId());
            result.add(jsonObject);
        }
        return result;

    }
    @PutMapping("updateOP")
    void updateuser(@RequestBody OperationDTO operationDTO, @RequestParam Integer id){
        OperationDTO operation = iOperationservice.updateOP(id,operationDTO);
    }

    @GetMapping("findOPbyIDOP")
    public OperationDTO getListOPBYID(@RequestParam Integer id) {
        log.info("Liste OP ");
        return this.iOperationservice.findOPbyIDOP(id);

    }
    @GetMapping("findOPbydemandeur")
    public OperationDTO getListOPBYDemandeur(@RequestParam String demandeur) {
        log.info("Liste OP ");
        return this.iOperationservice.findOPbydemandeur(demandeur);

    }

    @GetMapping("findOPbycategorie")
    public OperationDTO getListOPBYcategorie(@RequestParam String categorie) {
        log.info("Liste OP ");
        return this.iOperationservice.findOPbycategorieop(categorie);

    }
    @DeleteMapping("deleteOP")
    public String deleteOP(@RequestParam Integer id) {
        return this.iOperationservice.deleteOP(id);

    }
}
