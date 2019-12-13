/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.Serializable;

/**
 *
 * @author Killua
 */
public class MyProcess implements Serializable{
    
    private String imagename;
    private String pid;

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
