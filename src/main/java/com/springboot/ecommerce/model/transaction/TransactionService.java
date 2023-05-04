package com.springboot.ecommerce.model.transaction;

public interface TransactionService {

    void saveTransaction(Transaction transaction);

    void setSuccessTransaction(Transaction transaction);

    void setCancelledTransaction(Transaction transaction);

}
