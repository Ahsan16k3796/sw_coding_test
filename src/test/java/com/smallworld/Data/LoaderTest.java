package com.smallworld.Data;

import com.smallworld.data.Loader;
import com.smallworld.data.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LoaderTest {

    @Test
    void loadTransactionsTest(){
        int noOfTransactionsExpected = 13;
        List<Transaction> transactionList = Loader.loadTransactions();
        Assertions.assertEquals(noOfTransactionsExpected,transactionList.size());
    }

    @Test
    void getDistinctTransactionsListTest(){
        int noOfTransactionsExpected = 10;
        List<Transaction> transactionList = Loader.getDistinctTransactionsList();
        Assertions.assertEquals(noOfTransactionsExpected,transactionList.size());
    }

}
