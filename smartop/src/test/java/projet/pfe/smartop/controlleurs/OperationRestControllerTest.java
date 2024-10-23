package projet.pfe.smartop.controlleurs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import projet.pfe.smartop.dtos.CRDTO;
import projet.pfe.smartop.enums.EnumstatutCR;
import projet.pfe.smartop.services.ICRservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.BDDMockito.given;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OperationRestControllerTest {
    private MockMvc mockMvc;
    private ICRservice icrService;

    @BeforeEach
    public void setUp() {
        List<CRDTO> crdtoList = new ArrayList<>();
        CRDTO crdto1 = new CRDTO(3,new Date(), EnumstatutCR.TERMINEECHEC, "Résumé 1", "Equipement 1", null,"OP1");
        CRDTO crdto2 = new CRDTO(4,new Date(), EnumstatutCR.TERMINESUCCES, "Résumé 2", "Equipement 2", null,"OP2");
        crdtoList.add(crdto1);
        crdtoList.add(crdto2);

        given(icrService.getCR()).willReturn(crdtoList);
    }
    @Test
    public void testGetListCR() throws Exception {
        mockMvc.perform(get("/listCR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].dateFin", is("2024-07-10")))
                .andExpect((ResultMatcher) jsonPath("$[0].enumstatutCR", is("TERMINEECHEC")))
                .andExpect((ResultMatcher) jsonPath("$[0].resume", is("Résumé 1")))
                .andExpect((ResultMatcher) jsonPath("$[0].equipementImp", is("Equipement 1")))
                .andExpect((ResultMatcher) jsonPath("$[0].codeOP", is("OP1")))
                .andExpect((ResultMatcher) jsonPath("$[0].id", is(3)))
                .andExpect((ResultMatcher) jsonPath("$[1].dateFin", is("2024-07-11")))
                .andExpect((ResultMatcher) jsonPath("$[1].enumstatutCR", is("TERMINESUCCES")))
                .andExpect((ResultMatcher) jsonPath("$[1].resume", is("Résumé 2")))
                .andExpect((ResultMatcher) jsonPath("$[1].equipementImp", is("Equipement 2")))
                .andExpect((ResultMatcher) jsonPath("$[1].codeOP", is("OP2")))
                .andExpect((ResultMatcher) jsonPath("$[1].id", is(4)));
    }



}