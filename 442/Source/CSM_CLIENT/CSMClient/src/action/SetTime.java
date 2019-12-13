/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.Global;

/**
 *
 * @author Killua
 */
public class SetTime extends Thread{
    
    private long start;
    
    public SetTime(long tStart)
    {
        this.start = tStart;
    }
    
    @Override
    public void run()
    {
        
        if(Global.mainGui != null)
        {
            int i = 0;
            while(true)
            {
                Calendar endCal = Calendar.getInstance();
                long endtime = endCal.getTimeInMillis();
                
                int minute = (int)((int)(endtime - start)/1000/60);
                int monney = minute*50;

                String mytimer = getTimeElapsed(endtime);
                
                Global.mainGui.clock.setText(mytimer);

                if( i==0)
                {
                    Global.mainGui.txt_use_time.setText(String.valueOf(minute));
                    Global.mainGui.txt_cost.setText(String.valueOf(monney));
                }

                i++;
                if(i==60)
                {
                    i=0;
                }
                
                try {    
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SetTime.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    private String getTimeElapsed(long endtime)
    {
        //Calendar endcal = Calendar.getInstance();
        //long endtime = endcal.getTimeInMillis();
        long elapsedTime = endtime - start;
        elapsedTime = elapsedTime / 1000;

        String seconds = Integer.toString((int)(elapsedTime % 60));
        String minutes = Integer.toString((int)((elapsedTime % 3600) / 60));
        String hours = Integer.toString((int)(elapsedTime / 3600));

        if (seconds.length() < 2) {
            seconds = "0" + seconds;
        }

        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }

        if (hours.length() < 2) {
            hours = "0" + hours;
        }

        return hours+":"+minutes+":"+seconds;
    }
}
