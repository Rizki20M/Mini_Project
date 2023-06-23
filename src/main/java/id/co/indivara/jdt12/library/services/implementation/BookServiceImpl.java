package id.co.indivara.jdt12.library.services.implementation;

import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.model.BookRequest;
import id.co.indivara.jdt12.library.repositories.BookRepository;
import id.co.indivara.jdt12.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book saveBook(BookRequest bookRequest) {
        Book buku = Book.builder()
                .bookAuthor(bookRequest.getBookAuthor())
                .bookTotals(bookRequest.getBookTotals())
                .bookTitle(bookRequest.getBookTitle())
                .bookPublisher(bookRequest.getBookPublisher())
                .bookIsbn(bookRequest.getBookIsbn())
                .bookPages(bookRequest.getBookPages())
                .build();

        bookRepository.save(buku);
        return buku;
    }

    @Override
    public Book getBookById(Integer bookId) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public String deleteBook(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Buku Yang Anda Cari Tidak Ditemukan"));
        
        bookRepository.delete(book);
    return "Buku Berhasil Dihapus";
    }
}
