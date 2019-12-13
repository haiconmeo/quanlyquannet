/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr.Tran
 */
public class CsmConnection extends Thread{
    
    private static final int CSMPORT = 10000;
    
    private Vector<Socket> lstsocket;
    private Vector<Thread> lstclientthr;
    public CsmConnection()
    {
        lstsocket = new Vector<>();
        lstclientthr = new Vector<>();
    }

    @Override
    public void run() 
    {
        try 
        {
            //mở socket chờ client kết nối tới
            ServerSocket serversock  = new ServerSocket(CSMPORT);
            
            // với mỗi kết nối tới tạo 1 thread để thực hiện login từ phía client
            while(true)
            {
                Socket clientsock = serversock.accept();
                //lstsocket.add(clientsock);
                
                System.out.println("server accept 1 client");
                        
                LoginFromClient clientlogin = new LoginFromClient(clientsock);
                clientlogin.start();
                
                
                //lstclientthr.add(clientlogin);
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(CsmConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
