package id.co.indivara.jdt12.library.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="readers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reader_id")
    private Integer readerId;

    @Column(name="reader_name")
    private String readerName;

    @Column(name="reader_address")
    private String readerAddress;

    @Column(name="reader_phone")
    private String readerPhone;
}
