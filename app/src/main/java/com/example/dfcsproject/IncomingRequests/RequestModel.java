package com.example.dfcsproject.IncomingRequests;

public class RequestModel {
    public String pdfdata,user_email,sender_email,datetime,user_name,user_bio ,from;
    public RequestModel()
    {

    }
    public RequestModel(String pdfdata, String user_email, String sender_email, String datetime, String user_name, String user_bio,String from) {
        this.pdfdata = pdfdata;
        this.user_email = user_email;
        this.sender_email = sender_email;
//        this.id = id;
        this.from = from;
        this.datetime = datetime;
        this.user_name = user_name;
        this.user_bio = user_bio;
    }

    public String getPdfdata() {
        return pdfdata;
    }

    public void setPdfdata(String pdfdata) {
        this.pdfdata = pdfdata;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

//

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_bio() {
        return user_bio;
    }

    public void setUser_bio(String user_bio) {
        this.user_bio = user_bio;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
