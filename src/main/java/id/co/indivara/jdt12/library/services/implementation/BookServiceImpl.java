package id.co.indivara.jdt12.library.services.implementation;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.handling.Message;
import id.co.indivara.jdt12.library.model.BookRequest;
import id.co.indivara.jdt12.library.model.DisplayBookResponse;
import id.co.indivara.jdt12.library.repositories.BookRepository;
import id.co.indivara.jdt12.library.repositories.TransactionRepository;
import id.co.indivara.jdt12.library.repositories.WishlistRepository;
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
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

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
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku Yang Anda Cari Tidak Ditemukan"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Message deleteBook(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku Yang Anda Cari Tidak Ditemukan"));
        bookRepository.deleteById(bookId);
        return new Message(200,"Buku Berhasil Dihapus");
    }


    @Override
    public DisplayBookResponse displayBook(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku Yang Anda Cari Tidak Ditemukan"));

        return DisplayBookResponse.builder()
                .book(book)
                .readingTimes(transactionRepository.findAllByBook(book).size())
                .wishlist(wishlistRepository.findAllByBook(book).size())
                .build();

    }
    @Override
    public Book updateBook(BookRequest bookRequest, Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku Yang Anda Cari Tidak Ditemukan"));

        book.setBookIsbn(bookRequest.getBookIsbn());
        book.setBookTitle(bookRequest.getBookTitle());
        book.setBookAuthor(bookRequest.getBookAuthor());
        book.setBookPublisher(bookRequest.getBookPublisher());
        book.setBookPages(bookRequest.getBookPages());
        book.setBookTotals(bookRequest.getBookTotals());
        bookRepository.save(book);

        return book;
    }
}

