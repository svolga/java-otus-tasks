package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> treeMap;

    public CustomerService() {
        treeMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return getCopy(treeMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getCopy(
                treeMap.entrySet().stream()
                        .filter(e -> e.getKey().getScores() > customer.getScores())
                        .findFirst()
                        .orElse(null)
        );
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }

    private Map.Entry<Customer, String> getCopy(Map.Entry<Customer, String> entry) {
        return entry == null ? null : Map.entry(clone(entry.getKey()), entry.getValue());
    }

    private Customer clone(Customer customer) {
        return new Customer(customer.getId(), customer.getName(), customer.getScores());
    }

}