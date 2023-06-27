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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Objects;


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
        Book book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Buku Yang Ingin Dipinjam Tidak Ada"));
        Reader reader = readerRepository.findById(req.getReaderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reader Tidak Ditemykan, Anda Harus Registrasi Terlebih Dahulu"));

        if(book.getBookTotals() == 0){
            Wishlist wishlist = wishlistRepository.findByReaderAndBook(reader, book).orElse(null);
            if(wishlist == null){
                Wishlist wishlist1 = Wishlist.builder()
                        .book(book)
                        .reader(reader)
                        .build();

                wishlistRepository.save(wishlist1);
                return "Buku Sedang Kosong, Anda Akan Ditambahkan Ke Daftar Wishlist";

            }
            return "Buku Masih Kosong, Anda Sudah Ada Dalam Daftar Wishlist";
        }

        book.setBookTotals(book.getBookTotals() - 1);
        Transaction trx = Transaction.builder()
                .book(book)
                .borrowDate(LocalDate.now())
                .penalty(false)
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaksi Tidak Ditemukan"));
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Tidak Ditemukan"));
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

        return "Buku Berhasil Dikembalikan," + (transaction.getPenalty() ? " Tetapi Anda Terkena Penalty Karena Melewati Batas Waktu Pengembalian" : "");
    }

}
