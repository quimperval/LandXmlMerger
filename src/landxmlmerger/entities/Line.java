/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landxmlmerger.entities;

/**
 *
 * @author THX1138
 */
public class Line extends Entity implements Cloneable{
    
    private Double dir = null;
    
    
    
    public Line(){
        super(Constants.LINETYPE);
    }

    public Double getDir() {
        return dir;
    }

    public void setDir(Double dir) {
        this.dir = dir;
    }
   
    @Override
    public String getBasicInfo(){
        
        String response = "";
        
        response = "Type: "+ super.getEntType() +",dir: " + this.dir + ", length: " + this.getLength() + ";";
        
        return response;
    }
    
    @Override
    public Line clone() {
        try {
            return (Line) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Line();
        }
}
    
}


