package com.homework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static Sex stringToSex(String string) {
        switch (string) {
            case "Male":
                return Sex.MALE;
            case "Female":
                return Sex.FEMALE;
        }
        return null;
    }

    private static void printInfoFor(List<Customer> suitable_customers) {
        if (suitable_customers.size() == 0) {
            System.out.println("Не найдено подходящих покупателей");
            return;
        }

        int middle_age = 0;
        int middle_income = 0;
        int max_income = Integer.MIN_VALUE;
        for(Customer customer : suitable_customers)  {
            middle_age += customer.getAge();
            middle_income += customer.getIncome();
            max_income = Integer.max(max_income, customer.getIncome());
        }
        middle_age /= suitable_customers.size();
        middle_income /= suitable_customers.size();

        System.out.println("Подходящих покупателей найдено " + String.valueOf(suitable_customers.size()));
        System.out.println("Средний возвраст " + String.valueOf(middle_age));
        System.out.println("Средний доход " + String.valueOf(middle_income));
        System.out.println("Максимальный доход " + String.valueOf(max_income));
    }

    public static void main(String[] args) throws Exception {
        int age_from = Integer.MIN_VALUE;
        int age_to = Integer.MAX_VALUE;
        String filename = "data_customers.txt";

        if (args.length > 0) age_from = Integer.parseInt(args[0]);
        if (args.length > 1) age_to = Integer.parseInt(args[1]);

        ArrayList<Customer> customerList = new ArrayList<Customer>();
        File file = new File(filename);
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String[] lines = new String[5];
        for (int i = 0; ; i++) {
            if (i % 5 == 0 && i > 0) customerList.add(new Customer(
                    Integer.parseInt(lines[0]),
                    stringToSex(lines[1]),
                    Integer.parseInt(lines[2]),
                    Integer.parseInt(lines[3]),
                    Integer.parseInt(lines[4])
            ));

            String line = fileReader.readLine();
            if (line == null) break;
            else lines[i % 5] = line;
        }

        ArrayList<Customer> suitable_customers_female = new ArrayList<Customer>();
        ArrayList<Customer> suitable_customers_male = new ArrayList<Customer>();

        for (Customer customer : customerList) {
            if (customer.getAge() > age_from && customer.getAge() < age_to) {
                if (customer.getSex() == Sex.FEMALE) suitable_customers_female.add(customer);
                else suitable_customers_male.add(customer);
            }
        }

        System.out.println("Информация для женщин: ");
        printInfoFor(suitable_customers_female);
        System.out.println();
        System.out.println("Информация для мужчин: ");
        printInfoFor(suitable_customers_male);
    }
}
