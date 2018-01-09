package model;

import java.util.ArrayList;
import java.util.Date;

public class Order {

    public ArrayList<String> getClass_belong() {
        return class_belong;
    }

    public void setClass_belong(ArrayList<String> class_belong) {
        this.class_belong = class_belong;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }

    public ArrayList<String> getColor() {
        return color;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }

    public ArrayList<Integer> getAmount() {
        return amount;
    }

    public void setAmount(ArrayList<Integer> amount) {
        this.amount = amount;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Date getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(Date deal_time) {
        this.deal_time = deal_time;
    }

    public ArrayList<String> getProduct_name() {
        return product_name;
    }

    public void setProduct_name(ArrayList<String> product_name) {
        this.product_name = product_name;
    }

    public ArrayList<Double> getSingle_price() {
        return single_price;
    }

    public void setSingle_price(ArrayList<Double> single_price) {
        this.single_price = single_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    ArrayList<String> product_name;
    ArrayList<String> class_belong;
    ArrayList<String> size;
    ArrayList<String> color;
    ArrayList<Double> single_price;
    ArrayList<Integer> amount;
    double total_money;
    String status;
    Date deal_time;
    String id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String user_id;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;
}
