package id.co.indivara.jdt12.library.controller;

import id.co.indivara.jdt12.library.entities.Book;
import id.co.indivara.jdt12.library.entities.Reader;
import id.co.indivara.jdt12.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderService readerService;
    @GetMapping("/{id}")
    public Reader getReaderById(@PathVariable("id") Integer readerId){
        return readerService.getReaderById(readerId);
    }

    @GetMapping("/all")
    public List<Reader> getAllReaders(){
        return readerService.getAllReaders();
    }
}
