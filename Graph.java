import java.util.*; 
import java.lang.Object; 
import java.lang.Number; 
import java.lang.Double;

/** 
 *  An implementation of a Graph.
 * 
 *  @author Ji Won Chung 
 *  @version CSC 212 December 15, 2016
 */
public class Graph<V, E>{
    
    /** The list of vertices */
    private ArrayList<Node> nodes; 
    
    /** The list of edges */
    private ArrayList<Edge> edges; 
    
    /** Constructor that initalizes with empty nodes and edge lists */
    public Graph(){
	this.nodes = new ArrayList<Node>(); 
	this.edges = new ArrayList<Edge>(); 
    }
    
    /** 
     *  Adds an edge. Does not allow for repeat edges. 
     * 
     *  @param data The data of the edge you are adding.
     *  @param head The head of the edge you are adding.
     *  @param tail The tail of the edge you are adding.
     *  @return newEdge The edge that you are adding.  
     */
    public Edge addEdge(E data, Node head, Node tail){
    	Edge newEdge = null;
    	if(head != tail){
	    newEdge = new Edge(data, head, tail); 
	    boolean containsEdge = false; 
	    for(Edge edge : this.edges){
		if(edge.equals(newEdge)){
		    System.out.println("You are adding a repeat edge.");
		    containsEdge = true; 
		}
	    }
	    if(containsEdge == false){
		newEdge.head.addEdgeRef(newEdge); 
		newEdge.tail.addEdgeRef(newEdge); 
		this.edges.add(newEdge); 
	    }
    	}
	
	return newEdge; 
    }
    
    /**
     *  Adds a node. 
     * 
     *  @param data The data of the node you want to add.
     *  @return newNode The node that you are adding.  
     */
    public Node addNode(V data){
	Node newNode = new Node(data);
	this.nodes.add(newNode); 
	return newNode; 
    }
    
    /** Checks the consistency of the graph.*/
    public void check(){
	if(this.numEdges() > 0){ 
	    for(Edge edge : this.edges){
		if(!(edge.head.edgeList.contains(edge) && edge.tail.edgeList.contains(edge))){ //checks if head or tail of an edge links back to that edge 
		    System.err.println("Not all the heads and tail of the edge links back to the edge."); 
		} 
		if(!(this.nodes.contains(edge.head) && this.nodes.contains(edge.tail))){ //checks if every head or tail of an edge is in the central node list
		    if(!this.nodes.contains(edge.head)){
			System.err.println("Edge: "+ edge.data + " does not have its head: "+ edge.head.data ); 
		    }if(!this.nodes.contains(edge.tail)){
			System.err.println("Edge: " + edge.data + " does not have its tail: " + edge.tail.data); 
		    }
		 }
		
		if((edge.tail == null) || (edge.head == null)){ //checks if edge/head/tail links are not null 
		    System.err.println("Some of the edges and tails are null"); 
		}
		
	    }
	    System.out.println("...Otherwise, central edge list looks okay."); 
	}

	//Checks every edge listed for a node has that node as either head or tail 
	if(this.numNodes() > 0){
	    for(Node node : this.nodes){
		if(node.edgeList.size() > 1){
		     for(Edge edge : node.edgeList){
			 if(!(edge.head == node || edge.tail == node)){
			     System.err.println("Every edge listed for a node does not have that node as either the head or tail "); 
			 } 
			 if(!(this.edges.contains(edge))){ //checks every edge refrence by node is in the central edge list 
			     System.err.println(edge.data + " is not in the central edge list of the graph .");
			 }
		     }
		}
	    }
	    System.out.println("...Otherwise, central node list looks okay.");
	}
    } 
    
    /**
     *  Breadth-first traversal of graph.
     *
     *  @param start The node that you want to start the traversal.
     *  @return edgesTraversed The set of edges that the BFT has traversed on. 
     */
    public HashSet<Edge> BFT(Node start){
	HashSet<Edge> edgesTraversed = new HashSet<Edge>(); 
	LinkedList<Node> queue = new LinkedList<Node>(); 
	HashSet<Node> seenNodes = new HashSet<Node>(); 
	
	queue.add(start); 
	seenNodes.add(start); 
	
	while(queue.size()>0){
	     Node node = queue.poll(); 
	     LinkedList<Node> neighbors = node.getNeighbors(); 
	     for(Node neighbor : neighbors){
		 if(!seenNodes.contains(neighbor)){
		     queue.offerLast(neighbor); 
		     Edge traversedEdge = this.getEdgeRef(node, neighbor); 
		     edgesTraversed.add(traversedEdge); 
		     seenNodes.add(neighbor);	
		 }
		 
	     }
	     //System.out.println(node.getData());//prints all the nodes we've traversed over 
	} 
	return edgesTraversed;
    }
    
    /**
     *  Calls the depth-first traversal of graph -- public interface.
     * 
     *  @param start The node in the graph we want to start at.
     *  @return Set of edges that we have traversed over. 
     */
    public HashSet<Edge> DFT(Node start){
	HashSet<Node> seenNodes = new HashSet<Node>();
	HashSet<Edge> edgesTraversed = new HashSet<Edge>(); 
	return DFT(start, seenNodes, edgesTraversed); 
    }
    
    /**
     *  Depth-first traversal of graph. Recursive style.
     *
     *  @param start The node in the graph we want to start at.
     *  @param seenNodes The set of nodes that we have already seen 
     *  @param edgesTraversed The set of edges we have already been on.
     *  @return edgesTraversed The set of edges we have already been on.  
     */
    private HashSet<Edge> DFT(Node start, HashSet<Node> seenNodes, HashSet<Edge> edgesTraversed){
	
	if(seenNodes.contains(start)){
	    //do nothing 
	}else{
	    seenNodes.add(start); 
		 LinkedList<Node> neighbors = start.getNeighbors(); 
	     for(Node neighbor : neighbors){
		 if(!seenNodes.contains(neighbor)){
		     Edge traversedEdge = this.getEdgeRef(start, neighbor); 
		     edgesTraversed.add(traversedEdge); 
		 }		 
		 edgesTraversed = DFT(neighbor, seenNodes, edgesTraversed); 
	     }
	 }
	return edgesTraversed; 	 
    }
    
    /** 
     *  Dijkstra's shortest-path algorithm to compute distances to nodes. 
     * 
     *  @param start The node where the grpah starts.
     *  @param dijkstraGraph The graph who we want to conduct the algorithm on. 
     *  @return distanceArray An array whose each index corresponds to the index of the central node list's index and contains information about the calculate shortest distance from this node to that node. 
     */
    public static <S, T extends Number> double[] distances(Graph<S, T> dijkstraGraph, Graph<S, T>.Node start){
	HashSet<Graph<S, T>.Node> unvisitedNodes = new HashSet<Graph<S,T>.Node>(); 
	HashSet<Graph<S, T>.Node> visitedNodes = new HashSet<Graph<S, T>.Node>(); 
	HashMap<Graph<S, T>.Node, Double> costTable = new HashMap<Graph<S, T>.Node, Double>(); 
	
	int numNodes = dijkstraGraph.numNodes();
	double[] distanceArray = new double[numNodes];
	
	for(int i = 0; i < numNodes; i++){
	    Graph<S, T>.Node node = dijkstraGraph.getNode(i);
	    costTable.put(node, Double.POSITIVE_INFINITY);
	}
	
	costTable.put(start, 0.0);
	unvisitedNodes.add(start); 
	
	while(unvisitedNodes.size() > 0){
	    Graph<S, T>.Node smallestNode = null; 
	    for(Graph<S, T>.Node node : unvisitedNodes){
		if(smallestNode == null){
		    smallestNode = node; 
		}if(costTable.get(node) < costTable.get(smallestNode)){
		    smallestNode = node; 
		} 
	    }
	    
	    unvisitedNodes.remove(smallestNode); 
	    visitedNodes.add(smallestNode); 
	    LinkedList<Graph<S, T>.Node> neighbors = smallestNode.getNeighbors(); 
	    for(Graph<S, T>.Node neighbor : neighbors){
		if(!visitedNodes.contains(neighbor)){
		    unvisitedNodes.add(neighbor); 
		}			     
		Graph<S, T>.Edge edgeBetween = dijkstraGraph.getEdgeRef(smallestNode, neighbor);
		
		double edgeCost = edgeBetween.getData().doubleValue();
		
		double newCost = costTable.get(smallestNode) + edgeCost; 
		
		if(newCost < costTable.get(neighbor)){
		    // System.out.println("neighbor..."+neighbor + " newCost...."+newCost); 
		    costTable.put(neighbor, newCost); 
		} 
	    }
	}
	for(int i=0; i <distanceArray.length; i++){
	    Double totalCost =costTable.get(dijkstraGraph.getNode(i)); 
	    distanceArray[i] = totalCost; 
	}
	
	return distanceArray;
    }
    
    /** 
     *  Returns nodes that are endpoints of a list of edges.
     * 
     *  @param edges The list of edges whose endpoint information you want. 
     *  @return nodes The hashset of the endpoints of each edge. 
     */
    public HashSet<Node> endpoints(HashSet<Edge> edges){
	HashSet<Node> nodes = new HashSet<Node>();
	if(edges.size() > 1){
	    for(Edge edge : edges){
		nodes.add(edge.head); 
		nodes.add(edge.tail); 
	    }
	}
	
	return nodes; 
    }
    
    
    
    /** 
     *  Accessor for edges.
     * 
     *  @param i The index of the edge that you want to retrieve.
     *  @return The edge we have indexed
     */
    public Edge getEdge(int i ){
	return this.edges.get(i); 
    }
    
    /** 
     *  Accessor for specific edge. 
     * 
     *  @param head The head of the edge you want to find. 
     *  @param tail The tail of the edge you want to find. 
     *  @return foundEdge The edge that you want to find. 
     */
    public Edge getEdgeRef(Node head, Node tail){
	Edge foundEdge = null; 
	for(Edge edge : this.edges){
	    if(edge.equals(head, tail)){
		foundEdge = edge;
	    }
	} 
	return foundEdge; 
    } 
    
    /** 
     *  Accessor for nodes.
     * 
     *  @param i The index of the node you want to access.
     *  @return The node we have indexed
     */
    public Node getNode(int i){ 
	return this.nodes.get(i); 
    } 

    /** 
     *  Accessor for number of edges.
     *  
     *  @return The number of edges of the graph.
     */
    public int numEdges(){
	return this.edges.size();
    }
    
    /**
     *  Accessor for number of nodes. 
     * 
     *  @return The number of nodes of the graph.
     */
    public int numNodes(){
	return this.nodes.size(); 
    }
    
    /** 
     *  Returns nodes not on a given list.
     * 
     *  @param group The group of nodes that we are given.  
     *  @return notPresent The group of nodes that are not in our given list. 
     */
    public HashSet<Node> otherNodes(HashSet<Node> group){
	HashSet<Node> notPresent = new HashSet<Node>(); 
	for(Node node : this.nodes){
	    if(!group.contains(node)){
		notPresent.add(node); 
	    }
	}
	return notPresent; 
    }
    
   
    /** Prints a representation of the graph.*/
    public void print(){
	System.out.println();
	if(this.numNodes() > 0){
	    for(Node node: this.nodes){
		System.out.println("The node..."+node.data + " has these edges: "); 
		for(Edge edge : node.edgeList){
		    System.out.println("     Edge: "+edge.data); 
		}
	    }
	    System.out.println(); 
	}if(this.numEdges() > 0){
	    for(Edge edge : this.edges){
		System.out.println("The edge..." + edge.data + " has this info: ");
		System.out.println("     The head of this edge: "+edge.head.data); 
		System.out.println("     The tail of this edge: "+ edge.tail.data); 
	    }
	    System.out.println();
	}if(this.numEdges() == 0){
	    System.out.println("The central edge list is empty."); 
	}if(this.numEdges() > 0 && this.numNodes() == 0){
	    System.out.println("You have edges in your central edge list, but nodes for them in the central nodes list."); 
	}else if( this.numEdges() == 0 && this.numNodes() ==0){
	    System.out.println("Your graph is empty."); 
	}
    }
    
    /** 
     *  Removes an edge. 
     *  
     *  @param edge The edge you want to remove from the graph. 
     */
    public void removeEdge(Edge edge){
	edge.head.removeEdgeRef(edge); 
	edge.tail.removeEdgeRef(edge); 
	this.edges.remove(edge); //remove edge from graph's central list 
    }
    
    /** 
     *  Removes an edge.
     *  
     *  @param head The head of the edge you want to remove from the graph.
     *  @param tail The head of the edge you want to remove from the graph.
     */
    public void removeEdge(Node head, Node tail){
	Edge edge = getEdgeRef(head, tail); 
	
	head.removeEdgeRef(edge); 
	tail.removeEdgeRef(edge); 
	
	this.edges.remove(edge); //removes edge from graph 
    } 
  
    /**
     *  Removes a node. 
     * 
     *  @param node The node we want to remove 
     */
    public void removeNode(Node node){
	while(node.edgeList.size()>0){
	    int size = node.edgeList.size();
	    this.removeEdge(node.edgeList.get(size-1)); 
	}
	this.nodes.remove(node); 
    }
    
    
    /**************************************Nested Edge Class ***************************************/
    /** 
     *  A nested Edge class.  
     *  An Edge that has a data, a tail, and a head. 
     */ 
    protected class Edge{
	/** The tail of the edge */
	private Node tail; 
	
	/** The head of the edge */
	private Node head; 
	
	/** The data of the edge */
	private E data; 
	
	/**
	 *  Constructor that creates a new edge 
	 * 
	 *  @param data The data inside the edge 
	 *  @param tail The tail of the edge 
	 *  @param head The head of the edge 
	 */
	public Edge(E data, Node head, Node tail){
	    this.data = data; 
	    this.tail = tail; 
	    this.head = head; 
	} 

	/**
	 *  Two edges are equal if they connect the same endpoints regardless of the data they carry. 
	 *
	 *  @param edge2 The other edge you want to compare 
	 *  @return T/F 
	 */
	public boolean equals(Edge edge2){
	    boolean caseOne = (this.getHead() == edge2.getHead()) && (this.getTail() == edge2.getTail());
	    boolean caseTwo = (this.getHead() == edge2.getTail()) && (this.getTail() == edge2.getHead());
	    
	    return (caseOne || caseTwo);
	}

	/**
	 *  Two edges are equal if they connect the same endpoints regardless of the data they carry. 
	 *
	 *  @param node1 One endpoint of an edge
	 *  @param node2 Another endpoint of an edge 
	 *  @return T/F 
	 */
	public boolean equals(Node node1, Node node2){
	    boolean caseOne = (this.getHead() == node1) && (this.getTail() == node2);
	    boolean caseTwo = (this.getHead() == node2) && (this.getTail() == node1);
	
	    return (caseOne || caseTwo);
	}

	/** 
	 *  Accessor for data of the edge.
	 * 
	 *  @return The data of the node 
	 */
	public E getData(){ 
	    return this.data; 
	}

	/** 
	 *  Accessor for the tail of the edge.
	 *
	 *  @return The tail of the edge 
	 */
	public Node getTail() { 
	    return this.tail; 
	} 

	/**
	 *  Accssor for the head of the edge.
	 * 
	 *  @return The head of the edge
	 */ 
	public Node getHead(){
	    return this.head; 
	} 
	
	/** 
	 *  Redefined haschode to match redefined equals 
	 *
	 *  @return The redefined hashcode 
	 */
	public int hashCode(){ 
	    int newHash = this.head.hashCode() * this.tail.hashCode(); 
	    return newHash; 
	} 
	
	
	/** 
	 *  Accessor for the opposite Node.
	 *
	 *  @param node The node whose opposite you want to find.
	 */
	public Node oppositeTo(Node node){
	    if(this.head == node){
		return this.tail; 
	    }else{
		return this.head; 
	    }
	    
	} 
	
	/**
	 *  Manipulator for data of edge.
	 *
	 *  @param data The new data you want to set the edge data to. 
	 */ 
	public void setData(E data){ 
	    this.data = data; 
	} 
	
    } 
    
    /***************************************Nested Node Class***************************************/
    /**
     *  A nested Node Class.
     *  A node that has a data and a list of all the edges that are linked to it. 
     */ 
    protected class Node{
	/** The data contained inside of the node */
	private V data; 
	
	/** The edges that connect the node  */
	private ArrayList<Edge> edgeList; 
	
	/** 
	 *  Constructor of a disconnected Node.
	 * 
	 *  @param data The data for the node 
	 */
	public Node(V data){ 
	    this.data = data; 
	    this.edgeList = new ArrayList<Edge>(); 
	}
	
	/** 
	 *  Adds an edge to the edge list.
	 * 
	 *  @param edge The edge to add to the edge list.
	 */
	protected void addEdgeRef(Edge edge){ 
	    this.edgeList.add(edge); 
	}
	
	/** 
	 *  Returns the edge to a specified node, or null if there is none.
	 * 
	 *  @param neighbor The other node that connects the edge. 
	 *  @return Edge that is inbetween this and the neighbor
	 */
	public Edge edgeTo(Node neighbor){
	    Edge returnEdge = null; 
	    Edge nodeToEdge = getEdgeRef(this, neighbor); 
	    for(Edge edge : this.edgeList){
	    	if((nodeToEdge !=null) && (nodeToEdge.equals(edge))){
		    returnEdge = edge;
	    	}
	    }
	    
	    return returnEdge; 
	}
	
	/** 
	 *  Accessor for data inside the node.
	 *
	 *  @return The data inside the node.
	 */
	public V getData(){
	    return this.data; 
	} 
	
	/** 
	 *  Returns true if there is an edge to the node in the question.
	 * 
	 *  @return T/F 
	 */
	public boolean isNeighbor(Node node){
	    for(Edge edge : this.edgeList){
		if(edge.oppositeTo(this) == node){
		    return true; 
		} 
	    }
	    return false;  
	}
	
	
	/** 
	 *  Returns a list of neighbors.
	 *
	 *  @return neighbors The neighbors of the node.
	 */ 
	public LinkedList<Node> getNeighbors(){
	    LinkedList<Node> returnList = new LinkedList<Node>(); 
	    for(Edge edge : this.edgeList){
	    	Node neighbor = edge.oppositeTo(this);
	    	returnList.add(neighbor);
	    }
	    return returnList; 
	}
	
	
	/** 
	 *  Removes an edge from the edge list. 
	 * 
	 *  @param edge The edge we want to remove.
	 */
     	protected void removeEdgeRef(Edge edge){ 
	    this.edgeList.remove(edge); 
	} 
	
	/**
	 *  Manipulator for data.
	 * 
	 *  @param data The new node data. 
	 */
	public void setData(V data){
	    this.data = data; 
	}
	
    }
} 