
/**
 * Employee class can store </br>
 * 		String employeeName </br>
 *   	int employeeNo </br>
 *   	int employeeAge </br>
 *   	String employeeState </br>
 *   	int employeeZipCode </br>
 *   	int[] advisors </br>
 *		</br>
 *		one employee can have at most 3 advisors. </br>
 *		Get and set functions for non-primitive data uses/returns a new object </br>
 *		overloaded toString method with format:</br>
 *      Employee Name: employeeName, Employee number: employeeNo, Employee age: employeeAge, Employee state: employeeState, Employee Zip: employeeZip, Employee advisers: advisers[0-max].</br>
 *      overloaded equals method compares employeeNo </br>
 *      static getAllAdvisors methods returns all distinct advisors between 2 employees
 * @author Wen
 *
 */
public class Employee {
	// member variables
    private String employeeName;
    private int employeeNo;
    private int employeeAge;
    private String employeeState;
    private int employeeZipCode;
    private int[] advisors;
    
    /** Empty constructor
     * This empty constructor set all objects to null and all data to 0;
     */
    public Employee() {
    	employeeName = null;
    	employeeNo = 0;
    	employeeAge = 0;
    	employeeState = null;
    	employeeZipCode = 0;
    	advisors = null;
    }
    
    // Constructor with input values
    /**
     * This constructor inputs all givens values using set functions
     * @param inputName
     * @param inputNo
     * @param inputAge
     * @param inputState
     * @param inputZipCode
     * @param inputAdvisors
     */
    public Employee(String inputName, int inputNo, int inputAge, String inputState, int inputZipCode, int[] inputAdvisors) {
    	setName(inputName);
    	setNo(inputNo);
    	setAge(inputAge);
    	setState(inputState);
    	setZipCode(inputZipCode);
    	setAdvisors(inputAdvisors);
    }
    
    // copy constructor
    /**
     * Copy constructor using deep clone. 
     * input must be an object of Employee. 
     * All values are set with set function.
     * @param input
     * @throws NullPointerException,IllegalArgumentException
     */
    public Employee(Object input) throws NullPointerException,IllegalArgumentException{
    	if (input != null && input instanceof Employee) {
    		// cast input to a new Employee temp
    		Employee temp = (Employee) input;
	    	setName(temp.getName());
	    	setNo(temp.getNo());
	    	setAge(temp.getAge());
	    	setState(temp.getState());
	    	setZipCode(temp.getZipCode());
	    	setAdvisors(temp.getAdvisors());
	    	return;
    	}
    	if (input == null)
    		throw new NullPointerException("Parameter object can not be null.");
    	throw new IllegalArgumentException("input is not an object of Employee") ;
    }
    
    // Accessors
    
    /**
     * accessor returns clone of Advisor
     * @return cloneOfAdvisorArray
     */
    public int[] getAdvisors() {
    	
		return advisors.clone();
	}
    
    /**
     * Returns the value of employeeZipCode
     * @return employeeZipCode
     */
    public int getZipCode() {
		return employeeZipCode;
	}
    
    /**
     * Returns the value of employeeAge
     * @return employeeAge
     */
	public int getAge() {
		return employeeAge;
	}
	
	 /**
     * Returns a copy of employeeState
     * @return copy of employeeState
     */
	public String getState() {
		return new String(employeeState);
	}

	/**
     * Returns the value of employeeNo
     * @return employeeNo
     */
	public int getNo() {
		return employeeNo;
	}

	/**
     * Returns a copy of employeeName
     * @return copy of employeeName
     */
	public String getName() {
		return new String(employeeName);
	}

	// Mutators
	// Advisor can't have more than 3 values
	/**
	 * Set a copy of the inputAdvisors to object's advisors. 
	 * advisor amount must be 3 or less. 
	 * uses addAdvisor function.
	 * @param inputAdvisors
	 * @throws IllegalArgumentException
	 */
    public void setAdvisors(int[] inputAdvisors) throws IllegalArgumentException{
		advisors = new int[0];
    	if (inputAdvisors.length <= 3) {
			for (int i = 0; i < inputAdvisors.length; i++) {
				advisors = addAdvisor(advisors, inputAdvisors[i]);
			}
			
			return;
		}
		throw new IllegalArgumentException("Must have less than or equal to 3 advisors.");
	}
    
    /**
     * set object's employeeZip to inputZipCode
     * @param inputZipCode
     */
    public void setZipCode(int inputZipCode) {
		employeeZipCode = inputZipCode;
		
	}
    
    /**
     * set object's employeeAge to inputAge
     * @param inputAge
     */
    public void setAge(int inputAge) {
		employeeAge = inputAge;
		
	}
    
    /**
     * set object's employeeState to a copy of employeeState
     * @param inputState
     */
    public void setState(String inputState) {
		employeeState = new String(inputState);
	}
    
    
    /**
     * set object's employeeNo to inputNo
     * @param inputNo
     */
    public void setNo(int inputNo) {
		employeeNo = inputNo;
		
	}

    /**
     * set object's employeeName to a copy of inputName
     * @param inputName
     */
	public void setName(String inputName) {
    	employeeName = new String(inputName);
    }
	
	/** returns a string with format 
	 * Employee Name: employeeName, Employee number: employeeNo, Employee age: employeeAge, Employee state: employeeState, Employee Zip: employeeZip, Employee advisers: advisers[0-max].
	 * @return String formatedString
	 */
	public String toString() {
		// Organize the String information in the order of
		// employee name, employee no, age, state, zip code, and list of advisors’ employee nos
		String result = "Employee Name: " + employeeName + ", " + "Employee number: " + Integer.toString(employeeNo) + ", " + "Employee age: " + Integer.toString(employeeAge) + ", " +
				"Employee state: " + employeeState + ", " + "Employee Zip: " + Integer.toString(employeeZipCode) + ", " + "Employee advisers: ";
		for (int i = 0; i < advisors.length; i++) {
			result += (advisors[i]);
			if (i != advisors.length - 1) {
				result += ", ";
			}
			else {
				result += ".";
			}
		}
		
		return result;
	}
	
	/** Test if two objects have the same employee number.
	 * true for same, false for not same. 
	 * input must be an object of Employee and must not be null. 
	 * @param input
	 * @return boolean
	 * @throws NullPointerException
	 */
	public boolean equals(Object input) throws NullPointerException {
		if (input != null && input instanceof Employee) {
			Employee other = (Employee)input;
			if (employeeNo == other.getNo()) return true;
			return false;
		}
		throw new NullPointerException("Parameter can not be null.");
	}
	
	/**
	 * Returns all distinct advisors between 2 employees
	 * both employees must not be null.
	 * uses contains(array, int) to test if the object is already in array. 
	 * uses addAdvisors(array, int) to add distinct advisors to array. 
	 * @param e1
	 * @param e2
	 * @return int[]
	 * @throws NullPointerException
	 */
	public static int[] getAllAdvisors(Employee e1, Employee e2) throws NullPointerException {
		if (e1 != null & e2 != null) {
			int[] result = new int[0];
			// check if result contains the advisor,
			// if not, add it to result
			for (int i = 0; i < e1.getAdvisors().length; i++) {
				if (!contains(result, e1.getAdvisors()[i])) {
					result = addAdvisor(result, e1.getAdvisors()[i]);
				}
			}
			for (int i = 0; i < e2.getAdvisors().length; i++) {
				if (!contains(result, e2.getAdvisors()[i])) {
					result = addAdvisor(result, e2.getAdvisors()[i]);
				}
			}
			return result;
		}
		throw new NullPointerException("Parameter can not be null.");
	}
	/**
	 * add inputAdvisor and input to a new array that have one more size than input,
	 * returns the new array result.
	 * @param input
	 * @param inputAdvisor
	 * @return result
	 */
	private static int[] addAdvisor(int[] input, int inputAdvisor) {
		int[] result = new int[input.length + 1];
		// put everything from input to result, 
		// then add advisor to last.
		for(int i = 0; i < input.length; i++) {
			result[i] = input[i];
		}
		result[result.length - 1] = inputAdvisor;
		return result;
	}
	/**
	 * Determine if int inputAdvisor is already in the array input.
	 * @param input
	 * @param inputAdvisor
	 * @return boolean
	 */
	private static boolean contains(int[] input, int inputAdvisor) {
		for (int i = 0; i < input.length; i++) {
			if (input[i] == inputAdvisor) {
				return true;
			}
		}
		return false;
	}
	
	/** Main method, for testing class methods.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Test empty constructor
		Employee empty = new Employee();
		System.out.println("Building new object Employee empty using empty constructor.");
		
		// test value constructor
		Employee a = new Employee("aName", 100100, 10, "NM", 100000, new int[] {1,2,3});
		System.out.println("Building new object Employee a using constuctor Employee(\"aName\", 100100, 10, \"NM\", 100000, new int[] {1,2,3})");
		
		// test copy constructor
		Employee b = new Employee(a);
		System.out.println("Building new object Employee b using constructor Employee(a).\n");
		Employee c = new Employee(a);
		System.out.println("Building new object Employee c using constructor Employee(a).\n");
		Employee d = new Employee(a);
		System.out.println("Building new object Employee d using constructor Employee(a).\n");
		
		// test copy constructor null object
		System.out.println("Building new object Employee emptyCopy using constructor Employee(empty).");
		System.out.println("Excepted NullPointerException with copying empty object.");
		try {
			@SuppressWarnings("unused")
			Employee emptyCopy = new Employee(empty);
		}
		catch(Exception e) {
			System.out.println("Building emptyCopy failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// test copy constructor null
		System.out.println("Building new object Employee emptyCopy using constructor Employee(null).");
		System.out.println("Excepted NullPointerException with copying empty object.");
		try {
			@SuppressWarnings("unused")
			Employee emptyCopy = new Employee(null);
		}
		catch(Exception e) {
			System.out.println("Building emptyCopy failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// test copy constructor non-employee object
		System.out.println("Building new object Employee stringCopy using constructor Employee(\"apple\").");
		System.out.println("Excepted IlligalArugmentException with copying string object.");
		try {
			@SuppressWarnings("unused")
			Employee stringCopy = new Employee("apple");
		}
		catch(Exception e) {
			System.out.println("Building stringCopy failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// Test empty.accessors
		System.out.println("Testing empty.accessors");
		System.out.println("Expected to have exception since accessors don't handle null value.");
		try {
			System.out.printf("empty.getName is %s\n", empty.getName());
			System.out.printf("empty.getNo is %s\n", empty.getNo());
			System.out.printf("empty.getAge is %s\n", empty.getAge());
			System.out.printf("empty.getState is %s\n", empty.getState());
			System.out.printf("empty.getZipCode is %s\n", empty.getZipCode());
			for(int i = 0; i < empty.getAdvisors().length; i++) {
				System.out.printf("empty.getAdvisors()[%d] is: %d\n", i, empty.getAdvisors()[i]);
			}
			System.out.println();
		}
		catch (Exception e) {
			System.out.println("Printing empty.accessers failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// Test empty.toString
		System.out.println("Testing empty.toString()");
		System.out.println("Expected to have exception since toString don't handle null value.");
		try {
			System.out.println(empty);
		}
		catch (Exception e) {
			System.out.println("Printing empty failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// Test a.accessors
		System.out.println("Testing a.accessors");
		try {
			System.out.printf("a.getName is %s\n", a.getName());
			System.out.printf("a.getNo is %s\n", a.getNo());
			System.out.printf("a.getAge is %s\n", a.getAge());
			System.out.printf("a.getState is %s\n", a.getState());
			System.out.printf("a.getZipCode is %s\n", a.getZipCode());
			for(int i = 0; i < a.getAdvisors().length; i++) {
				System.out.printf("a.getAdvisors()[%d] is: %d\n", i, a.getAdvisors()[i]);
			}
			System.out.println();
		}
		catch (Exception e) {
			System.out.println("Printing a.accessers failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// Test b.accessors
		System.out.println("Testing b.accessors");
		try {
			System.out.printf("b.getName is %s\n", b.getName());
			System.out.printf("b.getNo is %s\n", b.getNo());
			System.out.printf("b.getAge is %s\n", b.getAge());
			System.out.printf("b.getState is %s\n", b.getState());
			System.out.printf("b.getZipCode is %s\n", b.getZipCode());
			for(int i = 0; i < b.getAdvisors().length; i++) {
				System.out.printf("b.getAdvisors()[%d] is: %d\n", i, b.getAdvisors()[i]);
			}
			System.out.println();
		}
		catch (Exception e) {
			System.out.println("Printing b.accessers failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// Test b.mutators
		System.out.println("Testing b.mutators.");
		System.out.println("Testing b.setName(\"bName\")");
		b.setName("bName");
		System.out.printf("b.getName is %s\n", b.getName());
		System.out.println("b.toString is \n" + b +"\n");
		
		System.out.println("Testing b.setNo(111111)");
		b.setNo(111111);
		System.out.printf("b.getNo is %s\n", b.getNo());
		System.out.println("b.toString is \n" + b +"\n");
		
		System.out.println("Testing b.setAge(11)");
		b.setAge(11);
		System.out.printf("b.getAge is %s\n", b.getAge());
		System.out.println("b.toString is \n" + b +"\n");
		
		System.out.println("Testing b.setState(\"NY\")");
		b.setState("NY");
		System.out.printf("b.getState is %s\n", b.getState());
		System.out.println("b.toString is \n" + b +"\n");
		
		System.out.println("Testing b.setZipCode(111000)");
		b.setZipCode(111000);
		System.out.printf("b.getZipCode is %s\n", b.getZipCode());
		System.out.println("b.toString is \n" + b +"\n");
		
		System.out.println("Testing b.setAdvisors(new int{3,4})");
		b.setAdvisors(new int[]{3,4});
		for(int i = 0; i < b.getAdvisors().length; i++) {
			System.out.printf("b.getAdvisors()[%d] is: %d\n", i, b.getAdvisors()[i]);
		}
		System.out.println("b.toString is \n" + b +"\n");
		
		System.out.println("Testing b.setAdvisors(new int{1,4,9,12,15})");
		System.out.println("Expected illigal argument since adivsor size can't be larger than 3");
		try {
			b.setAdvisors(new int[]{1,4,9,12,15});
			for(int i = 0; i < b.getAdvisors().length; i++) {
				System.out.printf("b.getAdvisors()[%d] is: %d\n", i, b.getAdvisors()[i]);
			}
			System.out.println("b.toString is \n" + b +"\n");
		}
		catch(Exception e) {
			System.out.println("b.setAdvisors(new int{1,4,9,12,15}) failed.");
			System.out.println(e);
			System.out.println();
		}
		
		// Testing equal() and object address
		System.out.println("Testing equal() and object address");
		System.out.println("a.toString is \n" + a);
		System.out.println("c.toString is \n" + c + "\n");
		System.out.println("a == c returns " + (a == c));
		System.out.println("a.equals(c) returns " + a.equals(c) + "\n");
		
		System.out.println("Set c employeeNumber to 100001.");
		c.setNo(100001);
		System.out.println("a == c returns " + (a == c));
		System.out.println("a.equals(c) returns " + a.equals(c) + "\n");
		
		System.out.println("Test equals() on non employee object.");
		try {
			
			System.out.println("a.equals(\"orange\") returns " + a.equals("orange"));
		}
		catch (NullPointerException e) {
			System.out.println("Evalute a.equals(\"orange\") failed");
			System.out.println(e);
			System.out.println();
		}
		
		// Testing getAllAdvisors
		System.out.println("Testing getAllAdvisors(a,b)");
		System.out.println("a.toString is \n" + a);
		System.out.println("b.toString is \n" + b);
		for (int i = 0; i < getAllAdvisors(a,b).length; i++) {
			int[] temp = getAllAdvisors(a,b);
			System.out.printf("getAllAdvisors(a,b)[%d] is %d \n", i, temp[i]);
		}
		System.out.println();
		
		System.out.println("Set d advisors to {5,2,3}");
		d.setAdvisors(new int[] {5,2,4});
		System.out.println("a.toString is \n" + a);
		System.out.println("d.toString is \n" + d);
		for (int i = 0; i < getAllAdvisors(d,b).length; i++) {
			int[] temp = getAllAdvisors(d,a);
			System.out.printf("getAllAdvisors(d,a)[%d] is %d \n", i, temp[i]);
		}
		System.out.println();
	}
}
