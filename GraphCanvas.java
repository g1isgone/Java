import java.util.*;
import java.awt.*;
import javax.swing.*;     

/**
 *  Implements a graphical canvas that displays a list of points.
 *
 *  @author  Ji Won Chung
 *  @version CSC 212, December 15, 2016 
 */
class GraphCanvas extends JComponent {
    /** Keeps track of the graph we want to draw*/
    public Graph<NodeDisplayData, EdgeDisplayData> graph; 
    
    /** Constructor for the graph canvas
     *  
     *  @param graph The graph we are taking in to draw. 
     */
    public GraphCanvas(Graph<NodeDisplayData, EdgeDisplayData> graph) {
	this.graph = graph; 
    }
    
    /**
     *  Draws the nodes and points
     *
     *  @param g The graphics object to draw with. 
     */
    public void paintComponent(Graphics g){
	
	int totalNumNodes = graph.numNodes(); 
	for(int i = 0; i < totalNumNodes; i++){
	    Graph<NodeDisplayData, EdgeDisplayData>.Node node = graph.getNode(i); 
	    Integer xCoord = node.getData().getXCoordinate().intValue(); 
	    Integer yCoord = node.getData().getYCoordinate().intValue(); 
	    NodeDisplayData displayNode = node.getData();
	  
	    //draws the node
	    g.setColor(displayNode.getNodeColor()); 
	    g.fillOval(xCoord -5, yCoord -5, 12, 12); 
	    
	    //draws the nodelabel
	    g.setColor(displayNode.getNodeLabelColor());
	    g.drawString(displayNode.getNodeLabel(), xCoord, yCoord); 
	    
	    //draws the distancelabel 
	    g.setColor(displayNode.getDistanceLabelColor()); 
	    g.drawString(displayNode.getDistanceLabel(), xCoord + 5, yCoord + 10); 
	}
	
	
	int totalNumEdges = graph.numEdges(); 
	if(totalNumEdges > 0){
	    for(int i = 0; i < totalNumEdges; i++){ 
		Graph<NodeDisplayData, EdgeDisplayData>.Edge edge = graph.getEdge(i); 
		Graph<NodeDisplayData, EdgeDisplayData>.Node head = edge.getHead(); 
		Graph<NodeDisplayData, EdgeDisplayData>.Node tail = edge.getTail(); 
		
		Integer p1x = head.getData().getXCoordinate().intValue(); 
		Integer p1y = head.getData().getYCoordinate().intValue(); 
		Integer p2x = tail.getData().getXCoordinate().intValue(); 
		Integer p2y = tail.getData().getYCoordinate().intValue(); 
		
		//draws the edge
		EdgeDisplayData displayEdge = edge.getData(); 
		g.setColor(displayEdge.getEdgeColor()); 
		g.drawLine(p1x, p1y, p2x, p2y); 

		//draws the edgelabel 
		g.setColor(displayEdge.getEdgeLabelColor());
		g.drawString(displayEdge.getEdgeLabel(), (p1x + p2x)/2, (p1y + p2y)/2);

		//draws the edge weight 
		g.setColor(displayEdge.getEdgeWeightColor());
		String edgeWeightInfo = String.valueOf(displayEdge.getEdgeWeight());
		g.drawString(edgeWeightInfo, (p1x + p2x)/2 + 5, (p1y + p2y)/2 + 10);
	    }
	}
    }
    
    /**
     *  The component will look bad if it is sized smaller than this
     *
     *  @returns The minimum dimension
     */
    public Dimension getMinimumSize() {
        return new Dimension(850,3000);
    }
    
    /**
     *  The component will look best at this size
     *
     *  @returns The preferred dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(850,500);
    }
}
