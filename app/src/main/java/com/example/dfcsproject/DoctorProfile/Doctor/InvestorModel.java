package com.example.dfcsproject.DoctorProfile.Doctor;


//public class package com.capcun.womenentrepreneur.FetchUserProfile;

public class InvestorModel {


    public String user_bio,user_email,user_mobile,user_name,user_type;

    public InvestorModel() {

    }

    public InvestorModel(String user_bio, String user_email, String user_mobile, String user_name, String user_type) {
        this.user_bio = user_bio;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.user_name = user_name;
        this.user_type = user_type;
    }

    public String getUser_bio() {
        return user_bio;
    }

    public void setUser_bio(String user_bio) {
        this.user_bio = user_bio;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}