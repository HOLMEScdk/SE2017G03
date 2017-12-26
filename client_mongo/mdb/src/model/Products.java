package model;

import java.util.Date;

public class Products {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_belong() {
        return class_belong;
    }

    public void setClass_belong(String class_belong) {
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

    public Double getRaw_price() {
        return raw_price;
    }

    public void setRaw_price(Double raw_price) {
        this.raw_price = raw_price;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Double getAgent_price() {
        return agent_price;
    }

    public void setAgent_price(Double agent_price) {
        this.agent_price = agent_price;
    }

    public Date getTime_import() {
        return time_import;
    }

    public void setTime_import(Date time_import) {
        this.time_import = time_import;
    }

    public String getTime_end_sale() {
        return time_end_sale;
    }

    public void setTime_end_sale(String time_end_sale) {
        this.time_end_sale = time_end_sale;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    String name;
    String class_belong;
    String[] size;
    String[] color;
    Double raw_price;
    Double sale_price;
    Double agent_price;
    Date time_import;
    String time_end_sale;
    int amount;
}
