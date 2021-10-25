package com.homework1;

enum Sex {
    MALE,
    FEMALE
}

public class Customer {

    private final Integer index;
    private final Sex sex;
    private final Integer age;
    private final Integer income;
    private final Integer costEstimation;

    public Customer(Integer index, Sex sex, Integer age, Integer income, Integer costEstimation) {
        this.index = index;
        this.sex = sex;
        this.age = age; //TODO подумать, возможно стоит проверять, что число положительное
        this.income = income;
        this.costEstimation = costEstimation;
    }

    public Integer getIndex() {
        return index;
    }

    public Sex getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getIncome() {
        return income;
    }

    public Integer getCostEstimation() {
        return costEstimation;
    }
}
