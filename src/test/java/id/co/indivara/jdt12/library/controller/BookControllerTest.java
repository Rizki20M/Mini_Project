package id.co.indivara.jdt12.library.controller;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.handling.Message;
import id.co.indivara.jdt12.library.mapper.Convert;
import id.co.indivara.jdt12.library.services.BookService;
import id.co.indivara.jdt12.library.services.implementation.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllBooksTest() throws Exception {
        List<Book> bookCheck = bookServiceImpl.getAllBooks();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/all")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    List<Book> book = Convert.getAllData(result.getResponse().getContentAsString(), Book.class);
                    Assertions.assertNotNull(book);
                    Assertions.assertEquals(bookCheck.get(0).getBookTitle(), book.get(0).getBookTitle());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookId").isNotEmpty());

    }
    @Test
    public void getBookById() throws Exception {
        Book book  = bookServiceImpl.getBookById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    Book book1 = Convert.getData(result.getResponse().getContentAsString(), Book.class);
                    Assertions.assertNotNull(book1);
                    Assertions.assertEquals(book.getBookId(),book1.getBookId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").isNotEmpty());
    }
    @Test
    public void updateBook() throws Exception {
       Book book = bookServiceImpl.getBookById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/updateBook/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Convert.toJson(book))
                )
                .andDo (result -> {
                    Book book1 = Convert.getData(result.getResponse().getContentAsString(), Book.class);
                    Assertions.assertNotNull(book);
                    Assertions.assertEquals(book.getBookId(),book1.getBookId());

                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").isNotEmpty());
    }
    @Test
    public void addAndDeleteBook() throws Exception {
        Book book  = Book.builder()
                .bookIsbn("ISBN 333-463-3425")
                .bookTitle("Malin Kandang")
                .bookAuthor("Budi")
                .bookPublisher("Erlangga")
                .bookPages(122)
                .bookTotals(2)
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/book/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Convert.toJson(book))
                )
                .andDo(result -> {
                    Book book1 = Convert.getData(result.getResponse().getContentAsString(), Book.class);
                    Assertions.assertNotNull(book1);
                    Assertions.assertEquals(book1.getBookTitle(),book.getBookTitle());
                    deleteBook(book1.getBookId());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookId").isNotEmpty());
    }
    public void deleteBook(Integer bookId) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/book/"+bookId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(result -> {
                    Message message = Convert.getData(result.getResponse().getContentAsString(), Message.class);
                    Assertions.assertNotNull(message);
                    Assertions.assertEquals("Buku Berhasil Dihapus",message.getMessage());
                })
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty());
    }
}
