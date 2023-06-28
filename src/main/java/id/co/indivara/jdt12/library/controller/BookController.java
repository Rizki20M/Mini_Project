package id.co.indivara.jdt12.library.controller;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.model.DisplayBookResponse;
import id.co.indivara.jdt12.library.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable ("id") Integer id){
        return bookService.getBookById(id);
    }
    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/display/{id}")
    public DisplayBookResponse displayBookResponse(@PathVariable("id") Integer id ){
        return bookService.displayBook(id);
    }
}
