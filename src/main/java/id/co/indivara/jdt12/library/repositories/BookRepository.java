package id.co.indivara.jdt12.library.repositories;
import id.co.indivara.jdt12.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
