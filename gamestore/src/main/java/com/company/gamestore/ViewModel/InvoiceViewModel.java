package com.company.gamestore.ViewModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class InvoiceViewModel {
    @NotNull
    @Size(max = 50)
    private String name;
    @NotNull
    @Size(max = 100)
    private String street;
    @NotNull
    @Size(max = 50)
    private String city;
    @NotNull
    @Size(max = 20)
    private String state;
    @NotNull
    @Size(max = 10)
    private String zipcode;
    @NotNull
    @Size(max = 50)
    private String item_type;
    @NotNull
    private int item_id;
    @NotNull
    private int quantity;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public int getItem_id() {
        return item_id;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}