package id.co.indivara.jdt12.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReaderRequest {
    private String readerName;
    private Integer readerAge;
    private String readerAddress;
    private String readerGender;
    private String readerPhone;

}
