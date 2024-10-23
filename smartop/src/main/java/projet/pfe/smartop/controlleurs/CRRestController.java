package projet.pfe.smartop.controlleurs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projet.pfe.smartop.dtos.CRDTO;
import projet.pfe.smartop.dtos.OperationDTO;
import projet.pfe.smartop.dtos.UtilisateurDTO;
import projet.pfe.smartop.services.ICRservice;
import projet.pfe.smartop.services.IOperationservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("SmartOP/")
@Slf4j
@RequiredArgsConstructor
public class CRRestController {
    private final ICRservice icRservice;
    private final IOperationservice iOperationservice;

    @PostMapping("nouveauCR")
    void creerNouveauCR(@RequestBody CRDTO crdto){
        OperationDTO operationDTO= this.iOperationservice
                .chercherOPParCodeOP(crdto.getCodeOP());
        crdto.setCodeOP(crdto.getCodeOP());
        crdto.setOperation(operationDTO);
        CRDTO cr = icRservice.addCR(crdto);

    }

    @GetMapping("listCR")
    public List<Map<String, Object>> getListCR() {
        log.info("Liste CR ");
        List<CRDTO> crdtoList =this.icRservice.getCR();
        List<Map<String, Object>> result = new ArrayList<>();
        for (CRDTO crdto : crdtoList) {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("dateFin", crdto.getDateFin());
            jsonObject.put("enumstatutCR", crdto.getEnumstatutCR());
            jsonObject.put("resume", crdto.getResume());
            jsonObject.put("equipementImp", crdto.getEquipementImp());
            jsonObject.put("codeOP", crdto.getCodeOP());
            jsonObject.put("id", crdto.getId());
            result.add(jsonObject);
        }
        return result;

    }
    @PutMapping("updateCR")
    void updateCR(@RequestBody CRDTO crdto, @RequestParam Integer id){
        CRDTO cr = icRservice.updateCR(id,crdto);
    }

    @GetMapping("findCRbyIDOP")
    public List<CRDTO> getListCRBYID(@RequestParam Integer id) {
        log.info("Fetching CR list for operation ID: {}", id);
        List<CRDTO> crList = this.icRservice.findCRbyIDOP(id);
        log.info("Fetched {} CR(s) for operation ID: {}", crList.size(), id);
        return new ArrayList<>(crList);

    }
    @GetMapping("findCRbydemandeur")
    public List<CRDTO> getListCRBYdemandeur(@RequestParam String demandeur) {
        log.info("Fetching CR list for operation demandeur: {}", demandeur);
        List<CRDTO> crList = this.icRservice.findCRbydemandeur(demandeur);
        log.info("Fetched {} CR(s) for operation demandeur: {}", crList.size(), demandeur);
        return new ArrayList<>(crList);

    }
    @GetMapping("findCRbycategorie")
    public List<CRDTO> getListCRBYcategorie(@RequestParam String categorie) {
        log.info("Fetching CR list for operation categorie: {}", categorie);
        List<CRDTO> crList = this.icRservice.findCRbycategorieop(categorie);
        log.info("Fetched {} CR(s) for operation categorie: {}", crList.size(), categorie);
        return new ArrayList<>(crList);

    }
    @DeleteMapping("deleteCR")
    public String deleteCR(@RequestParam Integer id) {
        return this.icRservice.deleteCR(id);

    }
}
