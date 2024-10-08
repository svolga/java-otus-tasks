package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map;

    public CustomerService() {
        map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return getCopy(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getCopy(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> getCopy(Map.Entry<Customer, String> entry) {
        return entry == null ? null : Map.entry(clone(entry.getKey()), entry.getValue());
    }

    private Customer clone(Customer customer) {
        return new Customer(customer.getId(), customer.getName(), customer.getScores());
    }

}