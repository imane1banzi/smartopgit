package projet.pfe.smartop.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import projet.pfe.smartop.config.CustomModelMapper;
import projet.pfe.smartop.daos.OperationDAO;
import projet.pfe.smartop.dtos.OperationDTO;
import projet.pfe.smartop.dtos.UtilisateurDTO;
import projet.pfe.smartop.entites.Operation;
import projet.pfe.smartop.entites.Utilisateur;
import projet.pfe.smartop.exceptions.BusinessException;
import projet.pfe.smartop.services.IOperationservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IOperationserviceimpl implements IOperationservice {
    private static final Logger logger = LoggerFactory.getLogger(IOperationserviceimpl.class);

    private final CustomModelMapper customModelMapper;
    private final OperationDAO operationDAO;
    @Override
    public OperationDTO addOP(OperationDTO operationDTO) {
        try{
            Operation operationbo= this.customModelMapper.modelMapper().map(operationDTO,Operation.class);
            var savedOperationBo= this.operationDAO.save(operationbo);
            return customModelMapper.modelMapper().map(savedOperationBo, OperationDTO.class);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public OperationDTO updateOP(Integer id, OperationDTO operationDTO) {

        Operation operationbo= this.customModelMapper.modelMapper().map(operationDTO,Operation.class);
        Operation operationFound = operationDAO.findAll().stream().filter(bo -> bo.getId().equals(id)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No operation with identity [%s] exist !", id))
        );
        operationbo.setId(operationFound.getId());
        operationbo.setId(id);
        OperationDTO updateoperationResponse = this.customModelMapper.modelMapper().map(operationDAO.save(operationbo), OperationDTO.class);
        return updateoperationResponse;
    }

    @Override
    public String deleteOP(Integer id) {


    /*  if (id == null)
            throw new BusinessException("Enter a correct identity customer");
        Operation operationFound = operationDAO.findAll().stream().filter(op -> op.getId_operation().equals(id)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No OP with identity %s exist in database", id))
        );
        operationDAO.delete(operationFound);
        return String.format("Operation with identity %s is deleted with success", id);
        try {
            operationDAO.deleteById(id);
        }
      catch (Exception ignored){}
   return null;
        try {
            // Trouver l'opération
            logger.info("Searching for operation with id: {}", id);
            Operation operation = operationDAO.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Operation not found"));

            // Log avant de détacher l'utilisateur
            logger.info("Found operation with id: {}", id);

            // Détacher l'utilisateur et sauvegarder cette modification
            if (operation.getUtilisateur() != null) {
                logger.info("Detaching user from operation with id: {}", id);
                operation.setUtilisateur(null);
                operationDAO.save(operation); // Sauvegarder l'opération sans utilisateur

                // Vérification après détachement et sauvegarde
                Operation detachedOperation = operationDAO.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Operation not found after detaching user"));
                if (detachedOperation.getUtilisateur() != null) {
                    logger.error("Failed to detach user from operation with id: {}", id);
                    return "Failed to detach user from operation.";
                }
            }

            // Supprimer l'opération
            logger.info("Deleting operation with id: {}", id);
            operationDAO.delete(operation);

            logger.info("Operation with id: {} deleted successfully", id);
            return "Operation deleted successfully";
        } catch (DataIntegrityViolationException e) {
            // Gérer le cas où l'opération est liée à un utilisateur existant
            logger.error("Cannot delete operation: it is linked to an existing user.", e);
            return "Cannot delete operation: it is linked to an existing user.";
        } catch (Exception e) {
            // Gérer les autres exceptions
            logger.error("An error occurred while deleting the operation.", e);
            return "An error occurred while deleting the operation.";
        }*/
        try {
            operationDAO.deleteById(id);
        }
        catch (Exception ignored){}
        return null;
    }

    @Override
    public OperationDTO findOPbyIDOP(Integer id) {
        Operation operation = operationDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found"));
        return customModelMapper.modelMapper().map(operation, OperationDTO.class);
    }

    @Override
    public OperationDTO findOPbydemandeur(String demandeur) {
        Operation operationFound = operationDAO.findAll().stream().filter(bo -> bo.getDemandeur().equals(demandeur)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No operation with identity [%s] exist !", demandeur)));
        return customModelMapper.modelMapper().map(operationFound, OperationDTO.class);
    }

    @Override
    public OperationDTO findOPbycategorieop(String categorie) {
        Operation operationFound = operationDAO.findAll().stream().filter(bo -> bo.getCategorie().equals(categorie)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No operation with identity [%s] exist !", categorie)));
        return customModelMapper.modelMapper().map(operationFound, OperationDTO.class);
    }

    @Override
    public List<OperationDTO> getOP() {
        return operationDAO.findAll().stream().
                map(op -> this.customModelMapper.modelMapper().map(op, OperationDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public OperationDTO exportOP() {
        return null;
    }

    @Override
    public OperationDTO chercherOPparID(Integer id) {
        Optional<Operation> optionalOP = this.operationDAO.findById(id);
        if (optionalOP.isPresent()) {
            Operation OPBo = optionalOP.get();
            return customModelMapper.modelMapper().map(OPBo, OperationDTO.class);
        }
        return null;
    }

    @Override
    public OperationDTO chercherOPParCodeOP(String codeOP) {
        Operation operationFound = operationDAO.findAll().stream().filter(bo -> bo.getCodeOP().equals(codeOP)).findFirst().orElseThrow(
                () -> new BusinessException(String.format("No operation with codeOP [%s] exist !", codeOP)));
        return customModelMapper.modelMapper().map(operationFound, OperationDTO.class);
    }
}
