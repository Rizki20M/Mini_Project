package id.co.indivara.jdt12.library.controller;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.handling.Message;
import id.co.indivara.jdt12.library.mapper.Convert;
import id.co.indivara.jdt12.library.services.implementation.ReaderServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReaderControllerTest {
    @Autowired
    private ReaderServiceImpl readerServiceImpl;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllReadersTest() throws Exception {
        List<Reader> readerCheck = readerServiceImpl.getAllReaders();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reader/all")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    List<Reader> reader = Convert.getAllData(result.getResponse().getContentAsString(), Reader.class);
                    Assertions.assertNotNull(reader);
                    Assertions.assertEquals(readerCheck.get(0).getReaderName(), reader.get(0).getReaderName());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].readerId").isNotEmpty());

    }
    @Test
    public void getReaderById() throws Exception {
        Reader reader  = readerServiceImpl.getReaderById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reader/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    Reader reader1 = Convert.getData(result.getResponse().getContentAsString(), Reader.class);
                    Assertions.assertNotNull(reader1);
                    Assertions.assertEquals(reader.getReaderId(),reader1.getReaderId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.readerId").isNotEmpty());
    }

    @Test
    public void updateReader() throws Exception {
        Reader reader = readerServiceImpl.getReaderById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/updateReader/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Convert.toJson(reader))
                )
                .andDo (result -> {
                    Reader reader1 = Convert.getData(result.getResponse().getContentAsString(), Reader.class);
                    Assertions.assertNotNull(reader);
                    Assertions.assertEquals(reader.getReaderId(),reader1.getReaderId());

                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.readerId").isNotEmpty());
    }
    @Test
    public void addAndDeleteReader() throws Exception {
        Reader reader  = Reader.builder()
                .readerName("Dheo")
                .readerAge(45)
                .readerAddress("Jl. Cilandak")
                .readerGender("Male")
                .readerPhone("084562745")
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/reader/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Convert.toJson(reader))
                )
                .andDo(result -> {
                    Reader reader1 = Convert.getData(result.getResponse().getContentAsString(), Reader.class);
                    Assertions.assertNotNull(reader1);
                    Assertions.assertEquals(reader1.getReaderName(),reader.getReaderName());
                    deleteReader(reader1.getReaderId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.readerId").isNotEmpty());
    }
    public void deleteReader(Integer readerId) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/reader/"+readerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    Message message = Convert.getData(result.getResponse().getContentAsString(), Message.class);
                    Assertions.assertNotNull(message);
                    Assertions.assertEquals("Reader Berhasil Dihapus",message.getMessage());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty());
    }
}
