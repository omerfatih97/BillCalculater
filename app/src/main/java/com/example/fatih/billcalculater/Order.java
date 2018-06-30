package com.example.fatih.billcalculater;

public class Order {
    String desk;
    String food;
    Integer quantity;
    Double price;

    public Order(String desk, String food, Double price,Integer quantity) {
        super();
        this.desk = desk;
        this.food = food;
        this.price = price;
        this.quantity=quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
