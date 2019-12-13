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
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.ClientInfo;
import structure.define;

/**
 *
 * @author Mr.Tran
 */
public class SendConnectionToServer {
    
    private ClientInfo  info;
    
    public SendConnectionToServer(ClientInfo myinfo)
    {
        this.info = myinfo;
    }
    
    public Socket DoConnect()
    {
        Socket socket = null;
        try {
            
            InetAddress ipaddr = InetAddress.getByName(info.getIP());
            socket = new Socket(ipaddr, define.SERVER_PORT);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            
            String cmd = "INFO " + info.getID();
            writer.println(cmd);
            writer.flush();
            
            String response = reader.readLine();
            if(response.startsWith(define.SUCCESS))
            {
                System.out.println(response);
            }
            else
            {
                System.out.println("Error.");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SendConnectionToServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return socket;
    }
}
