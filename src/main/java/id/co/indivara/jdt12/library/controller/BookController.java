package id.co.indivara.jdt12.library.controller;

import id.co.indivara.jdt12.library.entities.Book;
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
    public Book getBookById(@PathVariable Integer bookId){
        return bookService.getBookById(bookId);
    }
    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Integer bookId){
       return bookService.deleteBook(bookId);
    }
}
