/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
	private HeapNode min;
	private int size;
	private int numOfTree;
	private int numOfMarked;
	private static int totalCuts;
	private static int totalLinks;
	
	public FibonacciHeap(){
		this.size = 0;
		this.numOfMarked = 0;
		this.numOfTree = 0;

	}

   /**
    * public boolean empty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean empty()
    {
		if (this.size == 0) {
			return true;
		}
		return false;
	}

		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap. 
    */
    public HeapNode insert(int key)
    {   
    	this.size ++;
    	this.numOfTree ++;
    	HeapNode node = new HeapNode(key);
    	
    	if (this.size() == 1){
    		this.min = node;
    		node.setPrev(node);
    		node.setNext(node);
    	}else{
    		if (this.min.getKey() > key){
    			this.min = node;
    		}
    		node.setNext(this.min.getNext());
    		this.min.setNext(node);
    		node.setPrev(min);
    		node.getNext().setPrev(node);
    	}
    	return node;
    }

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	return; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	if (this.min != null){
    		return this.min;
    	}
    	return null;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	HeapNode min2 = heap2.findMin();
    	//change pointers
    	this.min.getNext().setPrev(min2.getPrev());
    	min2.getPrev().setNext(this.min.getNext());
    	this.min.setNext(min2);
    	this.min.getNext().setPrev(this.min);
    	
    	if (this.findMin().getKey() > min2.getKey()){
    		this.min = min2;
    	}
    	
    	this.size += heap2.size();
    	this.numOfMarked += heap2.numOfMarked;
    	this.numOfTree += heap2.numOfTree;
    	
    	  return;		
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return this.size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep()
    {
	int[] arr = new int[42];
        return arr; //	 to be replaced by student code
    }

   /**
    * public void arrayToHeap()
    *
    * Insert the array to the heap. Delete previous elemnts in the heap.
    * 
    */
    public void arrayToHeap(int[] array)
    {
        return; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {   
    	x.setKey(x.getKey()-delta);
    	HeapNode y = x.getParent();
    	if (y != null && x.getKey() < y.getKey()){
    		cascadingCut (x,y);
    	}
    	this.insert(x.getKey());
    	return;
    }
    
    /** Cut x from its parent y **/
    public void cut (HeapNode x, HeapNode y){
    	totalCuts ++;
    	x.setParent(null);
    	if (x.isMark()){
    		this.numOfMarked--;
    	}
    	x.setMark(false);
    	y.setRank(y.getRank()-1); //deacrese the rank of y by 1
    	if (x.getNext() == x){
    		y.setChild(null);
    	}else{
    		y.setChild(x.getNext());
    		x.getPrev().setNext(x.getNext());
    		x.getNext().setPrev(x.getPrev());
    	}
    }

    /** Perform a cascading-cut process starting at x **/
    public void cascadingCut (HeapNode x, HeapNode y){
    	cut (x,y);
    	if (y.getParent() != null){
    		if (!y.isMark()){
    			y.setMark(true);
    			this.numOfMarked++;
    		}else{
    			cascadingCut(y, y.getParent());
    		}
    	}
    }
    
   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return (this.numOfTree + (2*this.numOfMarked));
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return totalLinks;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return totalCuts;
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{
    	private int key;
    	private int rank;
    	private boolean mark;
    	private HeapNode child;
    	private HeapNode next;
    	private HeapNode prev;
    	private HeapNode parent;
    	
  	public HeapNode(int key) {
  		this.key = key;
  		this.mark = false;
  		this.rank = 0;
  		this.child = null;
  		this.next = null;
  		this.prev = null;
  		this.parent = null;
      }

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the mark
	 */
	public boolean isMark() {
		return mark;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}

	/**
	 * @return the child
	 */
	public HeapNode getChild() {
		return child;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(HeapNode child) {
		this.child = child;
	}

	/**
	 * @return the next
	 */
	public HeapNode getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(HeapNode next) {
		this.next = next;
	}

	/**
	 * @return the prev
	 */
	public HeapNode getPrev() {
		return prev;
	}

	/**
	 * @param prev the prev to set
	 */
	public void setPrev(HeapNode prev) {
		this.prev = prev;
	}

	/**
	 * @return the parent
	 */
	public HeapNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(HeapNode parent) {
		this.parent = parent;
	}

    }
}
