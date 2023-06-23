package id.co.indivara.jdt12.library.repositories;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
//    Optional<Transaction> findByIdAndReader(Integer transactionId, Reader reader);
}
