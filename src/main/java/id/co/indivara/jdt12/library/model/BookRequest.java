package id.co.indivara.jdt12.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotNull
    private Integer bookId;
    @NotNull
    private String bookIsbn;
    @NotNull
    private String bookTitle;
    @NotNull
    private String bookAuthor;
    @NotNull
    private String bookPublisher;
    @NotNull
    private Integer bookPages;
    @NotNull
    private Integer bookTotals;
}
