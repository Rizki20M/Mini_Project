package id.co.indivara.jdt12.library.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@Table(name="transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer borrowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(nullable = false)
    private LocalDate borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(nullable = false)
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate returnDate;

    private Boolean penalty;
}
