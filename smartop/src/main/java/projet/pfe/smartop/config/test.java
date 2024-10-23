package projet.pfe.smartop.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.pfe.smartop.services.ICRservice;
import projet.pfe.smartop.services.IOperationservice;
import projet.pfe.smartop.services.IUtilisateurservice;

import java.util.ArrayList;
import java.util.Date;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class test {
    private final IUtilisateurservice iUtilisateurservice;
    private final IOperationservice iOperationservice;
    private final ICRservice icRservice;
    @Bean
    CommandLineRunner commandLineRunner() {


        return args -> {
      /*    iUtilisateurservice.adduser(UtilisateurDTO.builder().
                    nom("testnom1").
                    prenom("testprenom1").
                    password("testpassword1").
                    adresseMail("testmail1").
                    matricule("testmatricule1").
                    groupe("testgroupe1").
                    enumType_utilisateur(EnumType_utilisateur.ADMIN).
                    listOperation(null).
                    build());

            iUtilisateurservice.updateuser(1,UtilisateurDTO.builder().
                    nom("testnom1").
                    prenom("testprenom1").
                    password("testpassword1").
                    adresseMail("testmail1").
                    matricule("testmatricule1").
                    groupe("testgroupe1").
                    enumType_utilisateur(EnumType_utilisateur.INGENIEUR).
                    listOperation(null).
                    build());
            iUtilisateurservice.deleteuser(1);
            System.out.println(iUtilisateurservice.getusers());
            System.out.println((iUtilisateurservice.finduserbymatricule("testmatricule")));
            iUtilisateurservice.deleteuser(2);
            iUtilisateurservice.deleteuser(52);
             iOperationservice.addOP(OperationDTO.builder().
                    titre("testtitre2").
                    dept("testdept2").
                    equipementImpacte("testequipement2").
                    demandeur("testdemandeur2").
                    categorie("testcategorie2").
                    dateDemande(new Date()).
                    datePrevisionelle(new Date()).
                    derniereModification("testdernieremodification2").
                    commentaire("testcommentaire2").
                    enumStatutOP(EnumStatutOP.PLANIFIE).
                    utilisateur(iUtilisateurservice.chercherUtilisateurParId(1)).
                    compterenduList(null).
                    build());

           iOperationservice.updateOP(1,OperationDTO.builder().
                   titre("testtitre2").
                   dept("testdept2").
                   equipement_impacte("testequipement2").
                   demandeur("testdemandeur2").
                   categorie("testcategorie2").
                   date_demande(new Date()).
                   date_previsionelle(new Date()).
                   derniere_modification("testdernieremodification2").
                   commentaire("testcommentaire2").
                   enumStatutOP(EnumStatutOP.ENCOURS).
                   utilisateur(null).
                   compterenduList(null).
                   build());

System.out.println(iOperationservice.findOPbydemandeur("testdemandeur"));

            iOperationservice.deleteOP(1);
            System.out.println(iOperationservice.findOPbycategorieop("testcategorie2"));
            System.out.println(iOperationservice.getOP());
            icRservice.addCR(CRDTO.builder().
                            dateFin(new Date()).
                            enumstatutCR(EnumstatutCR.TERMINESUCCES).
                            resume("testresume2").
                            equipementImp("testequipement2").
                            operation(iOperationservice.chercherOPparID(2)).build()
                    );
            icRservice.updateCR(1,CRDTO.builder().
                    dateFin(new Date()).
                    enumstatutCR(EnumstatutCR.TERMINESUCCES).
                    resume("testresume1").
                    equipementImp("testequipement1").
                    operation(iOperationservice.chercherOPparID(2)).build());
            icRservice.deleteCR(1);
             System.out.println(icRservice.findCRbyIDOP(2));
              System.out.println(icRservice.findCRbydemandeur("testdemandeur2"));
              System.out.println(icRservice.findCRbycategorieop("testcategorie"));
              System.out.println(icRservice.getCR());
            */



        };
    }
}
