/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import structure.Global;

/**
 *
 * @author Mr.Tran
 */
public class SetTime extends Thread{
    
    private long start;
    private int row;
    public SetTime(long tStart, int _row)
    {
        this.start = tStart;
        this.row = _row;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            Calendar endCal = Calendar.getInstance();
            //long starttime = startcal.getTimeInMillis();
            long endtime = endCal.getTimeInMillis();

            int minute = (int) ((endtime - start)/1000/60);
            //int monney = minute*50;
            Global.mainGui.main_table.setValueAt(String.valueOf(minute), row, 5);
            
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SetTime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
