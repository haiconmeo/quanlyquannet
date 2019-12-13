/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import remote.RemoteServerGui;
import structure.MyProcess;
import structure.define;

/**
 *
 * @author Mr.Tran
 */
public class Commands {
    
    private Socket                  socket;
    private BufferedReader          reader;
    private PrintWriter             writer;
    
    public Commands(Socket sock)
    {
        this.socket = sock;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }catch(IOException ex){
        }
    }
    
    public boolean LoginButton()
    {
        boolean result = false;
        String cmd = "LOGIN";
        try
        {
            writer.println(cmd);
            writer.flush();

            
            String response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }catch(IOException ex){
        }
        return result;
    }
    
    public boolean LogoutButton()
    {
        boolean result = false;
        String cmd = "LOGOUT";
        try
        {
            writer.println(cmd);
            writer.flush();

            String response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }catch(IOException ex){
        }
        return result;
    }
    
    public boolean ShutdownButton()
    {
        boolean result = false;
        String cmd = "SHUTDOWN";
        try
        {
            writer.println(cmd);
            writer.flush();

            String response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }catch(IOException ex){
        }
        return result;
    }
    
    public boolean RestartButton()
    {
        boolean result = false;
        String cmd = "RESTART";
        try
        {
            writer.println(cmd);
            writer.flush();

            String response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }catch(IOException ex){
        }
        return result;
    }
    
    public boolean DesktopButton()
    {
        boolean result = false;
        
        remote.RemoteServerGui remote = new RemoteServerGui(socket, writer);
        remote.setVisible(true);
        
        String cmd = "DESKTOP";
        try
        {
            writer.println(cmd);
            writer.flush();

            System.out.println("send DESKTOP.");
            
            String response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                result = true;
                System.out.println("DESKTOP succ.");
            }
            else
            {
                result = false;
                System.out.println("DESKTOP fail.");
            }
            
        }catch(IOException ex){
            System.out.println("Error DESKTOP.");
        }
        
        return result;
    }
    
    public Vector<MyProcess> AppsButton()
    {
        Vector<MyProcess> listProc = new Vector<>();
        writer.println("APPS");
        try {
            ObjectInputStream objReader = new ObjectInputStream(socket.getInputStream() );
            listProc = (Vector<MyProcess>) objReader.readObject();
            
            if(listProc != null)
            {
                System.out.println(listProc.size());
                writer.println(define.SUCCESS);
            }
            else
            {
                writer.println(define.FAIL);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listProc;
    }
    
    
    public boolean KillButton(String pid)
    {
        boolean result = false;
        
        writer.println("KILL");
        String response;
        try {
            response = reader.readLine();
            if(response.equals(define.SUCCESS))
            {
                writer.println(pid);
                
                response = reader.readLine();
                if(response.equals(define.SUCCESS))
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
