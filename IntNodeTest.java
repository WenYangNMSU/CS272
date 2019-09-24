/**
 * Test class for IntNode. 
 * Testing add node, remove node, search, listLength, and toString.
 * Also testing accessors and mutators.
 * @author Wen
 *
 */
public class IntNodeTest {
	/**
	 * main test method.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Building new IntNode head1 with empty constructor");
		IntNode head1 = new IntNode();
		System.out.println("Testing toString()");
		System.out.println("head1 contains " + head1);
		System.out.println("Testing IntNode.listLength(head1):" + IntNode.listLength(head1));
		System.out.println();
		
		System.out.println("Building new IntNode head2 with IntNode(1,head1)");
		IntNode head2 = new IntNode(1,head1);
		System.out.println("head2 contains " + head2);
		System.out.println("Testing IntNode.listLength(head2):" + IntNode.listLength(head2));
		System.out.println();
		
		System.out.println("Testing head2.getData():" + head2.getData());
		System.out.println("Testing head2.getNext():" + head2.getNext());
		System.out.println();
		
		System.out.println("Building new IntNode head3 with IntNode(2,head2)");
		IntNode head3 = new IntNode(2,head2);
		System.out.println("head3 contains " + head3);
		System.out.println("Testing IntNode.listLength(head3):" + IntNode.listLength(head3));
		System.out.println();
		
		System.out.println("Testing head3.setData(3)");
		head3.setData(3);
		System.out.println("head3.getData():" + head3.getData());
		System.out.println();
		
		System.out.println("Testing head3.setNext(head1)");
		head3.setNext(head1);
		System.out.println("head3.getNext():" + head3.getNext());
		System.out.println("Testing IntNode.listLength(head3):" + IntNode.listLength(head3));
		System.out.println();
		
		System.out.println("reset all heads using empty contructor");
		head1 = new IntNode();
		head2 = new IntNode();
		head3 = new IntNode();
		System.out.println();
		
		System.out.println("Add value 1 to 9 to head1");
		for(int i = 9; i > 0; i--) {
			head1.addNodeAfterThis(i);
		}
		System.out.println("head1 contains " + head1);
		System.out.println("Testing IntNode.listLength(head1):" + IntNode.listLength(head1));
		System.out.println();
		
		System.out.println("Testing IntNode.search(head1, 1): " + IntNode.search(head1,1));
		System.out.println("Testing IntNode.search(head1, 9): " + IntNode.search(head1,9));
		System.out.println("Testing IntNode.search(head1, 10): " + IntNode.search(head1,10));
		System.out.println();
		
		System.out.println("Testing head1.removeNodeAfterThis()");
		head1.removeNodeAfterThis();
		System.out.println("head1 contains " + head1);
		System.out.println("Testing head1.removeNodeAfterThis() 10 more times");
		for (int i = 0; i < 10; i++) {
			head1.removeNodeAfterThis();
		}
		System.out.println("head1 contains " + head1);
	}
}
