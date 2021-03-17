/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package landxmlmerger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import landxmlmerger.entities.Alignment;
import landxmlmerger.entities.Constants;
import landxmlmerger.entities.Curve;
import landxmlmerger.entities.Line;

/**
 *
 * @author Ehurisa4
 */
public class LandXMLWriter {
    

    private static LandXMLWriter instance = null;
    
    
    private LandXMLWriter(){
    
    }
    
    public static LandXMLWriter getInstance(){
        if(instance==null){
            instance = new LandXMLWriter();
        }
        
        return instance;
    }
    
    public  void writeFromAlignment(Alignment input, String output){
    
        FileWriter fw = null;
        try {
            System.out.println("writeFromAlignment");
            
            fw = new FileWriter(output);
            
            fw.write(input.getXmlVersion()+"\n");
            fw.write(input.getLandXMLheader()+"\n");
            fw.write(input.getUnits()+"\n");
            fw.write(input.getProject()+"\n");
            fw.write(input.getApplication()+"\n");
            fw.write(input.getAlignmentsHeader()+"\n");
            
            String alnTag = getAlignmentTag(input);
            fw.write("\t\t"+alnTag+ "\n");
            
            fw.write("\t\t\t"+getOpeningTag(Constants.COORD_GEOM));
            
            for(int i =0; i<input.getEntitiesCount(); i++){
                
                if(input.getEntity(i).getEntType().equals(Constants.LINETYPE)){
                    String mLineStr = buildLineBody((Line) input.getEntity(i));
                    fw.write(mLineStr);
                    
                }
                
                if(input.getEntity(i).getEntType().equals(Constants.CURVETYPE)){
                    String mCurveStr = buildCurveBody((Curve) input.getEntity(i));
                    
                    fw.write(mCurveStr);
                }
            
            }
            
            
            fw.write("\t\t\t"+getClosingTag(Constants.COORD_GEOM));
            fw.write("\t\t</Alignment>" + "\n");
            fw.write("\t</Alignments>" + "\n");
            fw.write("</LandXML>");
            
            
          
            
            
        } catch (IOException ex) {
            Logger.getLogger(LandXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(LandXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    
    private static String getAlignmentTag(Alignment input){
        String response= "";
        
        /**
         * <Alignment name="Eje_6.0-Left-3.2500 (3)" length="1665.73567203005" staStart="734.910418787769" desc="">
         * 
         */
        response = "<Alignment name=\"" + input.getName()+"\" length=\""+ input.getLength() 
                +"\" staStart=\"" + input.getStaStart() +"\" desc=\""+ input.getDescription() +"\">";
    
        return response;
    }
    
    
    private String getOpeningTag(String value){
        return "<"+value+">\n";
    }
    
    private String getClosingTag(String value){
        return "</"+value+">\n";
    }
    
    private String buildLineBody(Line input){
    
        StringBuffer response = new StringBuffer();
        
        //<Line dir="5.648247373622" length="194.778774070003">
        
        response.append( "\t\t\t\t\t<Line dir=\"" + input.getDir() + "\" length=\"" + input.getLength()+ "\">\n");
        
        //Northing Easting
        //<Start>5658662.102070896886 537525.854114135844</Start>
        response.append("\t\t\t\t\t\t<Start>"+input.getyStart()+" "+input.getxStart()+"</Start>\n");
        
	//<End>5658681.272377368063 537719.687212903867</End>
        response.append("\t\t\t\t\t\t<End>"+input.getyEnd()+" "+input.getxEnd()+"</End>\n");        
        response.append("\t\t\t\t\t</Line>\n");
        return response.toString();
    }


    private String buildCurveBody(Curve input){
    
         StringBuffer response = new StringBuffer();
        
        //<Curve rot="ccw" chord="1.812090476007" crvType="arc" delta="2.209182360202" dirEnd="7.857429704244" 
        //dirStart="5.648247344042" external="0.008735605014" length="1.812202730914" midOrd="0.008733981682" 
        //radius="46.999998720774" tangent="0.906213639142">
        
        response.append("\t\t\t\t\t<Curve rot=\"" +input.getRot() + "\" chord=\"" +input.getChord() + "\" crvType=\""+ input.getCurvType() 
                +"\" delta=\"" + input.getDelta()+"\" dirEnd=\""+input.getDirEnd() +"\"");
        
        response.append(" dirStart=\""+input.getDirStart() +"\" external=\""+ input.getExternal() +"\" length=\""+ input.getLength() 
                +"\" midOrd=\"" +input.getMidOrd()+ "\" ");
        
        response.append("radius=\""+ input.getRadius()+"\" tangent=\""+input.getTangent() + "\">\n");
        
        /**
         * 
         * <Start>5658681.272377368063 537719.687212903867</Start>
					<Center>5658728.044185221195 537715.061429734109</Center>
					<End>5658681.485454917885 537721.486732242745</End>
					<PI>5658681.361567749642 537720.589026762522</PI>
         */
        
        response.append("\t\t\t\t\t\t<Start>"+input.getyStart()+" "+input.getxStart()  +"</Start>\n");
        response.append("\t\t\t\t\t\t<Center>"+input.getyCenter()+" "+input.getxCenter()  +"</Center>\n");
        response.append("\t\t\t\t\t\t<End>"+input.getyEnd()+" "+input.getxEnd()  +"</End>\n");
        response.append("\t\t\t\t\t\t<PI>"+input.getyPI()+" "+input.getxPI()  +"</PI>\n");
        
        response.append("\t\t\t\t\t</Curve>\n");
        
        return response.toString();
        
    }
    
}


