package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private static DB single_instance = null;
    public Connection con;

    private DB() throws ClassNotFoundException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11413686", "sql11413686", "PnahRd9uRq");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getInstance() throws ClassNotFoundException {
        if (single_instance == null)
            single_instance = new DB();
        return single_instance.con;
    }
}

