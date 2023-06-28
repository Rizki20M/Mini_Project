package id.co.indivara.jdt12.library.repositories;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByBook(Book book);
}
