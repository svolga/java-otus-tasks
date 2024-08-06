package ru.otus.test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.AssertionsForClassTypes;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.model.Customer;

public class CustomerTest {

    List<Customer> list = new ArrayList<>();

    @Before
    public void beforeTest1() {
        list.add(new Customer(1, "BeforeTestUser11", 150));
        list.add(new Customer(2, "BeforeTestUser12", 100));
        list.add(new Customer(3, "BeforeTestUser13", 170));
    }

    @Before
    public void beforeTest2() {
        list.add(new Customer(4, "BeforeTestUser21", 250));
        list.add(new Customer(5, "BeforeTestUser22", 230));
    }

    @Test
    public void addCustomerTest() {
        int size = list.size();
        list.add(new Customer(10, "New User", 1000));
        assertThat(list.size()).isEqualTo(size + 1);
    }

    @Test
    public void removeCustomerTest() {
        int size = list.size();
        list.remove(1);
        assertThat(list.size()).isEqualTo(size - 1);
    }

    @Test
    public void setterCustomerTestWithException() {
        String expectedName = "UpdatedName";
        Customer customer = list.get(1);
        customer.setName(expectedName);
        AssertionsForClassTypes.assertThat(customer.getName()).isEqualTo("NotValidUpdatedNames");
    }

    @After
    public void afterTest() {
        list.clear();
        assertThat(list.size()).isEqualTo(0);
    }
}
