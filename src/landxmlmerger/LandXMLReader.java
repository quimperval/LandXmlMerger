/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landxmlmerger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import landxmlmerger.entities.Alignment;
import landxmlmerger.entities.Entity;
import landxmlmerger.entities.Constants;
import landxmlmerger.entities.Curve;
import landxmlmerger.entities.Line;

/**
 *
 * @author Ehurisa4
 */
public class LandXMLReader {
    
    private static LandXMLReader instance = null;
    
    
    private LandXMLReader(){
    
    }
    
    public static LandXMLReader getInstance(){
        
        if(instance==null){
        
            instance = new LandXMLReader();
        
        }
        
        return instance;
       
    }
    
    
    public Alignment getFileEntities(String fileName){
    
        Alignment response = new Alignment();
        
        
        BufferedReader br = null;
        
        boolean isLookingForAlignments = true;
        
        boolean isLookingForSingleAlignment = false;
        
        boolean isCheckigForGeometry = false;
        boolean isReadingUnits = false;
        
        boolean isCheckingForLine = false;
        boolean isCheckingForCurve = false;
        
        try {
            System.out.println("getFileEntities for: " + fileName);
            
            br = new BufferedReader(new FileReader(fileName));
            
            String line = "";
            
            while((line=br.readLine())!=null){
                //System.out.println(line);
            
                if(isLookingForAlignments){
                    
                    if(line.contains("<?xml")){
                        if(line.contains(">")){
                            response.setXmlVersion(line);
                        }
                    }
                    
                    if(line.contains("<LandXML") && line.contains(">")){
                        response.setLandXMLheader(line);
                    }
                    
                    if(line.contains("<Units>") && !line.contains("</Units")){
                        
                        response.setUnits(line+"\n");
                        //System.out.println(line);
                        isReadingUnits = true;
                        //System.out.println("continue");
                        continue;
                        
                    }
                    
                    if(isReadingUnits){
                        String mUnits = response.getUnits();
                        //System.out.println(mUnits);
                        mUnits = mUnits + line + "\n";
                        //System.out.println(mUnits);
                        response.setUnits(mUnits);
                    }
                    
                    if(line.contains("</Units>")){
                        String mUnits = response.getUnits();
                        mUnits = mUnits + line ;
                        //response.setUnits(mUnits);
                        isReadingUnits = false;
                        continue;
                    }
                    
                    
                    if(line.contains("<Project") && line.contains("</Project>")){
                        response.setProject(line);
                    }
                    
                    if(line.contains("<Application") && line.contains("</Application>")){
                        response.setApplication(line);
                    }
                    
                    
                    if(line.contains("<"+ Constants.ALIGNMENTS)){
                        //System.out.println(line);
                        
                        response.setAlignmentsHeader(line);
                        isLookingForAlignments = false;
                        isLookingForSingleAlignment = true;
                    }
                } else {
                    if(isLookingForSingleAlignment){
                        if(line.contains("<"+Constants.ALIGNMENT) && !line.contains("</" + Constants.ALIGNMENT)){
                            
                            //System.out.println(line);
                            
                            if(line.contains(Constants.NAME)){
                                String name = getTagValue(line, Constants.NAME);
                                if(name!=null){
                                    
                                    if(response==null){
                                        response = new Alignment(name);
                                    }
                                    
                                    response.setName(name);
                                    
                                
                                }
                            }
                            
                            if(line.contains(Constants.LENGTH)){
                                String lenAsString = getTagValue(line, Constants.LENGTH);
                                //System.out.println(lenAsString);
                                response.setLength(Double.valueOf(lenAsString));
                            }
                            
                            if(line.contains(Constants.STASTART)){
                                String staStartStr = getTagValue(line, Constants.STASTART);
                                //System.out.println(staStartStr);
                                response.setStart(Double.valueOf(staStartStr));
                            }
                            
                              if(line.contains(Constants.DESCRIPTION)){
                                String desc = getTagValue(line, Constants.DESCRIPTION);
                                //System.out.println("des: " + desc + " desc length " + desc.length());
                                response.setDescription(desc);
                            }
                            
                            
                            isCheckigForGeometry = true;
                            System.out.println(response.getBasicData());
                        }
                    
                    } else {

                    }
                    
                     if(isCheckigForGeometry){
                            //System.out.println(line);
                            if(!isCheckingForLine){
                                if(line.contains("<" + Constants.LINETYPE) && !line.contains("</"+Constants.LINETYPE)){
                                    
                                    //System.out.println(line);
                                    
                                    String dirStr = getTagValue(line, Constants.DIR);
                                    
                                    String lenStr = getTagValue(line, Constants.LENGTH);
                                    
                                    Double mDir = Double.valueOf(dirStr);
                                    Double mLen = Double.valueOf(lenStr);
                                    
                                    if(dirStr!=null && lenStr!=null){
                                        
                                        Line mLine = new Line();
                                        mLine.setLength(mLen);
                                        mLine.setDir(mDir);
                                        response.addEntity(mLine);
                                    }
                                    
                                    if(response.getEntitiesCount()>0){
                                    
                                        //System.out.println(response.getEntity(response.getEntitiesCount()-1).getBasicInfo());
                                        
                                    }
                                    
                                    
                                    isCheckingForLine  = true;
                                } else {
                                    //System.out.println(line);
                                  // System.out.println("Not considered case A");
                                }
                            } else {
                               if(line.contains("</"+Constants.LINETYPE )){
                                   isCheckingForLine = false;
                                   continue;
                               }
                               
                               if(line.contains("<"+Constants.START + ">") && line.contains("</"+Constants.START + ">")){
                                   //System.out.println(line);
                                   // System.out.println("TAG: " + Constants.START);
                                   Double[] startCoords = getArrayOfDouble(line);
                                   //System.out.println(Arrays.toString(startCoords));
                                   
                                   Entity mEntity = response.getEntity(response.getEntitiesCount()-1);
                                   mEntity.setxStart(startCoords[0]);
                                   mEntity.setyStart(startCoords[1]);
                                   //System.out.println(mEntity.getStart());
                                   
                               }
                               
                                if(line.contains("<"+Constants.END + ">") && line.contains("</"+Constants.END + ">")){
                                   //System.out.println(line);
                                   //System.out.println("TAG: " + Constants.END);
                                   Double[] endCoords = getArrayOfDouble(line);
                                   //System.out.println(Arrays.toString(endCoords));
                                   
                                   Entity mEntity = response.getEntity(response.getEntitiesCount()-1);
                                   mEntity.setxEnd(endCoords[0]);
                                   mEntity.setyEnd(endCoords[1]);
                                   //System.out.println(mEntity.getEnd());
                               }
                            }
                            
                            
                            if(!isCheckingForCurve) {
                                if(line.contains("<" + Constants.CURVETYPE) && !line.contains("</"+Constants.CURVETYPE)){
                                
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
                                    
                                    String rotStr = getTagValue(line, Constants.ROT);
                                    String chordStr = getTagValue(line, Constants.CHORD);
                                    String crvTypeStr = getTagValue(line, Constants.CRVTYPE);
                                    String deltaStr = getTagValue(line, Constants.DELTA);
                                    String dirEndStr = getTagValue(line, Constants.DIREND);
                                    String dirStartStr = getTagValue(line, Constants.DIRSTART);
                                    String externalStr = getTagValue(line, Constants.EXTERNAL);
                                    String lenStr = getTagValue(line, Constants.LENGTH);
                                    String midOrdStr = getTagValue(line, Constants.MIDORD);
                                    String radiusStr = getTagValue(line, Constants.RADIUS);
                                    String tanStr = getTagValue(line, Constants.TANGENT);
                                    
                                    
                                    
                                    if(rotStr!=null && chordStr!=null && crvTypeStr!=null && deltaStr!=null
                                            && dirEndStr!=null && dirStartStr!=null && externalStr!=null 
                                            && lenStr!=null && midOrdStr!=null && radiusStr!=null && tanStr!=null ){
                                    
                                        
                                        
                                        Double mChord = Double.valueOf(chordStr);
                                        Double mDelta = Double.valueOf(deltaStr);
                                        Double mDirEnd = Double.valueOf(dirEndStr);
                                        Double mDirStart = Double.valueOf(dirStartStr);
                                        Double mExternal = Double.valueOf(externalStr);
                                        Double mLength = Double.valueOf(lenStr);
                                        Double mMidOrd = Double.valueOf(midOrdStr);
                                        Double mRadius = Double.valueOf(radiusStr);
                                        Double mTan = Double.valueOf(tanStr);
                                        
                                        Curve mCurve = new Curve();
                                        mCurve.setRot(rotStr);
                                        mCurve.setChord(mChord);
                                        mCurve.setCurvType(crvTypeStr);
                                        mCurve.setDelta(mDelta);
                                        mCurve.setDirEnd(mDirEnd);
                                        mCurve.setDirStart(mDirStart);
                                        mCurve.setExternal(mExternal);
                                        mCurve.setExternal(mExternal);
                                        mCurve.setLength(mLength);
                                        mCurve.setMidOrd(mMidOrd);
                                        mCurve.setRadius(mRadius);
                                        mCurve.setTangent(mTan);
                                        
                                        //System.out.println(mCurve.getBasicInfo());
                                        response.addEntity(mCurve);
                                        
                                    }
                                    
                                isCheckingForCurve = true;
                                }
                            
                            } else {
                                if(line.contains("</"+Constants.CURVETYPE )){
                                   isCheckingForCurve = false;
                                   continue;
                               }
                                
                                if(line.contains("<"+Constants.START + ">") && line.contains("</"+Constants.START + ">")){
                                   //System.out.println(line);
                                   //System.out.println("TAG: " + Constants.START);
                                   Double[] startCoords = getArrayOfDouble(line);
                                   //System.out.println(Arrays.toString(startCoords));
                                   
                                   Entity mEntity = response.getEntity(response.getEntitiesCount()-1);
                                   mEntity.setxStart(startCoords[0]);
                                   mEntity.setyStart(startCoords[1]);
                                   //System.out.println(mEntity.getStart());
                                   
                               }
                                
                                if(line.contains("<"+Constants.END + ">") && line.contains("</"+Constants.END + ">")){
                                   //System.out.println(line);
                                   //System.out.println("TAG: " + Constants.END);
                                   Double[] endCoords = getArrayOfDouble(line);
                                   

                                   //System.out.println(Arrays.toString(endCoords));
                                   
                                   Entity mEntity = response.getEntity(response.getEntitiesCount()-1);
                                   mEntity.setxEnd(endCoords[0]);
                                   mEntity.setyEnd(endCoords[1]);
                                   //System.out.println(mEntity.getEnd());
                                   
                               }
                               
                                if(line.contains("<"+Constants.CENTER + ">") && line.contains("</"+Constants.CENTER + ">")){
                                   //System.out.println(line);
                                   //System.out.println("TAG: " + Constants.CENTER);
                                   Double[] centerCoords = getArrayOfDouble(line);
                                   //System.out.println(Arrays.toString(centerCoords));
                                   
                                   String mType = response.getEntity(response.getEntitiesCount()-1).getEntType();
                                   if(mType.equals(Constants.CURVETYPE)){
                                   
                                       Curve mCurve = (Curve) response.getEntity(response.getEntitiesCount()-1);
                                       
                                       mCurve.setxCenter(centerCoords[0]);
                                       mCurve.setyCenter(centerCoords[1]);
                                   }
                                   
                               }
                                
                                 if(line.contains("<"+Constants.PINF + ">") && line.contains("</"+Constants.PINF + ">")){
                                   //System.out.println(line);
                                   //System.out.println("TAG: " + Constants.PINF);
                                   Double[] pInfCoords = getArrayOfDouble(line);
                                   //System.out.println(Arrays.toString(pInfCoords));
                                   
                                   String mType = response.getEntity(response.getEntitiesCount()-1).getEntType();
                                   if(mType.equals(Constants.CURVETYPE)){
                                   
                                       Curve mCurve = (Curve) response.getEntity(response.getEntitiesCount()-1);
                                       
                                       mCurve.setxPI(pInfCoords[0]);
                                       mCurve.setyPI(pInfCoords[1]);
                                   }
                                   
                               }
                            
                            }
                        
                        }
                    
                }
                
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LandXMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LandXMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(LandXMLReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return response;
    }
    
    
  
    
    
    private String getTagValue(String input, String varName){
        //System.out.println(varName);
    
        int namePos = input.indexOf(varName);
        
        String subStr = input.substring(namePos+(varName.length()+2));
        
        //System.out.println(subStr);
        
        subStr = subStr.substring(0,subStr.indexOf("\""));
        
        //System.out.println(subStr);      
        
        return subStr;
    }
    
    private Double[] getArrayOfDouble(String input){
    
        Double[] response = null;
        
        int iniPos = input.indexOf(">");
        int endPos = input.indexOf("</");
        
        String subStr = input.substring(iniPos+1, endPos);
        String[] mArray = subStr.split("\\ ");
        
        
        //System.out.println(Arrays.toString(mArray));
        
        response = new Double[mArray.length];
        
        response[0] = Double.valueOf(mArray[1]);
        response[1] = Double.valueOf(mArray[0]);
        
        
        return response;
    
    }
    
}
