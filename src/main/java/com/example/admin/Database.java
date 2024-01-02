package com.example.admin;
import java.sql.Connection;
import java.sql.DriverManager;


public class Database {

    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://localhost/rentcar", "root", "");
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}

