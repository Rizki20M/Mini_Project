package id.co.indivara.jdt12.library.services;

import id.co.indivara.jdt12.library.entities.Transaction;
import id.co.indivara.jdt12.library.model.BorrowRequest;

public interface TransactionService {
    Object borrowBook(BorrowRequest req);
    String returnBook(Integer transactionId, Integer readerId);
}
