package id.co.indivara.jdt12.library.repositories;

import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByReaderAndBook(Reader reader, Book book);
    List<Wishlist> findAllByBook(Book book);
}
