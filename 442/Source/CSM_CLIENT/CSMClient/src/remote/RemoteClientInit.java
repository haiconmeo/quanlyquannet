
/*
 * Author Ahmed Abdelhalim - 2009
 * Email: englemo@hotmail.com
 * Please do not remove the above lines
 */

package remote;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import structure.define;

/**
 *
 * This class is responsible for connecting to the server
 * and starting ScreenSpyer and ServerDelegate classes
 */
public class RemoteClientInit extends Thread{

    Socket socket = null;
    String ipserver;
    
    ScreenSpyer screen;
    ServerDelegate serdelegate;
    public RemoteClientInit(String ip)
    {
        this.ipserver = ip;
    }
    

    @Override
    public void run(){

        Robot robot = null; //Used to capture the screen
        Rectangle rectangle = null; //Used to represent screen dimensions

        try {
            System.out.println("Connecting to server ..........");
            socket = new Socket(ipserver, define.CMD_DESKTOP_PORT);
            System.out.println("Connection Established.");

            //Get default screen device
            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=gEnv.getDefaultScreenDevice();

            //Get screen dimensions
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            //Prepare Robot object
            robot = new Robot(gDev);

            //draw client gui
            //drawGUI();
            //ScreenSpyer sends screenshots of the client screen
            screen = new ScreenSpyer(socket,robot,rectangle);
            screen.start();
            
            //ServerDelegate recieves server commands and execute them
            serdelegate = new ServerDelegate(socket,robot);
            serdelegate.start();
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (AWTException ex) {
                ex.printStackTrace();
        }
    }
    
    public void stopthread()
    {
        serdelegate.stop();
        screen.stop();
        this.stop();
    }

    /*
    private void drawGUI() {
        JFrame frame = new JFrame("Remote Admin");
        JButton button= new JButton("Terminate");
        
        frame.setBounds(100,100,150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
      );
      frame.setVisible(true);
    }
    */
}
