package id.co.indivara.jdt12.library.services;

import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.model.BookRequest;

import java.util.List;

public interface BookService {
    // POST
    Book saveBook(BookRequest book);
    //GET
    Book getBookById (Integer bookId);
    List<Book> getAllBooks();
    //DELETE
    String deleteBook(Integer bookId);
}