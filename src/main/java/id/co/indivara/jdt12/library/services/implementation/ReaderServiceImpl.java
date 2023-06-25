package id.co.indivara.jdt12.library.services.implementation;
import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.model.ReaderRequest;
import id.co.indivara.jdt12.library.repositories.ReaderRepository;
import id.co.indivara.jdt12.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Override
    public Reader saveReader(ReaderRequest readerRequest) {
        Reader reader = Reader.builder()
                .readerName(readerRequest.getReaderName())
                .readerAge(readerRequest.getReaderAge())
                .readerAddress(readerRequest.getReaderAddress())
                .readerGender(readerRequest.getReaderGender())
                .readerPhone(readerRequest.getReaderPhone())
                .build();
        readerRepository.save(reader);
        return reader;
    }
    @Override
    public Reader getReaderById(Integer readerId) {
        return readerRepository.findById(readerId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reader Yang Anda Cari Tidak Ditemukan"));
    }
    @Override
    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    @Override
    public String deleteReader(Integer readerId) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reader Yang Anda Cari Tidak Ditemukan"));

        readerRepository.delete(reader);
        return "Reader Berhasil Dihapus";
    }
    public String updateReader(ReaderRequest readerRequest, Integer readerId){
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reader Yang Anda Cari Tidak Ditemukan"));
        reader.setReaderName(readerRequest.getReaderName());
        reader.setReaderAge(readerRequest.getReaderAge());
        reader.setReaderAddress(readerRequest.getReaderAddress());
        reader.setReaderGender(readerRequest.getReaderGender());
        reader.setReaderPhone(readerRequest.getReaderPhone());
        readerRepository.save(reader);

        return "Data Berhasil Di Update";
    }

}
