package id.co.indivara.jdt12.library.services.implementation;

import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.model.ReaderRequest;
import id.co.indivara.jdt12.library.repositories.ReaderRepository;
import id.co.indivara.jdt12.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderRepository readerRepository;
    @Override
    public Reader saveReader(ReaderRequest readerRequest) {
        Reader reader = Reader.builder()
                .readerName(readerRequest.getReaderName())
                .readerAddress(readerRequest.getReaderAddress())
                .readerPhone(readerRequest.getReaderPhone())
                .build();
        readerRepository.save(reader);
        return reader;
    }
}
