package id.co.indivara.jdt12.library.services;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.handling.Message;
import id.co.indivara.jdt12.library.model.ReaderRequest;

import java.util.List;

public interface ReaderService {
    Reader saveReader(ReaderRequest readerRequest);
    Reader getReaderById (Integer readerId);
    List<Reader> getAllReaders();
    Message deleteReader(Integer readerId);
    Reader updateReader(ReaderRequest readerRequest, Integer readerId);
}
