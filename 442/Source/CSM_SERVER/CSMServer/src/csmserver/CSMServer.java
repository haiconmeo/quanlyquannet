/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csmserver;

import structure.Global;

/**
 *
 * @author Mr.Tran
 */
public class CSMServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
        CsmserverGui csmgui = new CsmserverGui();
        csmgui.setVisible(true);
        Global.mainGui = csmgui;
    }
}
