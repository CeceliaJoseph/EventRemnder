package model;

public class ProfileData {

    private String ful_name;
    private String email;
    private String mobile_no;

    public String getName() {
        return ful_name;
    }

    public void setName(String name) {
        this.ful_name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile_no;
    }

    public void setMobile(String mobile) {
        this.mobile_no= mobile;
    }
}
