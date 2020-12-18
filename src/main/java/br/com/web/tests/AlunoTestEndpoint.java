package br.com.web.tests;

import br.com.web.endpoint.AlunoEndpoint;
import br.com.web.model.Aluno;
import br.com.web.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
public class AlunoTestEndpoint {

    private static final String ALUNO_API_URL_PATH = "/alunos";
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mvc;
    
    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoEndpoint alunoEndpoint;

    @Before
    public void setUp() throws Exception{
        mvc = MockMvcBuilders.standaloneSetup(alunoEndpoint).build();
    }

    @Test
    public void getAllAlunosWithElementsTest() throws Exception {
        List<Aluno>  list = new ArrayList<Aluno>();
        list.add( new Aluno("Joao teste", "matematica", "joao", "123"));
        list.add( new Aluno("Maria teste", "portugues", "maria", "123"));
        when(alunoRepository.findAll()).thenReturn( list);

        mvc.perform(
                MockMvcRequestBuilders.get(ALUNO_API_URL_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":null,\"nome\":\"Joao teste\",\"curso\":\"matematica\",\"usuario\":\"joao\",\"senha\":\"123\"},{\"id\":null,\"nome\":\"Maria teste\",\"curso\":\"portugues\",\"usuario\":\"maria\",\"senha\":\"123\"}]"));

    }

    @Test
    public void getAllAlunosWithoutElementsTest() throws Exception {
        List<Aluno>  list = new ArrayList<Aluno>();
        when(alunoRepository.findAll()).thenReturn( list);

        mvc.perform(
                MockMvcRequestBuilders.get(ALUNO_API_URL_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    public void getAlunoBydIdTest() throws Exception {

        when(alunoRepository.findOne(1L)).thenReturn( new Aluno("Joao teste", "matematica", "joao", "123"));

        mvc.perform(
                MockMvcRequestBuilders.get(ALUNO_API_URL_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":null,\"nome\":\"Joao teste\",\"curso\":\"matematica\",\"usuario\":\"joao\",\"senha\":\"123\"}"));

    }

    @Test
    public void getAlunoNotFoundTest() throws Exception {

        when(alunoRepository.findOne(1L)).thenReturn( new Aluno("Joao teste", "matematica", "joao", "123"));

        mvc.perform(
                MockMvcRequestBuilders.get(ALUNO_API_URL_PATH + "/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createAlunoTest() throws Exception {

        Aluno aluno = new Aluno("Joao teste", "matematica", "joao", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= ow.writeValueAsString( aluno);

        mvc.perform(
                MockMvcRequestBuilders.post(ALUNO_API_URL_PATH)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void createAlunoHasFailTest() throws Exception {

        Aluno aluno = new Aluno("Joao teste", "matematica", "joao", "123");


        mvc.perform(
                MockMvcRequestBuilders.post(ALUNO_API_URL_PATH))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void alunoIsDeletedTest() throws Exception {

        when(alunoRepository.findOne(1L)).thenReturn( new Aluno("Joao teste", "matematica", "joao", "123"));

        mvc.perform(
                MockMvcRequestBuilders.delete(ALUNO_API_URL_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
