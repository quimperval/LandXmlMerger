/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landxmlmerger;
import java.util.ArrayList;
import landxmlmerger.entities.*;
/**
 *
 * @author Ehurisa4
 */
public class LandXMLMerger {

    /**
     * @param args the command line arguments
     */
    
    private static Entity AuxEntity = null;
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //System.out.println("XD");
        
        //System.out.println("Createing LINE Object");
       
        String folder = "C:\\PRY\\P-014 Vientos del Secano\\04 Documentacion temporal\\Eje6Lxml\\";
        
        String mainFile = "Eje6SecondMergedBottom.xml";
        
        String partToAdd = "ToMergeBottomC.xml";
    
        String outputXmlFile = folder + "Eje6ThirdMergedBottom.xml";
        
        LandXMLReader mReader = LandXMLReader.getInstance();
        Alignment alnOne = null;
        Alignment alnToMerge = null;
        
        if(mReader!=null){
        
            alnOne = mReader.getFileEntities(folder+mainFile);
            
            alnToMerge = mReader.getFileEntities(folder+partToAdd);
            showAlnData(alnOne);
            validateLen(alnOne);
            showAlnData(alnToMerge);
            
            if(alnOne!=null && alnToMerge!=null){
                int elementWhereStartsMerge = getStartPointOfCoinc(alnOne, alnToMerge);
                System.out.println("elementWhereStartsMerge: " + elementWhereStartsMerge);
                
                if(AuxEntity!=null){
                    System.out.println("AuxEntity is not null");
                    System.out.println(AuxEntity.getStart());
                    System.out.println(AuxEntity.getEnd());
                   
                }
                
                int elementWhereEndsMerge = getEndPointOfCoinc(alnOne, alnToMerge);
                System.out.println("elementWhereEndsMerge: " + elementWhereEndsMerge);
                
                if(elementWhereStartsMerge>=0){
                    System.out.println("Length before: " + ((Line) alnOne.getEntity(elementWhereStartsMerge)).getLength());
                    setNewEndPointForElement( alnOne, alnToMerge,elementWhereStartsMerge);
                    System.out.println("Length after: " + ((Line) alnOne.getEntity(elementWhereStartsMerge)).getLength());
                }
                
                
                if(elementWhereEndsMerge>=0){
                    
                    if(elementWhereStartsMerge!=elementWhereEndsMerge) {
                        System.out.println("Length before: " + ((Line) alnOne.getEntity(elementWhereEndsMerge)).getLength());
                        setNewStartPointForElement(alnOne, alnToMerge, elementWhereEndsMerge);
                        System.out.println("Length after: " + ((Line) alnOne.getEntity(elementWhereEndsMerge)).getLength());
                    } else {
                        if(elementWhereStartsMerge==elementWhereEndsMerge ) {
                            System.out.println("END AND START IS IN THE SAME ELEMENT");
                            
                            System.out.println(AuxEntity.getBasicInfo());
                            System.out.println(AuxEntity.getStart());
                            System.out.println(AuxEntity.getEnd());
                            System.out.println("Updating new element");
                            setNewStartPointForElement(AuxEntity, alnToMerge);
                            System.out.println("Updated values");
                            System.out.println(AuxEntity.getStart());
                            System.out.println(AuxEntity.getEnd());
                            
                        }
                    }
                    
                    
                }
                
                ArrayList<Entity> listOfNewEntities = new ArrayList<Entity>();
                
                
                
                for(int i= 0; i<(elementWhereStartsMerge+1)  ; i++){
                    listOfNewEntities.add(alnOne.getEntity(i));
                }
                
                System.out.println("Size of new Entities array: " + listOfNewEntities.size());
                
              
                
                //Placing the elementos we want to merge
                for(int i=0; i<alnToMerge.getEntitiesCount(); i++){
                    listOfNewEntities.add(alnToMerge.getEntity(i));
                }
                
                System.out.println("Size of new Entities array: " + listOfNewEntities.size());
                
                if(elementWhereStartsMerge==elementWhereEndsMerge && AuxEntity!=null){
                    System.out.println("Adding auxiliary element");
                    listOfNewEntities.add(AuxEntity);
                }
                
                System.out.println("Size of new Entities array: " + listOfNewEntities.size());
                
                if(elementWhereEndsMerge!=elementWhereStartsMerge){
                    //Continue adding the rest of elements of the first array
                    for(int i=elementWhereEndsMerge; i< alnOne.getEntitiesCount(); i++){
                        listOfNewEntities.add(alnOne.getEntity(i));
                    }
                } else {
                    if(elementWhereEndsMerge==elementWhereStartsMerge){
                        
                        if((elementWhereEndsMerge+1)< (alnOne.getEntitiesCount())){
                            for(int i=(elementWhereEndsMerge+1); i< alnOne.getEntitiesCount(); i++){
                            listOfNewEntities.add(alnOne.getEntity(i));
                        }
                        }
                    
                    }
                }
                
                
                
                System.out.println("Size of new Entities array: " + listOfNewEntities.size());
                
                alnOne.setListOfEntities(listOfNewEntities);
                
                System.out.println("Size of new Entities array: " + alnOne.getEntitiesCount());
                
                LandXMLWriter mWriter = LandXMLWriter.getInstance();
                
                System.out.println("Length: " + alnOne.getLength());
                alnOne.recalculateLen();
                System.out.println("Recalculated Length: " + alnOne.getLength());
                mWriter.writeFromAlignment(alnOne,outputXmlFile );
                
            }
            
            
            
        }
        
        
        
    }
    
    private static void showAlnData(Alignment aln){
    if(aln!=null){
        
            int countOfLines = 0;
            int countOfCurves = 0;
            
            for(int i = 0; i<aln.getEntitiesCount(); i++){
                
                Entity mEnt = aln.getEntity(i);
                if(mEnt.getEntType().equals(Constants.LINETYPE)){
                    //System.out.println(Constants.LINETYPE);
                    countOfLines++;
                }
                
                if(mEnt.getEntType().equals(Constants.CURVETYPE)){
                    //System.out.println(Constants.CURVETYPE);
                    countOfCurves++;
                }
            }
            
            System.out.println("Alignment has: " + countOfLines + " lines");
            System.out.println("Alignment has: " + countOfCurves + " curves");
            
        }
    
    }
    
    private static int getStartPointOfCoinc(Alignment main, Alignment secToMerge){
        System.out.println("--------------------");
        System.out.println("getStartPointOfCoinc");
        int response = -999;
        
        //System.out.println("Size: " + main.getEntitiesCount());
        
        for(int i =0; i<main.getEntitiesCount(); i++){
            System.out.println("Counter: " + i);
            Entity mEnt = main.getEntity(i);
            
            //System.out.println(mEnt.getBasicInfo());
            
            if(mEnt!=null){
                double mainXStart = mEnt.getxStart();
                double mainYStart = mEnt.getyStart();
                double mainXEnd = mEnt.getxEnd();
                double mainYEnd = mEnt.getyEnd();
            
                double secXStart = secToMerge.getEntity(0).getxStart();
                double secYStart = secToMerge.getEntity(0).getyStart();
                
                double secXEnd = secToMerge.getEntity(0).getxEnd();
                double secYEnd = secToMerge.getEntity(0).getyEnd();
                
                if( (secXStart>mainXEnd && secYStart>mainYEnd) || 
                        (secXStart<mainXStart && secYStart<mainYStart)) {
                    //Do nothing
                } else {
                      if(mEnt.getEntType().equals(Constants.LINETYPE)){
                    Double yValue = getForecastedIntersectionYValue(mEnt, secXStart);
                    System.out.println("forecasted Y: " + yValue);
                    System.out.println("secYStart: " + secYStart);
                    Double difference = yValue - secYStart;
                    
                    difference = Math.abs(difference.doubleValue());
                    System.out.println("Difference: "+difference );
                    if(difference<0.000002){
                        System.out.println("Starts in :" + i);
                        AuxEntity = ((Line) mEnt).clone();
                        System.out.println(mEnt.getBasicInfo());
                        System.out.println(AuxEntity.getBasicInfo());
                        response = i;
                        break;
                    }
                }
                }
            
              
             }
            
            
            
                    
        }
        
        return response;
    }
 
    private static Double getForecastedIntersectionYValue(Entity entity, Double xValue){
        //System.out.println("getForecastedIntersectionYValue");
        Double response = null;
        
        //y = mx + b
        // m = (y2 - y1)/ (x2 - x1)
        
        Double deltaY = entity.getyEnd() - entity.getyStart();
        //System.out.println("deltaY: " + deltaY);
        
        Double deltaX = entity.getxEnd() - entity.getxStart();
        
        //System.out.println("deltaX: " + deltaX);
        Double slope = deltaY / deltaX;
        
        //System.out.println("Slope: " + slope);
        
        //System.out.println("yEnd: " + entity.getyEnd() );
        Double b = entity.getyEnd() - slope*entity.getxEnd();
        //System.out.println("b: " + b);
        //System.out.println("xValue: " + xValue);
        response = slope*xValue + b;

        return response;
    }
    
    
    private static void validateLen(Alignment input){
    
        double len = 0;
        for(int i=0; i<input.getEntitiesCount(); i++){
        
            if(input.getEntity(i).getEntType().equals(Constants.LINETYPE)){
                Line mLine = (Line) input.getEntity(i);
                
                len = len + mLine.getLength();
            
            }
            
            if(input.getEntity(i).getEntType().equals(Constants.CURVETYPE)){
                Curve mCurve = (Curve) input.getEntity(i);
                
                len = len + mCurve.getLength();
            
            }
        }
        
        System.out.println("Sum of lengths: " + len);
        System.out.println("Alignment Len: "  + input.getLength());
        
    }
 
    private static void setNewEndPointForElement(Alignment aln, Alignment toMerge ,int pos){
        System.out.println("setNewEndPointForElement");
        if(pos>=0){
            if(aln.getEntity(pos).getEntType().equals(Constants.LINETYPE)){
                
                double xNewEnd = toMerge.getEntity(0).getxStart();
                double yNewEnd = toMerge.getEntity(0).getyStart();
                
                System.out.println("xNewEnd: " + xNewEnd + ", yNewEnd: " + yNewEnd);
                
                Line mLine = (Line) aln.getEntity(pos);
                mLine.setxEnd(xNewEnd);
                mLine.setyEnd(yNewEnd);
                
                Double deltaX = mLine.getxEnd() - mLine.getxStart();
                Double deltaY = mLine.getyEnd() - mLine.getyStart();
                Double newLen = Math.pow(deltaX*deltaX + deltaY*deltaY, 0.5);
                
                mLine.setLength(newLen);
                
            }
        }
        
    
    }
    
    
     private static int getEndPointOfCoinc(Alignment main, Alignment secToMerge){
        System.out.println("--------------------");
         System.out.println("getEndPointOfCoinc");
        int response = -999;
        
        //System.out.println("Size: " + main.getEntitiesCount());
        
        for(int i =0; i<main.getEntitiesCount(); i++){
           System.out.println("Counter: " + i);
            Entity mEnt = main.getEntity(i);
            
            //System.out.println(mEnt.getBasicInfo());
            
            if(mEnt!=null){
                double mainXStart = mEnt.getxStart();
                double mainYStart = mEnt.getyStart();
                double mainXEnd = mEnt.getxEnd();
                double mainYEnd = mEnt.getyEnd();
            
                System.out.println("Item in the main alignment");
                System.out.println(mEnt.getStart());
                System.out.println(mEnt.getEnd());
                
                int itemsToMerge = secToMerge.getEntitiesCount();
                System.out.println("Last Item to merge");
                System.out.println(secToMerge.getEntity(itemsToMerge-1).getStart());
                System.out.println(secToMerge.getEntity(itemsToMerge-1).getEnd());
                
                double secXEnd = secToMerge.getEntity(itemsToMerge-1).getxEnd();
                double secYEnd = secToMerge.getEntity(itemsToMerge-1).getyEnd();
                
                
                
                if( (secXEnd>mainXEnd && secYEnd>mainYEnd) || 
                        (secXEnd<mainXStart && secYEnd<mainYStart)) {
                    //Do nothing
                    System.out.println("Doing Nothing");
                } else {
                     if(mEnt.getEntType().equals(Constants.LINETYPE)){
                    Double yValue = getForecastedIntersectionYValue(mEnt, secXEnd);
                    System.out.println("forecasted Y: " + yValue);
                    System.out.println("secYEnd: " + secYEnd);
                    Double difference = yValue - secYEnd;
                    System.out.println("Difference: "+difference );
                    difference = Math.abs(difference);
                    System.out.println("Abs of Difference: "+difference );
                    if(difference.doubleValue()<0.00000001){
                        
                        response = i;
                        break;
                    }
                }
                
                }
                
            
               
             }
            
            
            
                    
        }
        
        return response;
    }
    
     private static void setNewStartPointForElement(Alignment aln, Alignment toMerge ,int pos){
         System.out.println("setNewStartPointForElement");
        if(pos>=0){
            if(aln.getEntity(pos).getEntType().equals(Constants.LINETYPE)){
                
                int items = toMerge.getEntitiesCount();
                
                double xNewStart = toMerge.getEntity(items-1).getxEnd();
                double yNewStart = toMerge.getEntity(items-1).getyEnd();
                
                System.out.println("NewStarT: " + xNewStart + ", newSTartY: " + yNewStart);
                
                Line mLine = (Line) aln.getEntity(pos);
                mLine.setxStart(xNewStart);
                mLine.setyStart(yNewStart);
                
                Double deltaX = mLine.getxEnd() - mLine.getxStart();
                Double deltaY = mLine.getyEnd() - mLine.getyStart();
                Double newLen = Math.pow(deltaX*deltaX + deltaY*deltaY, 0.5);
                
                mLine.setLength(newLen);
                
            }
        }
        
    
    }
    
     
     private static void setNewStartPointForElement(Entity mLine, Alignment toMerge){
         System.out.println("setNewStartPointForElement");
        
            if(mLine.getEntType().equals(Constants.LINETYPE)){
                
                
                
                 int items = toMerge.getEntitiesCount();
                
                double xNewStart = toMerge.getEntity(items-1).getxEnd();
                double yNewStart = toMerge.getEntity(items-1).getyEnd();
                
                System.out.println("NewStarT: " + xNewStart + ", newSTartY: " + yNewStart);
                
                //Line mLine = (Line) aln.getEntity(pos);
                mLine.setxStart(xNewStart);
                mLine.setyStart(yNewStart);
                
                Double deltaX = mLine.getxEnd() - mLine.getxStart();
                Double deltaY = mLine.getyEnd() - mLine.getyStart();
                Double newLen = Math.pow(deltaX*deltaX + deltaY*deltaY, 0.5);
                
                mLine.setLength(newLen);
                
            }
        
        
    
    }
}
