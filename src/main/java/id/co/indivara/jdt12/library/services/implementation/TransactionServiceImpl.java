package id.co.indivara.jdt12.library.services.implementation;

import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.entities.Transaction;
import id.co.indivara.jdt12.library.entities.Wishlist;
import id.co.indivara.jdt12.library.model.BookRequest;
import id.co.indivara.jdt12.library.model.BorrowRequest;
import id.co.indivara.jdt12.library.repositories.BookRepository;
import id.co.indivara.jdt12.library.repositories.ReaderRepository;
import id.co.indivara.jdt12.library.repositories.TransactionRepository;
import id.co.indivara.jdt12.library.repositories.WishlistRepository;
import id.co.indivara.jdt12.library.services.TransactionService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    @Transactional
    public Object borrowBook(BorrowRequest req) {
        System.out.println(1);
        Book book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku yang mau dipinjem gak ada"));
        Reader reader = readerRepository.findById(req.getReaderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reader Tidak Ketemu, Anda Harus Registrasi Lebih Dahulu"));

        System.out.println("total dari buku sebelum di pinjam " + book.getBookTotals());
        System.out.println("id dari buku yang dipinjam " + book.getBookId());
        System.out.println("request id reader yang minjam " + req.getReaderId());
        System.out.println("request id buku yang dipinjam " + req.getBookId());
        if(book.getBookTotals() == 0){
            Wishlist wishlist = wishlistRepository.findByReaderAndBook(reader, book).orElse(null);
            if(wishlist == null){
                Wishlist wishlist1 = Wishlist.builder()
                        .book(book)
                        .reader(reader)
                        .build();

                wishlistRepository.save(wishlist1);
                return "Bukunya lagi kosong, udah ditambahin di wishlist";

            }
            return "Bukunya Masih kosong, wishlist kamu juga ada";
        }

        book.setBookTotals(book.getBookTotals() - 1);
        long dueData = Math.round((double) (book.getBookPages() / 100));

        Transaction trx = Transaction.builder()
                .book(book)
                .borrowDate(LocalDate.now())
                .penalty(false)
//                .dueDate(LocalDate.now().minusDays(2))
                .dueDate(LocalDate.now().plusDays((long)Math.ceil(book.getBookPages()/100)))
                .reader(reader)
                .build();

        Wishlist wishlist = wishlistRepository.findByReaderAndBook(reader, book).orElse(null);
        if(wishlist != null){
            wishlistRepository.delete(wishlist);
        }
        bookRepository.save(book);
        transactionRepository.save(trx);

        return trx;
    }

    @Override
    public String returnBook(Integer transactionId, Integer readerId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaksi Buku gak ketemu"));
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Gak Ketemu"));
        if(transaction.getReturnDate() != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku ini sudah pernah dikembalikan");
        }

        if(!Objects.equals(reader.getReaderId(), transaction.getReader().getReaderId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anda Bukan Pemilik Dari Peminjaman Ini");
        }

        if(LocalDate.now().isAfter(transaction.getDueDate())){
            transaction.setPenalty(true);
        }

        transaction.setReturnDate(LocalDate.now());
        transaction.getBook().setBookTotals(transaction.getBook().getBookTotals() + 1);

        transactionRepository.save(transaction);
        bookRepository.save(transaction.getBook());
        System.out.println("here");

        return "Buku Berhasil dikembalikan" + (transaction.getPenalty() ? "tetapi ada penalty karena kamu telat mengembalikan" : "");
    }

}
