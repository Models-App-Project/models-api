/*package com.modelsapp.models_api.ModelTests;

import com.modelsapp.models_api.controller.ModelController;
import com.modelsapp.models_api.entity.Model;
import com.modelsapp.models_api.service.ModelService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ModelController.class)
public class ModelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelService modelService;

    private Model model;

    @BeforeEach
    void setUp() {
        // Inicializando um exemplo de modelo
        model = new Model();
        model.setId(UUID.randomUUID());
        model.setName("Alice");
        model.setAge(25);
        model.setHeight(1.75);
        model.setEyesColor("Blue");
        model.setWeight(60.5);
        model.setHairColor("Blonde");
        model.setWaistline(65.0);
        model.setDescription("Professional model");
        model.setHip(90.0);
        model.setBust(85.0);
    }

    // Teste para o endpoint POST
    @Test
    public void testAddModel() throws Exception {
        // Mockando o comportamento do serviço
        given(modelService.saveModel(any(Model.class))).willReturn(model);

        // Enviando requisição POST
        mockMvc.perform(post("/models/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Alice\",\n" +
                        "    \"age\": 25,\n" +
                        "    \"height\": 1.75,\n" +
                        "    \"eyesColor\": \"Blue\",\n" +
                        "    \"weight\": 60.5,\n" +
                        "    \"hairColor\": \"Blonde\",\n" +
                        "    \"waistline\": 65.0,\n" +
                        "    \"description\": \"Professional model\",\n" +
                        "    \"hip\": 90.0,\n" +
                        "    \"bust\": 85.0\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.age").value(25))
                .andDo(print());
    }

    @Test
    public void testAddModelNegativeAge() throws Exception {
        // Mockando o comportamento do serviço
        given(modelService.saveModel(any(Model.class))).willReturn(model);

        // Enviando requisição POST
        mockMvc.perform(post("/models/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"Alice\",\n" +
                                "    \"age\": -24,\n" +
                                "    \"height\": 1.75,\n" +
                                "    \"eyesColor\": \"Blue\",\n" +
                                "    \"weight\": 60.5,\n" +
                                "    \"hairColor\": \"Blonde\",\n" +
                                "    \"waistline\": 65.0,\n" +
                                "    \"description\": \"Professional model\",\n" +
                                "    \"hip\": 90.0,\n" +
                                "    \"bust\": 85.0\n" +
                                "}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.age").value(25))
                .andDo(print());
    }

    // Teste para o endpoint GET (por ID)
    @Test
    public void testGetModelById() throws Exception {
        // Mockando o retorno do serviço
        given(modelService.findModelById(any(UUID.class))).willReturn(Optional.of(model));

        // Enviando requisição GET com um ID válido
        mockMvc.perform(get("/models/{id}", model.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.height").value(1.75))
                .andDo(print());
    }

    // Teste para um ID não encontrado
    @Test
    public void testGetModelByIdNotFound() throws Exception {
        // Mockando o retorno do serviço como não encontrado
        given(modelService.findModelById(any(UUID.class))).willReturn(Optional.empty());

        // Enviando requisição GET com um ID inválido
        mockMvc.perform(get("/models/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
*/