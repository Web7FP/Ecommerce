package com.springboot.ecommerce.model.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;


    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }


    @Override
    public void setSuccessTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.SUCCESS);
        this.saveTransaction(transaction);
    }

    @Override
    public void setCancelledTransaction(Transaction transaction) {
        transaction.setStatus(TransactionStatus.CANCELLED);
        this.saveTransaction(transaction);
    }


}

