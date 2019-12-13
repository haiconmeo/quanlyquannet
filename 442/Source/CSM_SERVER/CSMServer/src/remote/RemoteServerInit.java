/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import structure.define;

/**
 *
 * @author Mr.Tran
 */
public class RemoteServerInit extends Thread{
    
    JDesktopPane desktop;
    ClientHandler handler;
    
    public RemoteServerInit(JDesktopPane desk)
    {
        desktop = desk;
    }
    
    @Override
    public void run()
    {
        try 
        {
            ServerSocket sSocket = new ServerSocket(define.CMD_DESKTOP_PORT);
            Socket cSocket = sSocket.accept();
            
            System.out.println("remote nhan 1 socket");
            
            handler = new ClientHandler(cSocket, desktop);
            handler.start();
                    
        } catch (IOException ex) {
            Logger.getLogger(RemoteServerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void childthreadstop()
    {
        handler.childthreadstop();
        handler.stop();
    }
}
