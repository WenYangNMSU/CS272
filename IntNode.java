/**
 * IntNode class using linked list.
 * Allows add node, remove node, search, listLength, and toString.
 * toString format: this->next->...->tail
 * Accessers and mutators for data and next are provided.
 * @author Wen
 *
 */
public class IntNode {
	private int data;
	private IntNode next;
	// (1) the functionality of the methods, (2) the parameters, (3) the return values,
	// (4) the pre-conditions if there is any;
	/**
	 * Empty constructor that create a node with value 0 and nextValue null.
	 */
	public IntNode() {
		data = 0;
		next = null;
	}
	
	/**
	 * Value conductor that create node with inputData and given nextNode.
	 * @param inputData The data of the node.
	 * @param inputNext The pointer to next node.
	 */
	public IntNode(int inputData, IntNode inputNext) {
		setData(inputData);
		setNext(inputNext);
	}
	
	/**
	 * Accessor for Data
	 * @return data The value that is contained in current node.
	 */
	public int getData() {
		return data;
	}
	/**
	 * Accessor for Next
	 * @return next The pointer to the next node.
	 */
	public IntNode getNext() {
		return next;
	}
	/**
	 * Mutator for data
	 * @param inputData the new data that should be inputed.
	 */
	public void setData(int inputData) {
		data = inputData;
	}
	/**
	 * Mutator for next
	 * @param inputNext the new nextPointer that should inputed.
	 */
	public void setNext(IntNode inputNext) {
		next = inputNext;
	}
	/**
	 * returns a String with format head->next->...->tail
	 * @return String format head->next->...->tail
	 */
	public String toString() {
		// format 
		// head->next->...->tail
		String returnString = Integer.toString(data);
		IntNode currentNode = next;
		while(currentNode != null) {
			returnString = returnString + "->" + Integer.toString(currentNode.data);
			currentNode = currentNode.getNext();
		}
		
		return returnString;
	}
	/**
	 * Add a node after current node, given the data value. The new node should have Next as the Next of current node.
	 * Suppose origanl list was 5->10. 
	 * Add 20 after 5 would be 5->20->10.
	 * @param inputData The data value for the new node that need to be add.
	 */
	public void addNodeAfterThis(int inputData) {
		IntNode newNode = new IntNode();
		newNode.setData(inputData);
		newNode.setNext(next);
		
		setNext(newNode);
	}
	
	/**
	 * Remove the node after current node. Set the Next to the current Next's Next.
	 * If current Next is null, set Next to null.
	 */
	public void removeNodeAfterThis() {
		try {
			setNext(next.getNext());
		}
		// next.getNext() will throw a null exception if next is null.
		catch(NullPointerException e) {
			setNext(null);
		}
	}
	
	/**
	 * Returns the length of the list starting from head.
	 * @param head The starting point
	 * @return length The length of the list start from head.
	 */
	public static int listLength(IntNode head) {
		int length = 0;
		IntNode currentNode = head;
		while(currentNode != null) {
			length++;
			currentNode = currentNode.getNext();
		}
		return length;
	}
	
	/**
	 * Search if inputData is in the list starting from head. 
	 * @param head The start point of the list.
	 * @param inputData The data that's been searched.
	 * @return boolean True if data is in list, else false.
	 * @custom.precondition head is not null.
	 */
	public static boolean search(IntNode head, int inputData) {
		IntNode currentNode = head;
		while(currentNode != null) {
			if(currentNode.getData() == inputData) {
				return true;
			}
			currentNode = currentNode.getNext();
		}
		return false;
	}
}
