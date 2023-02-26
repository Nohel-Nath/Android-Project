package com.login_signup_screendesign_demo;

public class Student {
    private String fullname;
    private String emailId;
    private String mobile;
    private String locat;
    private String pass;
    private String conf;

    public Student() {
    }

    public Student(String fullname, String emailId, String mobile, String locat, String pass, String conf) {
        this.fullname = fullname;
        this.emailId = emailId;
        this.mobile = mobile;
        this.locat = locat;
        this.pass = pass;
        this.conf = conf;
    }




    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocat() {
        return locat;
    }

    public void setLocat(String locat) {
        this.locat = locat;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }
}
