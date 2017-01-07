import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;        
import java.io.*;


/**
 *  Implements a GUI for inputting nodes and edges. 
 *
 *  @author  Ji Won Chung
 *  @version CSC 212 December 15, 2016
 */
public class GraphGUI {
    /** The graph to be displayed */
    private GraphCanvas canvas;

    /** The graph to draw */
    Graph<NodeDisplayData, EdgeDisplayData> graph = new Graph<NodeDisplayData,EdgeDisplayData>(); 

    /** Label for the input mode instructions */
    private JLabel instr;

    /** The input mode */
    InputMode mode = InputMode.ADD_POINTS;
    
    /** Remembers point where last mousedown event occurred */
    Graph<NodeDisplayData, EdgeDisplayData>.Node pointUnderMouse;
    
    /** Remembers the first point clicked for edge */
    Graph<NodeDisplayData, EdgeDisplayData>.Node edgeStart; 
    
    /** Remembers the second point clicked for edge */
    Graph<NodeDisplayData, EdgeDisplayData>.Node edgeEnd; 
    
    /** Remembers the textField that the user uses to input info*/
    JTextField textField; 
    
    /**
     *  Schedules a job for the event-dispatching thread
     *  creating and showing this application's GUI.
     */
    public static void main(String[] args) {
        final GraphGUI GUI = new GraphGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GUI.createAndShowGUI();
                }
            });
	
    }
    
    /** Sets up the GUI window */
    public void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
	
        // Create and set up the window.
        JFrame frame = new JFrame("Graph GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
        // Add components
        createComponents(frame);
	
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /** Puts content in the GUI window */
    public void createComponents(JFrame frame) {
        // graph display
        Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        canvas = new GraphCanvas(graph);
        PointMouseListener pml = new PointMouseListener();
        canvas.addMouseListener(pml);
        canvas.addMouseMotionListener(pml);
        panel1.add(canvas);
        instr = new JLabel("Click to add new points; drag to move.");
        panel1.add(instr,BorderLayout.NORTH);
        pane.add(panel1);
	
	
	JPanel panel3 = new JPanel(); 
	panel3.setLayout(new GridLayout(3, 2));
	
	//A textfield for user input
	JPanel panel2 = new JPanel();
	panel2.setLayout(new GridLayout(2, 1));
	JLabel textBoxName = new JLabel("User Input Text Box (No Spacings Allowed)");
	JTextField textfield = new JTextField(1);
	this.textField = textfield;
	panel2.add(textBoxName);
	panel2.add(textfield); 
	
	
	
	//add remove node
	//buttons to control edges and nodes
	JPanel panel4 = new JPanel();
	panel4.setLayout(new GridLayout(5, 1));
	JLabel addElementsName = new JLabel("Add Nodes and Edges");
	
	JButton addPointButton = new JButton("Add/Move Nodes");
	addPointButton.addActionListener(new AddPointListener());
	
	JButton rmvPointButton = new JButton("Remove Nodes");  
	rmvPointButton.addActionListener(new RmvPointListener());

	JButton addEdgeButton = new JButton("Add Edges"); 
	addEdgeButton.addActionListener(new AddEdgeListener()); 
	
	JButton rmvEdgeButton = new JButton("Remove Edges"); 
	rmvEdgeButton.addActionListener(new RmvEdgeListener());
	
	
	panel4.add(addElementsName);
	panel4.add(addPointButton);
	panel4.add(rmvPointButton);
	panel4.add(addEdgeButton); 
	panel4.add(rmvEdgeButton); 
	
	
	
	//buttons for traversal and dijkstra
	JPanel panel5 = new JPanel();
	panel5.setLayout(new GridLayout(7, 1));
	
	JLabel section5Name = new JLabel("Implement Traversals and Dijkstra");
	
	JButton bftButton = new JButton("BFT Traversal"); 
	bftButton.addActionListener(new BFTListener()); 
	
	JButton dftButton = new JButton("DFT Traversal"); 
	dftButton.addActionListener(new DFTListener()); 
 	
 	JButton dijkstraButton = new JButton("Dijkstra Algorithm"); 
	dijkstraButton.addActionListener(new DijkstraListener()); 
	
	JButton endDijkstraButton = new JButton("Remove Dijkstra");
	endDijkstraButton.addActionListener(new EndDijkstraListener());
	
	JButton endTraversalButton = new JButton("End Traversal"); 
	endTraversalButton.addActionListener(new EndTraversalListener()); 
	
	panel5.add(section5Name);
	panel5.add(bftButton);
	panel5.add(bftButton); 
	panel5.add(dftButton); 
	panel5.add(endTraversalButton); 
	panel5.add(dijkstraButton); 
	panel5.add(endDijkstraButton);
	
	
	
	//Buttons to import and export the graph 
	JPanel panel6 = new JPanel();
	panel6.setLayout(new GridLayout(6, 1));

	//Buttons to change features of nodes and edges 
	JLabel section6Name = new JLabel("Set Node and Edge Features");
	
	JButton setNodeLabelButton = new JButton("Set Node Label"); 
	setNodeLabelButton.addActionListener(new SetNodeLabelListener()); 
	
	JButton removeNodeLabelButton = new JButton("Remove Node Label"); 
	removeNodeLabelButton.addActionListener(new RemoveNodeLabelListener()); 
	
	JButton setEdgeLabelButton = new JButton("Set Edge Label");
	setEdgeLabelButton.addActionListener(new SetEdgeLabelListener());
	
	JButton rmvEdgeLabelButton = new JButton("Remove Edge Label");
	rmvEdgeLabelButton.addActionListener(new RemoveEdgeLabelListener());
	
	JButton setEdgeWeightButton = new JButton("Set Edge Weight");
	setEdgeWeightButton.addActionListener(new SetEdgeWeightListener());
	

	panel6.add(section6Name);
	panel6.add(setNodeLabelButton); 
	panel6.add(removeNodeLabelButton); 
	panel6.add(setEdgeLabelButton);
	panel6.add(rmvEdgeLabelButton);
	panel6.add(setEdgeWeightButton);
	
	
	JPanel panel7 = new JPanel();
	panel7.setLayout(new GridLayout(6, 1));
	
	JLabel section7Name = new JLabel("Import and Export Graph");
	JButton makeGraphButton = new JButton("Import the Graph");
	makeGraphButton.addActionListener(new MakeGraphListener()); 
	 
	JButton saveGraphButton = new JButton("Save the Graph"); 
	saveGraphButton.addActionListener(new SaveGraphListener()); 
	
	panel7.add(section7Name);
	panel7.add(makeGraphButton);
	panel7.add(saveGraphButton);
	
	
	
	panel3.add(panel4);
	panel3.add(panel6);
	panel3.add(panel5);
	panel3.add(panel7);
	panel3.add(panel2);

	pane.add(panel3);

    }
    
    /** 
     * Returns a point found within the drawing radius of the given location, 
     * or null if none
     *
     *  @param x  the x coordinate of the location
     *  @param y  the y coordinate of the location
     *  @return  A node from the canvas if there is one covering this location, 
     *  or a null reference if not
     */
    public Graph<NodeDisplayData, EdgeDisplayData>.Node findNearbyNode(int x, int y) {
	Graph<NodeDisplayData, EdgeDisplayData>.Node returnPoint = null; 
	int totalNumNodes = graph.numNodes(); 
	for( int i = 0; i < totalNumNodes; i++){
	    Graph<NodeDisplayData, EdgeDisplayData>.Node node = graph.getNode(i); 
	    Point coordinate = node.getData().getCoordinate(); 
	    if(coordinate.distance(x,y) < 10){
		returnPoint = node; 
	    }
	}
	return returnPoint; 
    }
    
    /** Constants for recording the input mode */
    enum InputMode {
        ADD_POINTS, RMV_POINTS, ADD_EDGES, RMV_EDGES, BFT_TRAVERSAL, DFT_TRAVERSAL, END_TRAVERSAL, DIJKSTRA, END_DIJKSTRA, 
	    SET_NODE_LABEL, RMV_NODE_LABEL, SET_EDGE_LABEL, RMV_EDGE_LABEL, SET_EDGE_WEIGHT
	    }
    
    /** Listener for AddPoint button */
    private class AddPointListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_POINTS;
            instr.setText("Click to add new points or change their location.");
        }
    }
    
    /** Listener for RmvPoint button */
    private class RmvPointListener implements ActionListener {
        /** Event handler for RmvPoint button */
        public void actionPerformed(ActionEvent e) {
	    mode = InputMode.RMV_POINTS;
	    instr.setText("Click to remove new points or change their location."); 
        }
    }
    
    /** Listener for AddEdge button */
    private class AddEdgeListener implements ActionListener {
	/** Event handler for AddEdge button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.ADD_EDGES; 
	    instr.setText("Click on one point, drag to a different point and release the mouse to add an edge between them."); 
	}
	
    }
    
    /** Listener for RmvEdge button */
    private class RmvEdgeListener implements ActionListener{
	/** Event handler for RemoveEdge button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.RMV_EDGES; 
	    instr.setText("Click on one point and release the mouse at another point to remove the edge between them."); 
	}
    }
    
    /** Listener for BFT button */
    private class BFTListener implements ActionListener{
	/** Event handler for BFT button */
	public void actionPerformed(ActionEvent e){
	    instr.setText("Displaying Breadth First Traversal. Click a point you want the traversal to begin."); 
	    mode = InputMode.BFT_TRAVERSAL;
	}
    }
    
    /** Listener for DFT button */
    private class DFTListener implements ActionListener{
	/** Event handler for DFT button*/
	public void actionPerformed(ActionEvent e){
	    instr.setText("Displaying Depth First Traversal. Click a point you want the traversal to begin."); 
	    mode = InputMode.DFT_TRAVERSAL;
	}
    }
    
    /** Listener for Dijkstra button */
    private class DijkstraListener implements ActionListener{
	/** Event handler for Dijkstra button */
	public void actionPerformed(ActionEvent e){
	    instr.setText("Displaying Dijkstra's Algorithm. Click a point you want the traversal to begin."); 
	    mode = InputMode.DIJKSTRA;
	}
    }
    
    /** Listener for EndDijkstra button */
    private class EndDijkstraListener implements ActionListener{
	/** Event handler for Dijkstra button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.END_DIJKSTRA;
	    instr.setText("Removing info from Dijkstra's Shortest Path Algorithm."); 
	    int totalNumNodes = graph.numNodes(); 
	    
	    for(int i = 0; i < totalNumNodes; i++){
		graph.getNode(i).getData().setNodeColor(Color.RED); 
		graph.getNode(i).getData().setDistanceLabel("");
	    }
	    canvas.repaint();
	}
    }
    
    
    /** Listener for End Traversal button */
    private class EndTraversalListener implements ActionListener{
	
	/**Event handler for End Traversal button */
	public void actionPerformed(ActionEvent e){
	    instr.setText("Gets rid of graphical display of BFT or DFT."); 
	    
	    int totalNumNodes = graph.numNodes(); 
	    int totalNumEdges = graph.numEdges(); 
	    
	    for(int i = 0; i < totalNumNodes; i++){
		graph.getNode(i).getData().setNodeColor(Color.RED); 
	    }
	    
	    for(int i = 0; i < totalNumEdges; i++){
		graph.getEdge(i).getData().setEdgeColor(Color.BLACK); 
	    }
	    
	    canvas.repaint();
	    
	}
    }
    
    /** Listener for MakeGraph Button */
    private class MakeGraphListener implements ActionListener{
	
   	/** 
	 *  Method to read a file and convert it to a graph in the correct format.
	 *  
	 *  @param sc The scanner we are retrieving the textfile information from 
	 */
	private void importGraph(Scanner sc){
	    if(sc.hasNextLine()){
    		String[] graphInfo; 
    		HashMap<Integer, Graph<NodeDisplayData, EdgeDisplayData>.Node> nodeTable = new HashMap<Integer, Graph<NodeDisplayData, EdgeDisplayData>.Node>();
    		String infoLine = sc.nextLine();
    		graphInfo = infoLine.split("\\s+");
		int numNodes = Integer.parseInt(graphInfo[1]);
    		int numEdges = Integer.parseInt(graphInfo[2]);
    		ArrayList<String> nodesInfo = new ArrayList<String>();
    		ArrayList<String> edgesInfo = new ArrayList<String>();
		
		while(sc.hasNextLine()){
		    String graphLine = sc.nextLine();
		    String[] graphInfoString = graphLine.split("\\s+");
		    String nodeEdgeIdentifier = graphInfoString[0];
		    if(nodeEdgeIdentifier.equals("n")){ //sort all the info on nodes together 
			nodesInfo.add(graphLine);
		    }else if(nodeEdgeIdentifier.equals("e")){ //sort all the info on the edges together
			edgesInfo.add(graphLine);
		    }else{
			System.err.println("A designation of node and edge in your file is wrong.");
		    }
    		}
				
    		for(String nodeInfo : nodesInfo){
		    String[] nodeString = nodeInfo.split("\\s+");
		    NodeDisplayData nodeData = new NodeDisplayData();
		    
		    //Raw information from the line
		    String nodeIdentifierInfo = nodeString[1];
		    String nodeLabel = nodeString[2];
		    String nodeXCoordInfo = nodeString[3];
		    String nodeYCoordInfo = nodeString[4];
		    
		    //Coordinate information 
		    Integer nodeXCoord = Integer.parseInt(nodeXCoordInfo);
		    Integer nodeYCoord = Integer.parseInt(nodeYCoordInfo);
		    
		    //Set the node info 
		    nodeData.setNodeLabel(nodeLabel);
		    nodeData.setXCoord(nodeXCoord); 
		    nodeData.setYCoord(nodeYCoord); 
		    
		    Graph<NodeDisplayData, EdgeDisplayData>.Node addedNode = graph.addNode(nodeData);
		    
		    //Identifier of the node 
		    int nodeIdentifier = Integer.parseInt(nodeIdentifierInfo); 
		    nodeTable.put(nodeIdentifier, addedNode);
    		}
		
    		for(String edgeInfo : edgesInfo){
		    String[] edgeString = edgeInfo.split("\\s+");
		    EdgeDisplayData edgeData = new EdgeDisplayData();
		    
		    //Raw information from the line 
		    String headIdentifierInfo = edgeString[1];
		    String tailIdentifierInfo = edgeString[2];
		    String edgeLabelInfo = edgeString[3];
		    String edgeWeightInfo = edgeString[4]; 
		    
		    //Conversion of types 
		    double edgeWeight = Double.parseDouble(edgeWeightInfo);
		    int headIdentifier = Integer.parseInt(headIdentifierInfo);
		    int tailIdentifier = Integer.parseInt(tailIdentifierInfo);
		    
		    //Retrieve the head, tail of the edge 
		    Graph<NodeDisplayData, EdgeDisplayData>.Node head = nodeTable.get(headIdentifier);
		    Graph<NodeDisplayData, EdgeDisplayData>.Node tail = nodeTable.get(tailIdentifier);
		    
		    //Set the edge info
		    edgeData.setEdgeLabel(edgeLabelInfo);
		    edgeData.setEdgeWeight(edgeWeight);
		    graph.addEdge(edgeData, head, tail); 
		    
		    //Throws an error if we don't have all the correct info as the file specified 
		    if(nodesInfo.size() != numNodes){
			System.err.println("The number of nodes you specified do not match the number of nodes you have.");
		    }if(edgesInfo.size() != numEdges){
			System.err.println("The number of edges you specified do not match the number of edges you have.");
		    }
		}
		canvas.repaint(); //repaint 
	    }
	}
	
	/** Event handler for makeGraph button */
	public void actionPerformed(ActionEvent e){
	    System.out.println("Importing file with graph..."); 
	    String fileName = textField.getText(); 
	    
	    try{
	    	BufferedReader input = new BufferedReader(new FileReader(fileName));
	    	Scanner sc = new Scanner(input);
	    	importGraph(sc);
	    	instr.setText("Imported " + fileName);
		System.out.println("File is imported."); 
	    } catch(FileNotFoundException fileException){ 
		System.err.println("Problem reading file..." + fileName); 
		System.exit(-1);
	    } 
	}
    }
    
    /** Listener for SaveGraph Button */
    private class SaveGraphListener implements ActionListener{
	/**
	 *  Function that writes the graph onto a user-specified file.
	 * 
	 *  @param output The PrintWriter that we use to write the graph info.
	 **/
	private void exportGraph(PrintWriter output){
	    HashMap<Graph<NodeDisplayData, EdgeDisplayData>.Node, Integer> nodeTable = new HashMap<Graph<NodeDisplayData, EdgeDisplayData>.Node, Integer>();
	    int totalNumNodes = graph.numNodes();
	    int totalNumEdges = graph.numEdges(); 
	    String firstLine = "p "+ totalNumNodes+ " "+ totalNumEdges;
	    output.println(firstLine); 
	    for(int i = 0; i < totalNumNodes; i++){
		Graph<NodeDisplayData, EdgeDisplayData>.Node node = graph.getNode(i);
		NodeDisplayData nodeData = node.getData();
		Double xCoord = nodeData.getXCoordinate();
		Double yCoord = nodeData.getYCoordinate();
    		String nodeLine = "n "+ i+ " "+ nodeData.getNodeLabel() + " " + xCoord.intValue() + " " + yCoord.intValue();
	    	output.println(nodeLine);
	    	nodeTable.put(node, i);
	    }
	    for(int i = 0; i< totalNumEdges; i++){
	    	Graph<NodeDisplayData, EdgeDisplayData>.Edge edge = graph.getEdge(i);
	    	EdgeDisplayData edgeData = edge.getData();
		Graph<NodeDisplayData, EdgeDisplayData>.Node head = edge.getHead(); 
		Graph<NodeDisplayData, EdgeDisplayData>.Node tail = edge.getTail();
			
		int headIndex = nodeTable.get(head);
		int tailIndex = nodeTable.get(tail);

		double edgeWeight = edgeData.getEdgeWeight();
    		String edgeLine = "e "+ headIndex + " "+ tailIndex + " " + edgeData.getEdgeLabel() + " " + edgeWeight;
    		output.println(edgeLine); 
	    }
	}
	
	/** Event handler for saveGraph button */
	public void actionPerformed(ActionEvent e){
	    System.out.println("Saving graph in a file...");
	    String filename = textField.getText();
	    
	    try{ 
		PrintWriter output = new PrintWriter(new FileWriter(filename)); 
		exportGraph(output);
		output.close(); 
		System.out.println("Graph is saved and written in "+ filename); 
		instr.setText("Saved the Graph to: " + filename);
	    } catch(IOException exception){ 
		System.err.println("Problem reading file " + filename); 
		System.exit(-1);
	    } 
	}
    }
    
    /** Listener for SetNodeLabel Button */
    private class SetNodeLabelListener implements ActionListener{
	/** Event handler for SetNodeLabel button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.SET_NODE_LABEL;
	    instr.setText("Write the new name in the text box and click on a node to set its name.");
	}
    }
    
    /** Listener for RemoveNodeLabel Button */
    private class RemoveNodeLabelListener implements ActionListener{
	/** Event handler for RemoveNodeLabel button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.RMV_NODE_LABEL; 
	    instr.setText("Click on a node to remove its name and set to default.");
	}
    }
    
    /** Listener for SetNodeLabel Button */
    private class SetEdgeLabelListener implements ActionListener{
	/** Event handler for SetNodeLabel button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.SET_EDGE_LABEL;
	    instr.setText("Write the new name in the text box and select an edge to set its name. Click on one point, drag, and release to select an edge.");
	}
    }
    
    /** Listener for RemoveNodeLabel Button */
    private class RemoveEdgeLabelListener implements ActionListener{
	/** Event handler for RemoveNodeLabel button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.RMV_EDGE_LABEL; 
	    instr.setText("Select an edge to remove its name and set to default. Click on one point, drag, and release to select an edge.");
	}
    }
    
    /** Listener for SetEdgeWeight Button */
    private class SetEdgeWeightListener implements ActionListener{
	/** Event handler for SetEdgeLabel button */
	public void actionPerformed(ActionEvent e){
	    mode = InputMode.SET_EDGE_WEIGHT;
	    instr.setText("Write the new weight in the text box and select an edge to set its weight. Click on one point, drag, and release to select an edge.");

	}
    }
    

    /** Mouse listener for PointCanvas element */
    private class PointMouseListener extends MouseAdapter
        implements MouseMotionListener {
	
        /** Responds to click event depending on mode */
        public void mouseClicked(MouseEvent e) {
            switch (mode) {
		
            case ADD_POINTS:
		Graph<NodeDisplayData, EdgeDisplayData>.Node existingNode = findNearbyNode(e.getX(), e.getY()); 
		Point existingPoint = null; 
		if(existingNode != null){
		    existingPoint = existingNode.getData().getCoordinate(); 
		}if(((existingPoint != null) && (existingPoint.distance(e.getX(), e.getY()) > 5)) || (existingPoint == null)){
		    Point newPoint = new Point(e.getX(), e.getY()); 
		    NodeDisplayData newData = new NodeDisplayData(newPoint); 
		    graph.addNode(newData);
		}else{// Otherwise, emit a beep, as shown below:
		    Toolkit.getDefaultToolkit().beep();
		    System.err.println("Please select a valid node."); 
		}
		break;

            case RMV_POINTS:
		Graph<NodeDisplayData, EdgeDisplayData>.Node removalNode = findNearbyNode(e.getX(), e.getY()); 
		if(removalNode != null){
		    Point removalPoint = removalNode.getData().getCoordinate(); 
		    if(((removalPoint != null) && (removalPoint.distance(e.getX(), e.getY() )< 5 ))){
			graph.removeNode(removalNode);
		    }else{
			Toolkit.getDefaultToolkit().beep(); 
			System.err.println("Please select a valid node."); 
		    }
		}
		break;
		
	    case BFT_TRAVERSAL:
		Graph<NodeDisplayData, EdgeDisplayData>.Node startBFT = findNearbyNode(e.getX(), e.getY()); 
		if(startBFT != null){
		    startBFT.getData().setNodeColor(Color.ORANGE);
		    HashSet<Graph<NodeDisplayData, EdgeDisplayData>.Edge> traversedEdges = graph.BFT(startBFT); 
		    for(Graph<NodeDisplayData, EdgeDisplayData>.Edge edge : traversedEdges){
			edge.getData().setEdgeColor(Color.ORANGE); 
		    }
		}else{
		    Toolkit.getDefaultToolkit().beep();
		    System.err.println("Please select a valid node."); 
		}
		break; 

	    case DFT_TRAVERSAL:
		Graph<NodeDisplayData, EdgeDisplayData>.Node startDFT = findNearbyNode(e.getX(), e.getY()); 
		if(startDFT != null){
		    startDFT.getData().setNodeColor(Color.YELLOW); 
		    HashSet<Graph<NodeDisplayData, EdgeDisplayData>.Edge> traversedDFT = graph.BFT(startDFT); 
		    for(Graph<NodeDisplayData, EdgeDisplayData>.Edge edge : traversedDFT){
			edge.getData().setEdgeColor(Color.YELLOW); 
		    }
		}else{
		    Toolkit.getDefaultToolkit().beep();
		    System.err.println("Please select a valid node.");
		}
		break;
		
	    case DIJKSTRA: 
		Graph<NodeDisplayData, EdgeDisplayData>.Node startDijkstra = findNearbyNode(e.getX(), e.getY());
		if(startDijkstra != null){ 
		    startDijkstra.getData().setNodeColor(Color.GREEN); 
		    double[] dijkstraArray = Graph.distances(graph, startDijkstra); 
		    for(int i = 0; i < dijkstraArray.length; i++){
			graph.getNode(i).getData().setDistanceLabel(String.valueOf(dijkstraArray[i])); 
		    }
		}else{
		    Toolkit.getDefaultToolkit().beep();
		    System.err.println("Please select a valid node."); 
		}
		break;
		
	    case SET_NODE_LABEL:
		Graph<NodeDisplayData, EdgeDisplayData>.Node snLabelNode = findNearbyNode(e.getX(), e.getY()); 
		if(snLabelNode != null){
		    String newNodeLabel = textField.getText();
		    snLabelNode.getData().setNodeLabel(newNodeLabel); 
		}else{
		    Toolkit.getDefaultToolkit().beep(); 
		    System.err.println("Please select a valid node."); 
		}
		break;
	    
	    case RMV_NODE_LABEL:
		Graph<NodeDisplayData, EdgeDisplayData>.Node rmvLabelNode = findNearbyNode(e.getX(), e.getY()); 
		if(rmvLabelNode != null){
		    String emptyNodeLabel = "DefaultNodeLabel"; 
		    rmvLabelNode.getData().setNodeLabel(emptyNodeLabel);
		}else{
		    Toolkit.getDefaultToolkit().beep(); 
		    System.err.println("Please select a valid node."); 
		}
		break; 
		
	    }
	    canvas.repaint();
	    
        }
	
        /** Records point under mousedown event in anticipation of possible drag */
        public void mousePressed(MouseEvent e) {
            //Record point under mouse, if any 
            Graph<NodeDisplayData, EdgeDisplayData>.Node nearPoint = findNearbyNode(e.getX(), e.getY()); 
            pointUnderMouse = nearPoint;  
            switch(mode){
		
            case ADD_EDGES:
		edgeStart = pointUnderMouse; 
	    }
	}
	
        /** Responds to mouseup event */
        public void mouseReleased(MouseEvent e) {
	    //Clear record of point under mouse, if any
	    switch(mode){
		
	    case ADD_POINTS:
		pointUnderMouse = null; 
		break; 
	   
	    case RMV_POINTS:
		pointUnderMouse = null; 
		break; 
		
	    case ADD_EDGES:
		edgeEnd = findNearbyNode(e.getX(), e.getY()); 
		if((edgeEnd == null) || (edgeStart == null)||(edgeEnd == edgeStart)){ 
		    System.err.println("You don't have 2 valid nodes to create the edge."); 
		}else{
		    graph.addEdge(new EdgeDisplayData(), edgeStart, edgeEnd);  
		}
		pointUnderMouse = null;
		break; 
		
	    case RMV_EDGES:
		edgeEnd = findNearbyNode(e.getX(), e.getY());
		pointUnderMouse = null; 
		if((edgeEnd == null) || (edgeStart == null) || (edgeEnd == edgeStart)){
		    System.err.println("You don have not selected 2 valid nodes to select an edge."); 
		} else{
		    graph.removeEdge(edgeStart, edgeEnd); 
		}
		break;

	    case SET_EDGE_LABEL:
		edgeEnd = findNearbyNode(e.getX(), e.getY()); 
		pointUnderMouse = null;
		if((edgeEnd == null) || (edgeStart == null)||(edgeEnd == edgeStart)){  
		    System.err.println("You have not selected 2 valid nodes to select an edge."); 
		}else{
		    String newEdgeLabel = textField.getText();
		    graph.getEdgeRef(edgeStart, edgeEnd).getData().setEdgeLabel(newEdgeLabel);
		}
		break;
		
	    case RMV_EDGE_LABEL:
		edgeEnd = findNearbyNode(e.getX(), e.getY()); 
		pointUnderMouse = null;
		if((edgeEnd == null) || (edgeStart == null)||(edgeEnd == edgeStart)){  
		    System.err.println("You have not selected 2 valid nodes to select an edge."); 
		}else{
		    graph.getEdgeRef(edgeStart, edgeEnd).getData().setEdgeLabel("DefaultEdgeLabel");
		}
		break;
		    
	    case SET_EDGE_WEIGHT: 
		edgeEnd = findNearbyNode(e.getX(), e.getY()); 
		pointUnderMouse = null;
		if((edgeEnd == null) || (edgeStart == null)||(edgeEnd == edgeStart)){  
		    System.err.println("You have not selected 2 valid nodes to select an edge."); 
		}else{
		    String newEdgeWeightInfo = textField.getText();
		   
		    //catches cases where user input puts a non-number for the weight 
		    try{
			double newEdgeWeight = Double.parseDouble(newEdgeWeightInfo);
			graph.getEdgeRef(edgeStart, edgeEnd).getData().setEdgeWeight(newEdgeWeight);
		    } catch(NumberFormatException typeException){
			System.err.println("Your edge weight is not a number. Try again.");
		    }
		}
		break; 
		
	    }
	    canvas.repaint(); 
        }

        /** Responds to mouse drag event */
        public void mouseDragged(MouseEvent e) {
	    //If mode allows point motion, and there is a point under the mouse
	    switch(mode){

	    case ADD_POINTS:
		if(pointUnderMouse != null){
		    //change its coordinates to the current mouse coordinates 
		    Point newPoint = new Point(e.getX(), e.getY()); 
		    pointUnderMouse.getData().setCoordinate(newPoint); 
		}
		break;
		
	    case ADD_EDGES: 
		edgeStart = pointUnderMouse;
		break;
		
	    case RMV_EDGES:
		edgeStart = pointUnderMouse;
		break; 

	    case SET_EDGE_LABEL:
		edgeStart = pointUnderMouse;
		break; 

	    case RMV_EDGE_LABEL:
		edgeStart = pointUnderMouse;
		break;

	    case SET_EDGE_WEIGHT: 
		edgeStart = pointUnderMouse; 
		break; 
	    }
	    canvas.repaint(); 
        }

	// Empty but necessary to comply with MouseMotionListener interface.
        public void mouseMoved(MouseEvent e) {}
    }
}