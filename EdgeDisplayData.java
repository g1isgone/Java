import javax.swing.*; 
import java.util.*; 
import java.awt.*; 

/**
 *  A class that implements display of an edge. Extends the number class. 
 * 
 *  @author Ji Won Chung
 *  @version CSC 212 December 15, 2016
 */
public class EdgeDisplayData extends Number{
    /** Label of the Edge */
    private String edgeLabel;
    
    /** Color of the EdgeLabel */
    private Color edgeLabelColor;  
    
    /** The color of the edge */
    private Color edgeColor; 
    
    /** The weight of the edge */
    private Double edgeWeight; 
    
    /** The color of the edgeWeight */
    private Color edgeWeightColor; 
    
    /** Constructor for EdgeDisplayData */
    public EdgeDisplayData(){
	this.edgeLabel = "DefaultEdgeLabel";
	this.edgeColor = Color.BLACK; 
	this.edgeLabelColor = Color.BLACK;
	this.edgeWeight = 1.0; 
	this.edgeWeightColor = Color.WHITE; 
    }
    
    /** 
     *  Accessor for edgeWeightColor 
     *
     *  @return The color of the edgeweight 
     */
    public Color getEdgeWeightColor(){
        return this.edgeWeightColor;
    }
    
    /** 
     *  Accessor for edgeWeight 
     *  
     *  @return The edgeWeight
     */
    public double doubleValue(){
        return this.edgeWeight; 
    }
    
    /** 
     *  Accessor for edgeWeight 
     *
     *  @return The edgeWeight
     */
    public double getEdgeWeight(){
        return this.edgeWeight;
    }
    
    /** 
     *  Accessor for edgeLabel
     *  
     *  @return The edgeLabel
     */
    public String getEdgeLabel(){
	return this.edgeLabel; 
    }
    
    /** 
     *  Accessor for edgeLabelColor 
     *
     *  @return The color of the EdgeLabel
     */
    public Color getEdgeLabelColor(){
	return this.edgeLabelColor; 
    } 
    
    /** 
     *  Accessor for edgeColor 
     *  
     *  @return The color of the Edge
     */
    public Color getEdgeColor(){
	return this.edgeColor; 
    } 
    
    /** 
     *  Manipulator for nodeLabel 
     * 
     *  @param newLabel The new label of the edge
     */
    public void setEdgeLabel(String newLabel){
	this.edgeLabel = newLabel;
    }
    
    /** 
     * Manipulator ofr edgeColor 
     * 
     * @param newColor The new color of the edge label
     */
    public void setEdgeLabelColor(Color newColor){
	this.edgeLabelColor = newColor; 
    } 
    
    /** 
     *  Manipulator for edgeColor 
     * 
     *  @param newColor The new color of the edge
     */
    public void setEdgeColor(Color newColor){
	this.edgeColor = newColor; 
    } 
    
    /** 
     *  Manipulator for edgeWeight 
     * 
     *  @param newWeight The new value of the weight of the ege
     */
    public void setEdgeWeight(Double newWeight){
        this.edgeWeight = newWeight;
    }

    /** 
     *  Manipulator for edgeWeightColor
     * 
     *  @param newColor The newColor for the edgeWeight
     */
    public void setEdgeWeightColor(Color newColor){
        this.edgeWeightColor = newColor; 
    }
    
    /** Accessor for floatvalue*/
    public float floatValue(){
        return 0.0f;
        //just a filler 
    }
    
    /** Accessor for longvalue */
    public long longValue(){
        return 0L;
        //just a filler
    }

    /** Accessor for intvalue */
    public int intValue(){
        return 0;
        //just a filler
    }
}