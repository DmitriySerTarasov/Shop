package ru.netology.com.example;


public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {

    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;

    }
    @Override
    public String toString() {
        return "Employee\n {\n" +
                "\tid = " + id +
                ",\n\tfirstName = " + firstName +
                ",\n\tlastName = " + lastName +
                ",\n\tcountry = " + country +
                ",\n\tage = " + age +
                "\n}";

    }

}
