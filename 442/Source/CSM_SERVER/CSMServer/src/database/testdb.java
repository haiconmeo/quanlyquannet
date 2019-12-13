/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import database.ConnectSqlServer;
/**
 *
 * @author hoang
 */
public class testdb {
       public static void main(String[] args) {
        
        // TODO code application logic here
        ConnectSqlServer s = new ConnectSqlServer();
//        s.getConnetion();
        s.CreateConnection();
        
    }
}
