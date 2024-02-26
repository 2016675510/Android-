package com.example.administrator.test2.DBComponent.Entity;

import java.util.HashMap;
import java.util.Map;

public class Note_Data {


    private static String username;
    private static String  userId;
    private static String userContent;
    private static String userTime;
    private  static String userTitle;
    private static String userTimeYear;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("content", this.getUserContent());
        map.put("title", this.getUserTitle());
        map.put("time", this.getUserTime());
        map.put("delete", "删除");
        return map;
    }
    public static String getUserTimeYear() {
        return userTimeYear;
    }

    public static void setUserTimeYear(String userTimeYear) {
        Note_Data.userTimeYear = userTimeYear;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Note_Data.username = username;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Note_Data.userId = userId;
    }

    public static String getUserContent() {
        return userContent;
    }

    public static void setUserContent(String userContent) {
        Note_Data.userContent = userContent;
    }

    public static String getUserTime() {
        return userTime;
    }

    public static void setUserTime(String userTime) {
        Note_Data.userTime = userTime;
    }

    public static String getUserTitle() {
        return userTitle;
    }

    public static void setUserTitle(String userTitle) {
        Note_Data.userTitle = userTitle;
    }
}
