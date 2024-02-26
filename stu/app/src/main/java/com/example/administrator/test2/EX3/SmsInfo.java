package com.example.administrator.test2.EX3;

public class SmsInfo {
    private String address;//发送地址
    private long date;//发送时间
    private int type;//类型
    private String body;//内容
    private int id;
    //构造方法
    public SmsInfo(String address, long date,int type, String body) {
        this.address = address;
        this.date = date;
        this.type = type;
        this.body = body;
    }
    public SmsInfo(String address, long date, int type, String body, int id) {
        this.address = address;
        this.date = date;
        this.type = type;
        this.body = body;
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public long getDate() {
        return date;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
}
