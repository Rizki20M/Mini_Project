package id.co.indivara.jdt12.library.model;

import id.co.indivara.jdt12.library.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayBookResponse {
    private Book book;
    private Integer wishlist;
    private Integer readingTimes;
}
