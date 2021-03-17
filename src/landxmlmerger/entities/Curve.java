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
public class Curve extends Entity{
    
    
    
    
    private String rot;
    private Double chord;
    private String curvType;
    private Double delta;
    private Double dirEnd;
    private Double dirStart;
    private Double external;

    private Double midOrd;
    private Double radius;
    private Double tangent;
    
    private Double xCenter;
    private Double yCenter;
    
    private Double xPI;
    private Double yPI;
    
    public Curve(){
        super(Constants.CURVETYPE);
    }

    @Override
    public String getBasicInfo() {
       /**
     * 1  rot="ccw" 
     * 2  chord="1.812090476007" 
     * 3  crvType="arc" 
     * 4  delta="2.209182360202" 
     * 5  dirEnd="7.857429704244" 
     * 6  dirStart="5.648247344042" 
     * 7  external="0.008735605014" 
     * 8  length="1.812202730914" 
     * 9  midOrd="0.008733981682" 
     * 10 radius="46.999998720774" 
     * 11 tangent="0.906213639142">
     *                                      * 
     */
        String response = "type: " + super.getEntType() + "rot " + this.rot + ", chord: " + this.chord +
                ", crvType: " + this.curvType + ", delta: " + this.delta + ", dirend: " + this.dirEnd 
                + ", dirStart: " + this.dirStart  + ", external: " + this.external + ", length: "
                + this.getLength() + ", midOrd: " + this.midOrd + ", radius: " + this.radius + ", tangent: "
                + this.tangent
                ;
        
        
        return response;

    }

    public String getRot() {
        return rot;
    }

    public void setRot(String rot) {
        this.rot = rot;
    }

    public Double getChord() {
        return chord;
    }

    public void setChord(Double chord) {
        this.chord = chord;
    }

    public String getCurvType() {
        return curvType;
    }

    public void setCurvType(String curvType) {
        this.curvType = curvType;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Double getDirEnd() {
        return dirEnd;
    }

    public void setDirEnd(Double dirEnd) {
        this.dirEnd = dirEnd;
    }

    public Double getDirStart() {
        return dirStart;
    }

    public void setDirStart(Double dirStart) {
        this.dirStart = dirStart;
    }

    public Double getExternal() {
        return external;
    }

    public void setExternal(Double external) {
        this.external = external;
    }

 

    public Double getMidOrd() {
        return midOrd;
    }

    public void setMidOrd(Double midOrd) {
        this.midOrd = midOrd;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getTangent() {
        return tangent;
    }

    public void setTangent(Double tangent) {
        this.tangent = tangent;
    }

    public Double getxCenter() {
        return xCenter;
    }

    public void setxCenter(Double xCenter) {
        this.xCenter = xCenter;
    }

    public Double getyCenter() {
        return yCenter;
    }

    public void setyCenter(Double yCenter) {
        this.yCenter = yCenter;
    }

    public Double getxPI() {
        return xPI;
    }

    public void setxPI(Double xPI) {
        this.xPI = xPI;
    }

    public Double getyPI() {
        return yPI;
    }

    public void setyPI(Double yPI) {
        this.yPI = yPI;
    }
    
    
    
}
