package com.example.logindemo;

/**
 * Created by aqsakawan on 4/20/2018.
 */

public class restaurant {
    private int rest_id;
    private String name;
    private String address;
    private long contact;

    public restaurant() {}

    public restaurant(int rest_id,String name, String address, long contact){
            this.rest_id = rest_id;
            this.name = name;
            this.address = address;
            this.contact = contact;
    }

    public String getName_rest() {
        return name;
    }
    public String getAddress_rest() {
        return address;
    }
    public long getContact_rest() {
        return contact;
    }
    public int get_id() {
        return rest_id;
    }


}
