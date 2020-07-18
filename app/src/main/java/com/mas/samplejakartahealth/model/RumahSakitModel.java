package com.mas.samplejakartahealth.model;

import java.io.Serializable;

public class RumahSakitModel implements Serializable {
    private String nama_rs, jenis_rs, kode_pos, location, telepon, email, website,
            faximile;
    private double latitude, longitude;

    public String getNama_rs() {
        return nama_rs;
    }

    public void setNama_rs(String nama_rs) {
        this.nama_rs = nama_rs;
    }

    public String getJenis_rs() {
        return jenis_rs;
    }

    public void setJenis_rs(String jenis_rs) {
        this.jenis_rs = jenis_rs;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFaximile() {
        return faximile;
    }

    public void setFaximile(String faximile) {
        this.faximile = faximile;
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
