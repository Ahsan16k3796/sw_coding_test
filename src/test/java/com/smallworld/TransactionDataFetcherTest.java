package com.smallworld;

import com.smallworld.data.Loader;
import com.smallworld.data.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TransactionDataFetcherTest {
    private TransactionDataFetcher transactionDataFetcher;

    @BeforeEach
    public  void setup(){
        transactionDataFetcher = new TransactionDataFetcher();
    }

    @Test
    public void getTotalTransactionAmountTest(){
        Double totalAmount = transactionDataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(2889.17,totalAmount);
    }

    @Test
    public void getTotalTransactionAmountSentByTest(){
        Double amount = transactionDataFetcher.getTotalTransactionAmountSentBy("Billy Kimber");
        Assertions.assertEquals(459.09,amount);
    }

    @Test
    public void getMaxTransactionAmountTest(){
        Double amount = transactionDataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(985.0,amount);
    }

    @Test
    public void countUniqueClientsTest(){
        Long count = transactionDataFetcher.countUniqueClients();
        Assertions.assertEquals(14,count);
    }

    @Test void hasOpenComplianceIssuesTest(){
        Boolean isIssueSolved = transactionDataFetcher.hasOpenComplianceIssues("Michael Gray");
        Assertions.assertEquals(true,isIssueSolved);
    }

    @Test void getTransactionsByBeneficiaryNameTest(){
        Map<String, Transaction> transactions = transactionDataFetcher.getTransactionsByBeneficiaryName();
        Assertions.assertEquals(10,transactions.entrySet().size());
    }

    @Test void getUnsolvedIssueIdsTest(){
        Set<Integer> set = Set.of(1,3,99,54,15);
        Set<Integer> transactions = transactionDataFetcher.getUnsolvedIssueIds();
        Assertions.assertEquals(set,transactions);
    }

    @Test
    void getAllSolvedIssueMessagesTest(){
        List<String> actualListOfIssues = List.of("Never gonna give you up", "Never gonna let you down", "Never gonna run around and desert you");
        List<String> issueMessagesResult = transactionDataFetcher.getAllSolvedIssueMessages();
        Assertions.assertArrayEquals(actualListOfIssues.toArray(),issueMessagesResult.toArray());
    }

    @Test
    void getTop3TransactionsByAmountTest(){
        List<Double> actualListOfTop3Amount = List.of(985.0,666.0,430.2);
        List<Transaction> transactions = transactionDataFetcher.getTop3TransactionsByAmount();
        List<Double> top3AmountResult = transactions.stream().mapToDouble(Transaction::getAmount).boxed().toList();
        Assertions.assertEquals(actualListOfTop3Amount,top3AmountResult);
    }

    @Test
    void getTopSenderTest(){
        Optional actual = Optional.of("Arthur Shelby");
        Optional<String> topSender = transactionDataFetcher.getTopSender();
        Assertions.assertEquals(actual,topSender);
    }

}
