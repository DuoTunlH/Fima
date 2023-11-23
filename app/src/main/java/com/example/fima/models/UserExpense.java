package com.example.fima.models;

public class UserExpense {
    private int id;
    private int userId;
    private int type;
    private String description;
    private String date;
    private double amount;

    public UserExpense(int id, int userId, int type, String description, String date, double amount) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.description = description;
        this.date = date;
        this.amount = amount;
    }
}
