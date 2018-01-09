package model;

public class Order {
    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getClass_belong() {
        return class_belong;
    }

    public void setClass_belong(String[] class_belong) {
        this.class_belong = class_belong;
    }

    public String[] getSize() {
        return size;
    }

    public void setSize(String[] size) {
        this.size = size;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public int[] getAmount() {
        return amount;
    }

    public void setAmount(int[] amount) {
        this.amount = amount;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }

    String[] name;
    String[] class_belong;
    String[] size;
    String[] color;
    int[] amount;
    double total_money;
}
