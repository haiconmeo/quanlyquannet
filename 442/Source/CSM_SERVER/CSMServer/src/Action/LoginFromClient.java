/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import database.ConnectSqlServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import structure.Global;
import structure.define;

/**
 *
 * @author Mr.Tran
 */
public class LoginFromClient extends Thread{

    private static final int    WAIT_INFO = 0;
    private static final int    WAIT_USERNAME = 1;
    private static final int    WAIT_PASSWORD = 2;
    private static final int    WAIT_LOGOUT = 3;
    
    private Socket              cSocket;
    private BufferedReader      reader;
    private PrintWriter         writer;
    
    private String              idClient;
    private String              username;
    private String              password;
    //private int                 state;
    
    public LoginFromClient(Socket socket) throws IOException
    {
        this.cSocket = socket;
        this.reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(cSocket.getOutputStream()), true);
    }
    
    @Override
    public void run()
    {
        boolean finish = false;
        
        String line;
        //state = WAIT_INFO;
        try
        {
            while(true)
            {
                line = reader.readLine();
                System.out.println(line);
                
                int state = ParseInput(line); 
                if(line.equals("QUIT"))
                {
                    break;
                }
                else
                {
                    switch(state)
                    {
                        case WAIT_INFO:
                            finish = CmdInfo(line);
                            break;
                        case WAIT_USERNAME:
                            finish = CmdUser(line);
                            break;
                        case WAIT_PASSWORD:
                            finish = CmdPassword(line);
                            break;
                        case WAIT_LOGOUT:
                            finish = CmdLogout(line);
                            break;
                    }
                }
            }//end while
            
            cSocket.close();
        }catch(IOException ex){
        }

        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 1);
        Global.mainGui.main_table.setValueAt(define.DISCONNECT, Integer.parseInt(idClient), 2);
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 3);
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 4);
        
        if(Global.threadtime != null)
        {
            Global.threadtime.stop();
            Global.threadtime = null;
        }
        
        Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 5);
        System.out.println("thread finish");
        
    }
    
    private boolean CmdInfo(String line)
    {
        boolean result = false;
        String ipclient = cSocket.getInetAddress().getHostAddress();
        idClient = "";
        if(line.startsWith("INFO"))
        {
            idClient = GetParameter(line);
            
            Global.mainGui.main_table.setValueAt(ipclient, Integer.parseInt(idClient), 1);
            Global.mainGui.main_table.setValueAt(define.OFFLINE, Integer.parseInt(idClient), 2);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 3);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 4);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 5);
            
            reply(define.SUCCESS, "INFO command success.");
            //state = WAIT_USERNAME;
            result = false;
        }
        else
        {
            reply(define.FAIL, "INFO command fail.");
            result = false;
        }
        
        return result;
    }
    
    private boolean CmdLogout(String line)
    {
        boolean result = false;
        
        if(line.startsWith("LOGOUT"))
        {
            Global.mainGui.main_table.setValueAt(define.OFFLINE, Integer.parseInt(idClient), 2);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 3);
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 4);
            
            
            if(Global.threadtime != null)
            {
                Global.threadtime.stop();
                Global.threadtime = null;
            }
            
            Global.mainGui.main_table.setValueAt("", Integer.parseInt(idClient), 5);
            
            reply(define.SUCCESS, "Logout success.");
        }
        
        //state = WAIT_USERNAME;
        
        System.out.println("LOGOUT finish.");
        return result;
    }
    
    private boolean CmdUser(String line)
    {
        boolean result = false;
        
        if(line.startsWith("USER "))
        {
            username = GetParameter(line);
            reply(define.SUCCESS, "USER command success.");
            //state = WAIT_PASSWORD;
            result = false;
        }
        else
        {
            reply(define.FAIL, "USER command fail.");
            //state = WAIT_USERNAME;
            result = false;
        }
        
        return result;
    }
    
    private boolean  CmdPassword(String line)
    {
        boolean result = false;
        if(line.startsWith("PASS "))
        {
            password  = GetParameter(line);
            System.out.print("pass" +password); 
            if(CheckUserPass() == true)
            {
                reply(define.SUCCESS, "Login successful.");
                
                Calendar startCal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String startTime = sdf.format(startCal.getTime());
                long start = startCal.getTimeInMillis();
                
                Global.mainGui.main_table.setValueAt(define.ONLINE, Integer.parseInt(idClient), 2);
                Global.mainGui.main_table.setValueAt(username, Integer.parseInt(idClient), 3);
                Global.mainGui.main_table.setValueAt(startTime, Integer.parseInt(idClient), 4);
                
                SetTime setTime = new SetTime(start, Integer.parseInt(idClient));
                setTime.start();
                Global.threadtime = setTime;
                
                result = false;
            }
            else
            {
                reply(define.FAIL, "Login fail. Username or password incorrect.");
                result = false;
            }
            
            //state = WAIT_LOGOUT;
        }
        return result;
    }
    
    private boolean CheckUserPass()
    {
        boolean result = false;
        try {
            System.out.print("username:manh repo :"+username); 
            if(!username.equals(""))
            {
                ConnectSqlServer conn = new ConnectSqlServer();
                Connection connection = conn.getConnetion();
                Statement statement = connection.createStatement();
                 System.out.print("den trong nay r");           
                String sql = String.format("SELECT * FROM account WHERE username ='%s'", username);
                System.out.print(sql);
                ResultSet r = statement.executeQuery(sql);
                String pass = "";
//                System.out.print("pas gui denr"+  r.first().getString("password")); 
                if(r.first())
                {
                    pass = r.getString("password");
                    System.out.print("pas gui denr"+  r.getString(3));

                }

                if(password.equals(pass))
                {
                    result = true;
                }
                else
                {
                    result = false;
                }
            }
        } catch (SQLException ex) {
            result = false;
        }
        
        return result;
    }
    
    private String GetParameter(String line)
    {
        String param;
        int p = 0;
        p = line.indexOf(" ");
        
        if(p >= line.length() || p ==-1) {
            param = "";
        }
        else {
            param = line.substring(p+1,line.length());
        }
        
        return param;
    }
    
    private int ParseInput(String line)
    {
        int result = WAIT_INFO;
        
        if(line.startsWith("INFO"))
        {
            result = WAIT_INFO;
        }
        if(line.startsWith("USER"))
        {
            result = WAIT_USERNAME;
        }
        if(line.startsWith("PASS"))
        {
            result = WAIT_PASSWORD;
        }
        if(line.startsWith("LOGOUT"))
        {
            result = WAIT_LOGOUT;
        }
        
        return result;
    }
    
    // Tra loi code ve client
    void reply(String code, String text) {
        writer.println(code + " " + text);
    }
}
