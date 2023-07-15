package com.smallworld.data;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Loader {
    static public List<Transaction> loadTransactions(){
        List<Transaction> transactions =null;
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("transactions.json");

            Transaction[] transactionsArray = gson.fromJson(reader, Transaction[].class);
            transactions = Arrays.asList(transactionsArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
    static public List<Transaction> getDistinctTransactionsList(){
        List<Transaction> transactionList = loadTransactions();

        List<Transaction> distinctTransactions = new ArrayList<>(
                transactionList.stream()
                .collect(Collectors.toMap(Transaction::getMtn, transaction -> transaction, (t1, t2) -> t1))
                .values());

        return distinctTransactions;
    }
}
