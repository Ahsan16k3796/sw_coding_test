package com.smallworld;

import com.smallworld.data.Loader;
import com.smallworld.data.Transaction;
import com.smallworld.data.TransactionAmountComparator;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TransactionDataFetcher {
    /**
     * Loading data from loader
     */
    private List<Transaction> transactionList = Loader.loadTransactions();
    private List<Transaction> dstinctTransactions = Loader.getDistinctTransactionsList();

    public TransactionDataFetcher() {
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        try {
            return dstinctTransactions
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        try{
            return dstinctTransactions
                    .stream()
                    .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        try{

            OptionalDouble max = dstinctTransactions
                    .stream()
                    .mapToDouble(Transaction::getAmount)
                    .max();

            return max.isPresent() ? max.getAsDouble() : 0;

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        try {
            Set<String> sender = transactionList
                    .stream()
                    .map(Transaction::getSenderFullName)
                    .collect(Collectors.toSet());
            Set<String> beneficiary = transactionList
                    .stream()
                    .map(Transaction::getBeneficiaryFullName)
                    .collect(Collectors.toSet());

            sender.addAll(beneficiary);

            return sender.size();

        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        try {
            for(Transaction t: transactionList){
                if((t.getSenderFullName().equals(clientFullName) || t.getBeneficiaryFullName().equals(clientFullName) && !t.isIssueSolved())){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        try {
            return transactionList
                    .stream()
                    .collect(Collectors.toMap(Transaction::getBeneficiaryFullName,transaction -> transaction,(t,t2)->t));
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        try {
         return transactionList
                 .stream()
                 .filter(transaction -> !transaction.isIssueSolved())
                 .mapToInt(Transaction::getIssueId)
                 .boxed()
                 .collect(Collectors.toSet());
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        try {
            Set<String> set =transactionList
                    .stream()
                    .filter(Transaction::isIssueSolved)
                    .collect(Collectors.toMap(Transaction::getIssueMessage,transaction -> transaction,(t1,t2)->t1))
                    .keySet();
            set.remove(null);
         return set
                 .stream().toList();
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        try {
            return dstinctTransactions
                    .stream()
                    .sorted(new TransactionAmountComparator())
                    .limit(3)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        try {

            Optional<Map<String, Double>> listOfTotalAmountGroupedByFullname = Optional.of(dstinctTransactions
                    .stream().collect(Collectors.groupingBy(Transaction::getSenderFullName,
                            Collectors.summingDouble(Transaction::getAmount))));


            Optional<Map.Entry<String,Double>>  optional = listOfTotalAmountGroupedByFullname.get()
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue());

            return Optional.of(optional.get().getKey());
        }catch (Exception e){
            throw new UnsupportedOperationException();
        }
    }

}
