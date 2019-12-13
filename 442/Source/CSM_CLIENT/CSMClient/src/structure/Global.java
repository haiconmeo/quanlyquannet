/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.net.Socket;

/**
 *
 * @author Mr.Tran
 */
public class Global {
    public static csmclient.CsmclientLoginGui loginGui = null;
    public static csmclient.CsmclientMainGui mainGui = null;
    public static remote.RemoteClientInit remoteThread = null;
    
    public static Socket    loginSocket = null;
    public static Socket    cmdSocket = null;
}
