package model;

import java.util.Date;

public class Customer {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AddExtraInfo[] getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(AddExtraInfo[] extra_info) {
        this.extra_info = extra_info;
    }

    public Carts[] getCarts() {
        return carts;
    }

    public void setCarts(Carts[] carts) {
        this.carts = carts;
    }

    public Order[] getOrder() {
        return order;
    }

    public void setOrder(Order[] order) {
        this.order = order;
    }

    public Date getRegistert_time() {
        return registert_time;
    }

    public void setRegistert_time(Date registert_time) {
        this.registert_time = registert_time;
    }

    String name;
    String password;
    AddExtraInfo[] extra_info;
    Carts[] carts;
    Order[] order;
    Date registert_time;
}
