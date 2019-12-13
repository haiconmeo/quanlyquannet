/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import structure.define;

/**
 *
 * @author Mr.Tran
 */
public class LoginToServer {
    
    private String          username = null;
    private String          password = null;
    private Socket          socket;
    private BufferedReader  reader;
    private PrintWriter     writer;
    
    public LoginToServer(String usern, String pass, Socket sock)
    {
        this.username = usern;
        this.password = pass;
        this.socket = sock;
        try{
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }catch(Exception ex){
            System.out.println("Loi Reader va Writer");
        }
        
    }
    
    public boolean DoLogin()
    {
        boolean result = false;
        try 
        {
            //gửi username cho server
            writer.println("USER "+username);
            writer.flush();
            
            String response = reader.readLine();
            if(!response.startsWith(define.SUCCESS))   //USER cmd fail
            {
                System.out.println("Server reply: "+response);
                result = false;
            }
            else    //USER cmd success
            {
                writer.println("PASS "+password);
                writer.flush();
                
                response = reader.readLine();
                if(!response.startsWith(define.SUCCESS))   //Login fail
                {
                    System.out.println("Server reply: "+response);
                    result = false;
                }
                else    // Login success.
                {
                    System.out.println("Server reply: "+response);
                    result = true;
                }
            }
        } catch (IOException ex) {
            System.out.println("Reader error.");
        }
        return result;
    }
}
