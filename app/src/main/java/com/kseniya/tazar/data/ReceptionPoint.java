package com.kseniya.tazar.data;


import java.io.Serializable;


public class ReceptionPoint implements Serializable {

    private int id_key;
    private int id;
    private String name;
    private String address;
    private long type;
    private String price;
    private String work_time;
    private String latitude;
    private String longitude;
    private String phone;
    private String description;
    private String images;
    private String departure;

    public ReceptionPoint() {

    }

    public ReceptionPoint(ReceptionPoint receptionPoint) {
        this.id = receptionPoint.getId();
        this.name = receptionPoint.getName();
        this.address = receptionPoint.getAddress();
        this.type = receptionPoint.getType();
        this.price = receptionPoint.getPrice();
        this.work_time = receptionPoint.getWork_time();
        this.latitude = receptionPoint.getLatitude();
        this.longitude = receptionPoint.getLongitude();
        this.phone = receptionPoint.getPhone();
        this.description = receptionPoint.getDescription();
        this.images = receptionPoint.getImages();
        this.departure = receptionPoint.getDeparture();

    }

    public int getId_key() {
        return id_key;
    }

    public void setId_key(int id_key) {
        this.id_key = id_key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {

        this.departure = departure;
    }
}
