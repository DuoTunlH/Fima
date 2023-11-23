package com.example.fima.models;

public class UserTodo {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String date;
    private double amount;

    public UserTodo(int id, int userId, String title, String description, String date, double amount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.amount = amount;
    }
}
