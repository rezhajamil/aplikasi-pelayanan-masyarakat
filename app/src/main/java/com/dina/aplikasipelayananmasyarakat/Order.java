package com.dina.aplikasipelayananmasyarakat;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    String id;
    String user_email;
    String purpose;
    String ktp;
    String kk;
    String status;
    String description;
    String akte;
    Date created_at;

    public Order(String id, String user_email, String purpose, String ktp, String kk, String status, String description, String akte, Date date) {
        this.id=id;
        this.user_email=user_email;
        this.purpose=purpose;
        this.ktp=ktp;
        this.kk=kk;
        this.status=status;
        this.description=description;
        this.akte=akte;
        this.created_at=date;
    }

    public String getId(){
        return id;
    }

    public String getUser_email(){
        return user_email;
    }

    public String getPurpose(){
        return purpose;
    }

    public String getKtp(){
        return ktp;
    }

    public String getKk(){
        return kk;
    }

    public String getStatus(){
        return status;
    }

    public String getDescription(){
        return description;
    }

    public String getAkte(){
        return akte;
    }

    public Date getCreated_at(){
        return created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public void setKk(String kk) {
        this.kk = kk;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAkte(String akte) {
        this.akte = akte;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(id);
//        parcel.writeString(user_email);
//        parcel.writeString(purpose);
//        parcel.writeString(ktp);
//        parcel.writeString(kk);
//        parcel.writeString(status);
//        parcel.writeString(description);
//        parcel.writeString(akte);
//        parcel.write
//    }
}
