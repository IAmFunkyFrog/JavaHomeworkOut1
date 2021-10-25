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

    private static void printInfoFor(Customer[] suitable_customers) {
        int middle_age = Arrays.stream(suitable_customers)
                .map(Customer::getAge)
                .reduce(Integer::sum)
                .orElse(-1) / suitable_customers.length;
        int middle_income = Arrays.stream(suitable_customers)
                .map(Customer::getIncome)
                .reduce(Integer::sum)
                .orElse(-1) / suitable_customers.length;
        int max_income = Arrays.stream(suitable_customers)
                .map(Customer::getIncome)
                .reduce(Integer::max)
                .orElse(-1);

        System.out.println("Подходящих покупателей найдено " + String.valueOf(suitable_customers.length));
        System.out.println("Средний возвраст " + String.valueOf(middle_age));
        System.out.println("Средний доход " + String.valueOf(middle_income));
        System.out.println("Максимальный доход " + String.valueOf(max_income));
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
                if(i % 5 == 0 && i > 0) customerList.add(new Customer(
                        Integer.parseInt(lines[0]),
                        stringToSex(lines[1]),
                        Integer.parseInt(lines[2]),
                        Integer.parseInt(lines[3]),
                        Integer.parseInt(lines[4])
                ));

                String line = fileReader.readLine();
                if(line == null) break;
                else lines[i % 5] = line;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Customer[] suitable_customers_by_age = customerList.stream()
                .filter(customer -> customer.getAge() > age_from && customer.getAge() < age_to)
                .toArray(Customer[]::new);

        System.out.println("Информация для женщин: ");
        printInfoFor(Arrays.stream(suitable_customers_by_age).filter(customer -> customer.getSex() == Sex.FEMALE).toArray(Customer[]::new));
        System.out.println();
        System.out.println("Информация для мужчин: ");
        printInfoFor(Arrays.stream(suitable_customers_by_age).filter(customer -> customer.getSex() == Sex.MALE).toArray(Customer[]::new));
    }
}
