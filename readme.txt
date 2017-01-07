CSC 212 Final Project 
Author: Ji Won Chung 
Version: December 15, 2016 
-------------------------------------------------------------------------------
Stage I 
-------------------------------------------------------------------------------
I made some changes from my previous version. I created an undirected graph that allowed for no repeat edges. It also does not allow self-direction. This means that a node cannot have an edge directed to itself. So I changed my addEdgeMethod to reflect that the edge cannot have the head == tail. I also changed my dijkstra’s method. My dijkstra’s method is now a static method that has 2 parameters: a Graph whose edges is a subclass of a Number class and the start node. The reason why I did this was so that the dijkstra’s method could be generic enough so that it would be compatible with my edgeDisplayData. My edgeDisplayData is a subclass of the Number class as well. So I can use doubleValue() to retrieve the edge Weight without having to change my Graph class. 

-------------------------------------------------------------------------------
Stage II
-------------------------------------------------------------------------------
######################### GENERAL STRUCTURE ################################### 
I have 5 major classes: Graph.java, NodeDisplayData.java, EdgeDisplayData.java, GraphGUI.java, GraphCanvas.java. I separated my program into these five structures because it would allow me to implement graphs with various data types. Thus, its applicability and generalizability was high. Separating into these five classes also allowed me to focus on one concept each. So each class makes sense within itself as well.

Graph.java only deals with the graph class. So it does not compromise to the NodeDisplayData and EdgeDisplayData classes so it can be used in a very general sense. So I can create graphs of different generic types. The only thing that is a bit less generic in the Graph.java class is dijkstra’s method. For the static method will only take in a type of graph whose edge extends number. However, I think this is a good compromise, because it seemed like we best saw the use of dijkstra’s method when we had edge weights that were doubles. Because if all the edge weights were just 1.0 in the case that they weren’t numbers it would not be BFT basically. 

My NodeDisplayData has information regarding node display. It has the nodeLabel, distanceLabel, coordPoint as its fields and more fields to set the colors. The nodeLabel is basically the name of the node or the data inside of the node. I called it nodeLabel although it was the data of our node to avoid confusion with Node data which would be an instance of nodedisplaydata. The default of nodeLabel is a string so that it will not cause any problems when exporting the graph to a file. The distanceLabel is used to show the distances calculated to that node when we use dijkstra’s method. The default distance label is 1.0, unless the user sets it. 

My EdgeDisplayData has information regarding edge display. It has edgeLabel and edgeWeight as its fields in addition to the color fields. EdgeDisplayData extends number so that dijkstra’s method can reliably call on doubleValue() on an instance of edgedisplaydata and always get the edge weight. Thus, my doubleValue() in my edgeDisplayData returns the edgeweight. I also have getEdgeWeight() that returns edge weight. It made sense to me to have both for consistency and making my code a bit more intuitive. The edgeLabel is basically the data of the edge. 

GraphCanvas basically paints everything. It paints the node data, edge weight, edge data, nodes, and edges of our graph. 

GraphGUI basically runs the user-interface and shows all the traversals and allows the user to edit, save, and import a graph interactively. I will go into more detail about design choices or particularly choices made in GraphGUI in the next section. 

############################ GRAPH GUI ######################################## 
************** Basic Arrangement ********
Every time you click a button, on the top left corner instructions appear. However, don’t click the import graph button with the textfield as empty or it will crash the program. The buttons are sorted by similar features. This was a design aspect that I chose to make it more user friendly. I also have a textfield in my GUI where the user can type in information to designate names or change info of the graph. I could have use JOptionPane but I did not have enough time to implement it. I also thought it would actually be simpler for the user to have a fixed textfield instead of something that would constantly pop up. I personally like no pop-ups, so that was a personal preference. 

******** RMV/ADD/Move Node **************
Here are some basic guidelines for removing and adding nodes:
1) Select an add/move nodes button.
2) Click on an empty space: create a default node 
   Click on a “non-empty” space: beep occurs 
   Click on a node and drag your mouse: node drags (with edges if any) 
   
You can add nodes continuously under this mode. 
1) Select remove nodes button.
2) Click on an empty space: beep occurs 
   Click on a node: node disappears from the graph 
	
******** RMV/ADD/Move Edge **************	
Here are some basic guidelines for removing and adding edges:
1) Select Remove Edges / Add Edges Button
2) To select an edge you want to add or remove do the following:
	A) Click on a node 
	B) Drag to another node	
	C) Release at the second node who you want to connect the edge to 

There are some extra lines in the console that will give you information on why you may potentially not be able to create an edge. Also, self-direction is not allowed. It does not do anything if you click on the node itself or click on a node and an empty space. It only draws when you select to valid nodes. 
 
********* Setting Labels & Weights ******
Here are some basic guidelines for setting labels: 
1) Press the button for setting or removing the label or weight.  
2) Type in the textfield the newName you want — no spacings should be inputted (as indicated by User Input TextBox (No Spacings Allowed)).
3)Select Node, Edge you want to set the new label to. You can do this continuously until you get out of the mode of the button. 
4) Click on a different button to not get out of the mode. 

***************Traversals ***************
Here are some basic guidelines for the traversal:  
1) Press the BFT Traversal/DFT Traversal 
2) Click on a node that you want to start the traversal on 
3) The graph will now have a colored node that specifies the start node and edges that are colored that show what edges have been traversed on.
4) Click End Traversal to reset the graph or get rid of all colored edges and nodes. 


***************Dijkstra *****************
Here are some basic guidelines for Dijkstra Button: 
1) Press the Dijkstra Algorithm button 
2) Click on a node that you want to calculate the distances from 
3) The graph will now have a colored node that specifies the start node and the nodes will have new numbers of the same color appear next to them that tells you the smallest distances to it. 
4) Click Remove Dijkstra to reset the graph or remove all the colored info. 

*************Dijkstra & Traversal ******* 
You can call Dijkstra & Traversal on other nodes continuously if the DFT Traversal, BFT Traversal, Dijkstra Algorithm button is still called on. I kept this option for the traversal because I wanted the user to be able to carry out traversals on disconnected graphs simultaneously if they wanted to. I also wanted the user to be able to do Dijkstra Algorithm on different nodes for easy comparison. The start nodes that one has clicked will still stay green which will allow the user to keep track of what it calculated the shortest distance of previously as well. This is sort of a compromise in that if you call multiple dijkstra’s than you might not be able to keep track of which one was which because all the nodes will stay that color. However, leaving it this way allows comparison for dijkstra on nodeA and nodeB to be easier I think. 

***************Import/Save **************
Here are some basic guidelines for Importing/Saving a file:  
1) Type in the textfield the textfileName you want — no spacings should be inputted (as indicated by User Input TextBox (No Spacings Allowed)).
2) Click on save graph: save the file under textfileName
   Click on import graph: import the file under textfileName 
	If the textfileName does not exist for import graph that the program will crash. 
You can import multiple graphs as well! The top left corner will tell you if you have successfully imported a graph or saved a file. It also prints out stuff in the console for more information.

########################## FILE FORMAT ######################################## 
My program parses the lines by spacings, so it is important that the spacings are correct. All names should not have spacings in between them. They should be connected in some way like the following: NY_to_LA. This should also be the case when the user creates names in the GUI. The format of the file is as follows: 
The first line always has three columns that are separated by two spacings. The format will be like “string 4 6”. The 4 is the number of nodes and 6 is the number of edges. 

The line that specifies nodes has 5 columns separated by 4 spacings. The format will be like the following:  “n index nodeLabel xCoordinate yCoordinate”. The “n” indicates that it is a node. The nodeLabel is the data inside the node that you want to put in. Note that nodeLabel should not have any spacings or it will cause problems in parsing that info. The “index” must be an integer identifies the node. It is not necessarily the index of the node. It is just an indicator that allows us to tell the edges what nodes they are connected to in the latter point of the file. I set it up this way in the case that the lines for the nodes and edges are different. The actual index of the node will be based on the order that the node was added in. For example, if “n 4 San_Francisco 568 233” was in the text file right before  “n 1 Los_Angeles 480 75”, then the node San_Francisco would have index i while Los_Angeles would have index i+1. The xCoordinate, yCoordinate is the coordinates that the graph should be located on the canvas. However, I have not created any boundaries for xCoordinate and yCoordinate so it is based on the user’s discretion that he or she writes down coordinates within the boundary of the canvas. 

The line that specifies edges has 5 columns separated by 4 spacings. The for mat will be like the following: “e head tail edgeLabel edgeWeight”. “e” designates that the line is an edge. The “head” “tail” are integers that tell us which nodes it is connected to. They refer to the “index” from a node line (as discussed in the previous paragraph). For example: “e 1 4 Sf_to_la 480.00” will connect node with identifier 1 and node with identifier 4, which is node Los_Angeles and node San_Francisco. The edgeLabel is basically the data of the edge. As with nodeLabel, there should be no spacings. The edgeWeight is the weight on the edge, which must be a number. 

-------------------------------------------------------------------------------
Testing
-------------------------------------------------------------------------------
I used TestGraph.java — an updated version from stagefinal1 to test my functions that I updated. I use 3 text files: worldmap.txt, worldmap2.txt, worldmapsCombined.txt to text my GUI. I tried importing and exporting the same files. I also imported two files, worldmap.txt and worldmap2.txt and combined the two graphs to created worldmapsCombined.txt. 
-------------------------------------------------------------------------------
Have a Great Winter Break :) 
-------------------------------------------------------------------------------
