package id.co.indivara.jdt12.library.controller;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.handling.Message;
import id.co.indivara.jdt12.library.model.BookRequest;
import id.co.indivara.jdt12.library.model.ReaderRequest;
import id.co.indivara.jdt12.library.services.BookService;
import id.co.indivara.jdt12.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderService readerService;
    @PostMapping("/book")
    public Book saveBook(@RequestBody BookRequest bookRequest){
        return bookService.saveBook(bookRequest);
    }
    @DeleteMapping("/book/{id}")
    public Message deleteBook(@PathVariable ("id")Integer bookId){
        return bookService.deleteBook(bookId);
    }
    @PutMapping("/updateBook/{id}")
    public Book updateBook(@RequestBody BookRequest bookRequest, @PathVariable("id") Integer bookId){
        return bookService.updateBook(bookRequest, bookId);
    }
    @PostMapping("/reader")
    public Reader saveReader(@RequestBody ReaderRequest readerRequest){
        return readerService.saveReader(readerRequest);
    }
    @DeleteMapping("/reader/{id}")
    public Message deleteReader(@PathVariable ("id")Integer readerId)   {
        return readerService.deleteReader(readerId);
    }

    @PutMapping("/updateReader/{id}")
    public Reader updateReader(@RequestBody ReaderRequest readerRequest, @PathVariable("id") Integer readerId){
        return readerService.updateReader(readerRequest,readerId);
    }
}
