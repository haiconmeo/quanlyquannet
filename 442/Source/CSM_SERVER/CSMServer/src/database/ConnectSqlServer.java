package database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author killua
 * 
 * Connect to database
 * 
 */
public class ConnectSqlServer 
{
    String driverName;
    String url;
    String dbname;
    String username;
    String password;
    static Connection connection;
    
    public ConnectSqlServer()
    {
        connection = null;
    }
    
    public void CreateConnection()
    {
        try{
            driverName = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://localhost:3306/";
            dbname = "manh";
            username = "root";
            password = "";
            
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(url+dbname, username, password);
            if (connection != null) {
                System.out.println("Connected");
            }
            else{
                System.out.println("deo");
            }
            
        }catch(Exception ex){
        }
    }
    
    public Connection getConnetion()
    {
        if(connection == null)
        {
            CreateConnection();
        }
        return connection;
    }
            
    public void closeConnection() throws SQLException
    {
        if(connection != null)
        {
            connection.close();
        }
    }
}
     
