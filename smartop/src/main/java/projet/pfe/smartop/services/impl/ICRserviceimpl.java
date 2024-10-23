package projet.pfe.smartop.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projet.pfe.smartop.config.CustomModelMapper;
import projet.pfe.smartop.daos.CRDAO;
import projet.pfe.smartop.daos.OperationDAO;
import projet.pfe.smartop.dtos.CRDTO;
import projet.pfe.smartop.dtos.OperationDTO;
import projet.pfe.smartop.entites.CR;
import projet.pfe.smartop.entites.Operation;
import projet.pfe.smartop.exceptions.BusinessException;
import projet.pfe.smartop.services.ICRservice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ICRserviceimpl implements ICRservice {
    private final CustomModelMapper customModelMapper;
    private final CRDAO crdao;
    @Override
    public CRDTO addCR(CRDTO crdto) {
        try{
            CR crbo= this.customModelMapper.modelMapper().map(crdto,CR.class);
            var savedcrBo= this.crdao.save(crbo);
            return customModelMapper.modelMapper().map(savedcrBo, CRDTO.class);
        }catch (Exception e) {

            return null;
        }
    }

    @Override
    public CRDTO updateCR(Integer id, CRDTO crdto) {
        CR crbo= this.customModelMapper.modelMapper().map(crdto,CR.class);
        CR crFound = crdao.findAll().stream().filter(bo -> bo.getId().equals(id)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No operation with identity [%s] exist !", id))
        );
        crbo.setId(crFound.getId());
        crbo.setId(id);
        CRDTO updateCRResponse = this.customModelMapper.modelMapper().map(crdao.save(crbo), CRDTO.class);
        return updateCRResponse;
    }

    @Override
    public String deleteCR(Integer id) {
        try {
            crdao.deleteById(id);
        }
        catch (Exception ignored){}
        return null;
    }

    @Override

    public List<CRDTO> findCRbyIDOP(Integer id) {
        log.info("Fetching all CRs from the database.");
        List<CR> crList = crdao.findAll();

        log.info("Filtering CRs by operation ID: {}", id);
        List<CR> filteredCRs = crList.stream()
                .filter(bo -> {
                    if (bo.getOperation() != null && bo.getOperation().getId() != null) {
                        return bo.getOperation().getId().equals(id);
                    }
                    return false;
                })
                .toList();

        if (filteredCRs.isEmpty()) {
            log.error("No operation with identity [{}] exist !", id);
            throw new BusinessException(String.format("No operation with identity [%s] exist !", id));
        }

        log.info("Mapping CR entities to CRDTOs.");
        List<CRDTO> crdtoList = filteredCRs.stream()
                .map(cr -> {
                    try {
                        return customModelMapper.modelMapper().map(cr, CRDTO.class);
                    } catch (Exception e) {
                        log.error("Error mapping CR to CRDTO: ", e);
                        throw new BusinessException("Mapping error");
                    }
                })
                .collect(Collectors.toList());

        log.info("Returning {} CRDTOs", crdtoList.size());
        return crdtoList;
    }

    @Override
    public List<CRDTO> findCRbydemandeur(String demandeur) {
        log.info("Fetching all CRs from the database.");
        List<CR> crList = crdao.findAll();

        log.info("Filtering CRs by operation demandeur: {}", demandeur);
        List<CR> filteredCRs = crList.stream()
                .filter(bo -> {
                    if (bo.getOperation() != null && bo.getOperation().getDemandeur() != null) {
                        return bo.getOperation().getDemandeur().equals(demandeur);
                    }
                    return false;
                })
                .toList();

        if (filteredCRs.isEmpty()) {
            log.error("No operation with demandeur [{}] exist !", demandeur);
            throw new BusinessException(String.format("No operation with demandeur [%s] exist !", demandeur));
        }

        log.info("Mapping CR entities to CRDTOs.");
        List<CRDTO> crdtoList = filteredCRs.stream()
                .map(cr -> {
                    try {
                        return customModelMapper.modelMapper().map(cr, CRDTO.class);
                    } catch (Exception e) {
                        log.error("Error mapping CR to CRDTO: ", e);
                        throw new BusinessException("Mapping error");
                    }
                })
                .collect(Collectors.toList());

        log.info("Returning {} CRDTOs", crdtoList.size());
        return crdtoList;
    }

    @Override
    public  List<CRDTO> findCRbycategorieop(String categorie) {
        log.info("Fetching all CRs from the database.");
        List<CR> crList = crdao.findAll();

        log.info("Filtering CRs by operation demandeur: {}", categorie);
        List<CR> filteredCRs = crList.stream()
                .filter(bo -> {
                    if (bo.getOperation() != null && bo.getOperation().getCategorie() != null) {
                        return bo.getOperation().getCategorie().equals(categorie);
                    }
                    return false;
                })
                .toList();

        if (filteredCRs.isEmpty()) {
            log.error("No operation with categorie [{}] exist !", categorie);
            throw new BusinessException(String.format("No operation with categorie [%s] exist !", categorie));
        }

        log.info("Mapping CR entities to CRDTOs.");
        List<CRDTO> crdtoList = filteredCRs.stream()
                .map(cr -> {
                    try {
                        return customModelMapper.modelMapper().map(cr, CRDTO.class);
                    } catch (Exception e) {
                        log.error("Error mapping CR to CRDTO: ", e);
                        throw new BusinessException("Mapping error");
                    }
                })
                .collect(Collectors.toList());

        log.info("Returning {} CRDTOs", crdtoList.size());
        return crdtoList;
    }

    @Override
    public List<CRDTO> getCR() {
        return crdao.findAll().stream().
                map(cr -> this.customModelMapper.modelMapper().map(cr, CRDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public CRDTO exportCR() {
        return null;
    }
}
