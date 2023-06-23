package id.co.indivara.jdt12.library.services;

import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.model.ReaderRequest;

public interface ReaderService {
    Reader saveReader(ReaderRequest readerRequest);
}
