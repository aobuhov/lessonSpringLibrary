package ru.alishev.springcourse.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Neil Alishev
 */
public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String fio;

    @Min(value = 1900, message = "Year of birthdate should be greater than 1900")
    private int year;

    public Person() {

    }

    public Person(int id, String fio, int year) {
        this.id = id;
        this.fio = fio;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
