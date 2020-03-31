package customer;

import java.util.HashMap;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;
    private static final String REGEX_NUMBER = "(\\+)[7][0-9]{10}";
    private static final String REGEX_EMAIL = "[_A-Za-z0-9-\\.]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})";

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
        throws IllegalArgumentException, PhoneNumberException, EmailException {
        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalArgumentException("Wrong format. Correct format: \n" +
                "add Василий Петров vasily.petrov@gmail.com +79215637722");
        }
        String name = components[0] + " " + components[1];
        if (components[3].matches(REGEX_NUMBER)) {
            if (components[2].matches(REGEX_EMAIL)) {
                storage.put(name, new Customer(name, components[3], components[2]));
            } else {
                throw new EmailException("Please check email input");
            }
        } else {
            throw new PhoneNumberException("Please check phone number entry");
        }
    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }
}