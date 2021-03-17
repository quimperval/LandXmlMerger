/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landxmlmerger.entities;

/**
 *
 * @author Ehurisa4
 */
public abstract class Entity {
    
    //Line 
    //Curve
    
    private String EntType = "";
    
    private Double xStart = null; 
    private Double yStart = null; 
    private Double xEnd = null; 
    private Double yEnd = null; 
    
    private Double length = null;
           
    public Entity(){
        
    }

    public Entity(String EntType){
        
        this.EntType = EntType;
    }
    
    public String getEntType() {
        return EntType;
    }

    public void setEntType(String EntType) {
        this.EntType = EntType;
    }

    public Double getxStart() {
        return xStart;
    }

    public void setxStart(Double xStart) {
        this.xStart = xStart;
    }

    public Double getyStart() {
        return yStart;
    }

    public void setyStart(Double yStart) {
        this.yStart = yStart;
    }

    public Double getxEnd() {
        return xEnd;
    }

    public void setxEnd(Double xEnd) {
        this.xEnd = xEnd;
    }

    public Double getyEnd() {
        return yEnd;
    }

    public void setyEnd(Double yEnd) {
        this.yEnd = yEnd;
    }
    
    
    public abstract String getBasicInfo();
    
    public String getStart(){
    
        String response = "xStart: " + this.xStart + ", yStart: " + this.yStart;
        
        return response;
    }
    
    public String getEnd(){
    
        String response = "xEnd: " + this.xEnd + ", yEnd: " + this.yEnd;
        
        return response;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    
    
}
