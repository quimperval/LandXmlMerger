/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landxmlmerger.entities;

import java.util.ArrayList;

/**
 *
 * @author Ehurisa4
 */
public class Alignment {
    
    private String xmlVersion = null;
    private String LandXMLheader = null;
    private String Units = null;
    private String Project = null;
    private String Application = null;
    
    private String AlignmentsHeader = null;
    
    private String name= null;
    
    private Double length = null;
    
    private Double staStart = null;
    
    ArrayList<Entity> listOfEntities = null;
    
    private String description = null;
    
    public Alignment(){
    
    }
    
    public Alignment(String name){
        this.name = name;
    }
    
    public Alignment(String name, Double length){
        this.name = name;
        this.length = length;
        
    }
    
    public Alignment(String name, Double length, Double start ){
        this.name = name;
        this.length = length;
        this.staStart = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getStart() {
        return staStart;
    }

    public void setStart(Double start) {
        this.staStart = start;
    }

    public ArrayList<Entity> getListOfEntities() {
        return listOfEntities;
    }

    public void setListOfEntities(ArrayList<Entity> listOfEntities) {
        this.listOfEntities = listOfEntities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public void addEntity(Entity entity){
    
        if(listOfEntities==null){
            listOfEntities = new ArrayList<Entity>();
        }
            
        listOfEntities.add(entity);
        
    }
    
    public String getBasicData(){
    
        String response = "Name: "+ this.name + ", Length: " + this.length + ", staStart: " + this.staStart + ", desc: " + this.description +"; ";
        
        return response;
    }
    
    public String getStartData(){
    
        String response = "";
        
        return response;
        
    }
    
    public String getEndData(){

        String response = "";
        
        return response;
    }
    
    
    public int getEntitiesCount(){
    
        return this.listOfEntities.size();
    }
    
    public Entity getEntity(int pos){
    
        return this.listOfEntities.get(pos);
    }

    public String getXmlVersion() {
        return xmlVersion;
    }

    public void setXmlVersion(String xmlVersion) {
        this.xmlVersion = xmlVersion;
    }

    public String getLandXMLheader() {
        return LandXMLheader;
    }

    public void setLandXMLheader(String LandXMLheader) {
        this.LandXMLheader = LandXMLheader;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String Units) {
        this.Units = Units;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String Project) {
        this.Project = Project;
    }

    public String getApplication() {
        return Application;
    }

    public void setApplication(String Application) {
        this.Application = Application;
    }

    public Double getStaStart() {
        return staStart;
    }

    public void setStaStart(Double staStart) {
        this.staStart = staStart;
    }

    public String getAlignmentsHeader() {
        return AlignmentsHeader;
    }

    public void setAlignmentsHeader(String AlignmentsHeader) {
        this.AlignmentsHeader = AlignmentsHeader;
    }
    
    public void recalculateLen(){
        
        double newLen =  0.00;
        
        for(int i=0; i<this.listOfEntities.size(); i++){
            newLen = newLen + this.listOfEntities.get(i).getLength();
        }
        
        this.setLength(newLen);
    }
    
    
}
