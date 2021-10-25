package com.homework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static Sex stringToSex(String string) throws IllegalArgumentException {
        switch (string) {
            case "Male":
                return Sex.MALE;
            case "Female":
                return Sex.FEMALE;
            default:
                throw new IllegalArgumentException("Неизвестный пол");
        }
    }

    public static void main(String[] args) {
	    int age_from = Arrays.stream(args)
                .filter(str -> str.startsWith("--from="))
                .map(str -> Integer.parseInt(str.split("=")[1]))
                .findFirst()
                .orElse(Integer.MIN_VALUE);
        int age_to = Arrays.stream(args)
                .filter(str -> str.startsWith("--to="))
                .map(str -> Integer.parseInt(str.split("=")[1]))
                .findFirst()
                .orElse(Integer.MAX_VALUE);
        String filename = Arrays.stream(args)
                .filter(str -> str.startsWith("--filename="))
                .map(str -> str.split("=")[1])
                .findFirst()
                .orElse("data_customers.txt");

        ArrayList<Customer> customerList = new ArrayList<Customer>();
        File file = new File(filename);
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String[] lines = new String[5];
            for(int i = 0;; i++) {
                String line = fileReader.readLine();
                if(line == null) break;
                else lines[i % 5] = line;

                if(i % 5 == 0 && i > 0) customerList.add(new Customer(
                        Integer.parseInt(lines[0]),
                        stringToSex(lines[1]),
                        Integer.parseInt(lines[2]),
                        Integer.parseInt(lines[3]),
                        Integer.parseInt(lines[4])
                ));
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Stream<Customer> suitable_customers = customerList.stream()
                .filter(customer -> customer.getAge() > age_from && customer.getAge() < age_to);
        int suitable_customers_count = suitable_customers.map(customer -> 1).reduce(Integer::sum).orElse(0);

        if(suitable_customers_count == 0) {
            System.out.println("Не найдено подходящих покупателей");
        }
        else {
            int middle_age = suitable_customers
                    .map(Customer::getAge)
                    .reduce(Integer::sum)
                    .orElse(-1) / suitable_customers_count;
            int middle_income = suitable_customers
                    .map(Customer::getIncome)
                    .reduce(Integer::sum)
                    .orElse(-1) / suitable_customers_count;
            int max_income = suitable_customers
                    .map(Customer::getIncome)
                    .reduce(Integer::max)
                    .orElse(-1);

            System.out.println("Подходящих покупателей найдено " + String.valueOf(suitable_customers_count));
            System.out.println("Средний возвраст " + String.valueOf(middle_age));
            System.out.println("Средний доход " + String.valueOf(middle_income));
            System.out.println("Максимальный доход " + String.valueOf(max_income));
        }
    }
}
