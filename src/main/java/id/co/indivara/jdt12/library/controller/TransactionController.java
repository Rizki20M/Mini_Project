package id.co.indivara.jdt12.library.controller;
import id.co.indivara.jdt12.library.entities.Transaction;
import id.co.indivara.jdt12.library.model.BorrowRequest;
import id.co.indivara.jdt12.library.model.ReturnRequest;
import id.co.indivara.jdt12.library.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/borrow")
    public Object borrowBook(@RequestBody BorrowRequest borrowRequest){
        return transactionService.borrowBook(borrowRequest);
    }

    @PostMapping("/return")
    public String  returnBook(@RequestBody ReturnRequest returnRequest){
        return transactionService.returnBook(returnRequest.getTransactionId(), returnRequest.getReaderId());
    }

}
