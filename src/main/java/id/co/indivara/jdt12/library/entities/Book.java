package id.co.indivara.jdt12.library.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name="books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Integer bookId;

    @Column(name="book_isbn")
    private String bookIsbn;

    @Column(name="book_title")
    private String bookTitle;

    @Column(name="book_author")
    private String bookAuthor;

    @Column(name="book_publisher")
    private String bookPublisher;

    @Column(name="book_pages")
    private Integer bookPages;

    @Column(name="book_totals")
    private Integer bookTotals;

}
