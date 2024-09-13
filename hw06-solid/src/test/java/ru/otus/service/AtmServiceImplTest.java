package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import ru.otus.enums.Banknote;
import ru.otus.exceptions.BalanceException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Банкомат должен выполнять ")
class AtmServiceImplTest {

    private AtmService atmService;

    @BeforeEach
    void setUp() {
        WalletService wallet = new WalletServiceImpl();
        CalculatorService calculator = new CalculatorServiceImpl(wallet);
        atmService = new AtmServiceImpl(wallet, calculator);
    }

    @Test
    @DisplayName("положить деньги")
    void put() {
        initAtm();
        assertEquals(4300, atmService.getBalance());
    }

    @Test
    @DisplayName("проверить баланс")
    void getBalance() {
        int currentBalance = atmService.getBalance();
        atmService.put(Banknote.BANKNOTE_1000, 3);
        int newBalance = atmService.getBalance();
        assertEquals(0, currentBalance);
        assertEquals(3000, newBalance);
    }

    @Test
    @DisplayName("выдать деньги")
    void get() {
        initAtm();
        int initBalance = atmService.getBalance();
        int requestedSum = 1700;
        atmService.get(requestedSum);
        int newBalance = atmService.getBalance();
        assertEquals(newBalance, initBalance - requestedSum);
    }

    @Test
    @DisplayName("показать содержимое банкомата")
    void getContent() {
        Map<Banknote, Integer> content = Map.of(Banknote.BANKNOTE_1000, 3);
        atmService.put(content);
        assertEquals(content, atmService.getContent());
    }

    @Test
    @DisplayName("выбросить ошибку баланса")
    void shouldThrowBalanceException() {
        initAtm();
        int initBalance = atmService.getBalance();
        int requestedSum = initBalance + 1;
        assertThatExceptionOfType(BalanceException.class).isThrownBy(() -> atmService.get(requestedSum));
    }

    private void initAtm() {
        atmService.put(Banknote.BANKNOTE_1000, 3);
        atmService.put(Banknote.BANKNOTE_500, 1);
        atmService.put(Banknote.BANKNOTE_500, 1);
        atmService.put(Banknote.BANKNOTE_100, 3);
    }
}