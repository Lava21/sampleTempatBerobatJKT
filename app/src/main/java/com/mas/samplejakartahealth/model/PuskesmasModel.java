package com.mas.samplejakartahealth.model;

import java.io.Serializable;

public class PuskesmasModel implements Serializable {
    private String namaPuskesmas, location, telepon, faximile, email, kepalaPuskesmas;
    private double latitude, longitude;

    public String getNamaPuskesmas() {
        return namaPuskesmas;
    }

    public void setNamaPuskesmas(String namaPuskesmas) {
        this.namaPuskesmas = namaPuskesmas;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getFaximile() {
        return faximile;
    }

    public void setFaximile(String faximile) {
        this.faximile = faximile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKepalaPuskesmas() {
        return kepalaPuskesmas;
    }

    public void setKepalaPuskesmas(String kepalaPuskesmas) {
        this.kepalaPuskesmas = kepalaPuskesmas;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
