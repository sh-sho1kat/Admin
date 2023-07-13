package com.example.admin.adminpart.students;

public class StudentData {
    public String id,email;
    String name,department,phone,gender,address,image,key;

    public StudentData(){

    }
    public StudentData(String id, String name, String department, String phone, String email, String gender, String address, String image, String key) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.image = image;
        this.key = key;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
