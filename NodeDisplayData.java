import javax.swing.*;
import java.util.*;
import java.awt.*; 

/**
 *  Class that implements display of a node. 
 *
 *  @author Ji Won Chung 
 *  @version CSC 212 December 15, 2016
 */
public class NodeDisplayData{
    /** The label of the node */
    private String nodeLabel; 
    
    /** The color of the nodeLabel*/
    private Color nodeLabelColor; 
    
    /** The distance to the node from another node for Dikstra's shortest-path algorithm*/
    private String distanceLabel; 
    
    /** The color of the distanceLabel*/
    private Color distanceLabelColor; 
    
    /** The color of the node */
    private Color nodeColor; 

    /** The point of the node */
    private Point coordPoint;
    
    
    /** Constructor for NodeDisplayData */
    public NodeDisplayData(){
	this.distanceLabel = ""; 
	this.nodeLabel = "DefaultNodeLabel"; 
	this.nodeColor = Color.RED; 
	this.coordPoint = new Point(0,0); 
	this.nodeLabelColor = Color.CYAN;
	this.distanceLabelColor = Color.GREEN; 
    }
    
    /** 
     * Constructor for NodeDisplayData
     *  
     *  @param dataPoint The coordinate of the node 
     */
    public NodeDisplayData(Point dataPoint){
	this.distanceLabel = ""; 
	this.nodeLabel = "DefaultNodeLabel"; 
	this.nodeColor = Color.RED;
	this.coordPoint = dataPoint; 
	this.nodeLabelColor = Color.CYAN;
	this.distanceLabelColor = Color.GREEN; 
    }
    
    /** 
     * Accessor for nodeLabel
     *
     * @return The nodeLabel 
     */
    public String getNodeLabel(){
	return this.nodeLabel;
    }
    
    /**
     *  Accessor for distanceLabel 
     * 
     *  @return The distanceLabel 
     */
    public String getDistanceLabel(){
	return this.distanceLabel; 
    }
    
    /** 
     *  Accessor for nodeColor 
     * 
     *  @return The color of the node 
     */
    public Color getNodeColor(){
	return this.nodeColor;
    }
    
    /** 
     *  Accessor for nodeLabelColor 
     *
     *  @return The color of the node label
     */
    public Color getNodeLabelColor(){
        return this.nodeLabelColor;
    }
    
    /** Accessor for distanceLabelColor 
     * 
     *  @return The color of the distance label
     */
    public Color getDistanceLabelColor(){
        return this.distanceLabelColor;
    }
    
    /** Accessor for coordPoint 
     *
     *  @return The coordinates of the node
     */
    public Point getCoordinate(){
	return this.coordPoint; 
    }
    
    /** 
     *  Accessor for coordPoint xCoord 
     * 
     *  @return the x-coordinate of the Node
     */
    public Double getXCoordinate(){
	return this.coordPoint.getX(); 
    }

    /** 
     *  Accessor for coordPoint yCoord
     * 
     *  @return the y-coordinate of the Node
     */
    public Double getYCoordinate(){
	return this.coordPoint.getY(); 
    } 
    
    
    /**
     *   Mainpulator for nodeLabel
     *   
     *   @param newLabel The new label that you want to set the node label to 
     */
    public void setNodeLabel(String newLabel){
	this.nodeLabel = newLabel; 
    }
    
    /** 
     *  Manipulator for distanceLabel 
     *  
     *  @param newLabel The new label that you want to set the distance label to 
     */
    public void setDistanceLabel(String newLabel){
	this.distanceLabel = newLabel; 
    }
    
    /** 
     *  Mainpulator for nodeColor 
     *
     *  @param newColor The new color you want to set the node color to 
     */
    public void setNodeColor(Color newColor){
	this.nodeColor = newColor; 
    }

    /** 
     *  Mainpulator for nodeLabelColor 
     * 
     *  @param newColor The new color you want to set the color of the node label to 
     */
    public void setNodeLabelColor(Color newColor){
	this.nodeLabelColor = newColor; 
    }
    
    /** 
     *  Mainpulator for nodeLabelColor 
     *  
     *  @param newColor The new color you want for the distance label
     */
    public void setDistanceLabelColor(Color newColor){
	this.distanceLabelColor = newColor;
    }
    
    /** 
     *  Manipulator for coordPoint 
     * 
     *  @param newPoint The new coordinates of the node
     */
    public void setCoordinate(Point newPoint){
	this.coordPoint = newPoint; 
    } 
    
    /** 
     *  Manipulator for coordPoint xCoord
     *  
     *  @param newXCoord The new x-coordinate of the node
     */
    public void setXCoord(Integer newXCoord){
	this.coordPoint.x = newXCoord;
    }
    
    /** 
     *  Manipulator for coordPoint yCoord
     * 
     *  @param newYCoord The new y-coordinate of the node
     */
    public void setYCoord(Integer newYCoord){
	this.coordPoint.y = newYCoord; 
    } 
}
