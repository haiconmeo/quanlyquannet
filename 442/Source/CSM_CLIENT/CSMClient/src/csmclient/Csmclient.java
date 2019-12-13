/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csmclient;

import action.RecvConnectionFromServer;
import action.SendConnectionToServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import structure.ClientInfo;
import structure.Global;

/**
 *
 * @author Mr.Tran
 */
public class Csmclient 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
        // connect to server
        ClientInfo info = SetIPserver();
        SendConnectionToServer connect = new SendConnectionToServer(info);
        Socket clSocket = connect.DoConnect();
        
        Global.loginSocket = clSocket;
        
        // Nhan cac ket noi tu server
        // Command cua server
        RecvConnectionFromServer connect1 = new RecvConnectionFromServer();
        connect1.start();
        
        CsmclientLoginGui logingui = new CsmclientLoginGui();
        logingui.setVisible(true); 
        
        Global.loginGui = logingui;
        
    }
    
    private static ClientInfo SetIPserver()
    {
        ClientInfo info = new ClientInfo();
        
        File myfile = new File("ipserver.conf");
        
        if(myfile.exists())
        {
            try 
            {
                BufferedReader buffin;
                buffin = new BufferedReader(new InputStreamReader(new FileInputStream(myfile)));
                
                String ID = buffin.readLine();
                if(ID != null)
                {
                    info.setID(ID);
                }
                else
                {
                    System.out.println("IP server null");
                }
                
                String IP = buffin.readLine();
                if(IP != null)
                {
                    info.setIP(IP);
                }
                else
                {
                    System.out.println("IP server null");
                }
                
                buffin.close();
                
            } catch (IOException ex) {
                System.exit(0);
            }
        }
        return info;
    }
}
