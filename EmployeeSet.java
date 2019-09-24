import java.io.*;
/**
 * EmployeeSet is a collection class for Employee
 * EmployeeSet allows storing any amount of Employees
 * EmployeeSet allows add and removing Employees.
 * EmployeeSet uses deep clone to clone objects.
 * Can read from csv file.
 * Employee with same no is treated as the same employee
 * two different add method with adding a employee to a index and adding employee to end
 * add ordered allows ordering employee when building from empty.
 * @author Wen
 *
 */
public class EmployeeSet {
	private int size;
	private Employee[] data;
	/**Empty constructor
	 * Initiate data size to 0.
	 * Initiate capacity to 10.
	 * 
	 */
	public EmployeeSet() {
		size = 0;
		data = new Employee[10];
	}
	/**
	 * Create a deep clone of obj. 
	 * obj must not be null and must be an instance of EmployeeSet
	 * @param obj
	 * @throws Exception 
	 */
	public EmployeeSet(Object obj) throws NullPointerException {
		if(obj != null && obj instanceof EmployeeSet) {
			EmployeeSet input = (EmployeeSet) obj;
			
			size = input.size();
			data = new Employee[input.capacity()];
			// put the copy of other collection value to this collection
			for(int i = 0; i < input.size(); i++) {
				set(i,input.get(i));
			}
			return;
		}
		throw new NullPointerException("Object can not be null and must be an instance of EmployeeSet.");
	}
	/**
	 * returns the number of valid data in the collection
	 * @return size
	 */
	public int size() {
		return size;
	}
	/**
	 * returns the current maximum capacity of the collection
	 * @return data.length
	 */
	public int capacity() {
		return data.length;
	}
	/**
	 * if current capacity is smaller than minimumCapacity,
	 * create new array with size minimumCapacity
	 * copy data to new array
	 * @param minimumCapacity
	 */
	private void ensureCapacity(int minimumCapacity) {
		if (capacity() < minimumCapacity) {
			Employee[] temp = new Employee[minimumCapacity];
			for(int i = 0; i < size; i++) {
				temp[i] = data[i];
				// no need to create no object
				// because old pointer was the only pointer associated with the object.
			}
			data = temp;
		}
	}
	/**
	 * returns a copy of the Employee at the index
	 * @param index
	 * @return
	 */
	public Employee get(int index) {
		return new Employee(data[index]);
	}
	/**
	 * set a copy of input employee to data array at index.
	 * the old employee will not be preserved.
	 * input must not be null.
	 * index must be smaller than size.
	 * @param index
	 * @param input
	 * @throws Exception
	 */
	public void set(int index, Employee input) throws NullPointerException {
		if(input != null) {
			data[index] = new Employee(input);
			return;
		}
		throw new NullPointerException("Input must not be null.");
	}
	/**add input to the first non empty slot.
	 * increase size by 1
	 * input must not be null
	 * if size exceed index limited, double the index limit
	 * @param input
	 * @throws Exception 
	 */
	public void add(Employee input) throws NullPointerException {
		try {
			set(size, input);
			size++;
		}
		catch(IndexOutOfBoundsException e) {
			ensureCapacity(2 * capacity());
			set(size, input);
			size++;
		}
	}
	/**add input to index of data.
	 * push everything behind back by 1
	 * increase size by 1
	 * input must not be null
	 * if size exceed index limited, double the index limit
	 * @param input
	 * @throws Exception 
	 */
	public void add(int index, Employee input) throws NullPointerException {
		try {
			for (int i = size; i > index; i--) {
				set(i, get(i - 1));
			}
			set(index, input);
			size++;
		}
		catch(IndexOutOfBoundsException e) {
			ensureCapacity(2 * capacity());
			size++;
			for (int i = (size - 1); i > index; i--) {
				set(i, get(i - 1));
			}
			set(index, input);
		}
	}
	/**
	 * if collection contains a employee that have 
	 * the same employee number as input employee's 
	 * employee number. return true, else false.
	 * @param input
	 * @return boolean
	 */
	public boolean contains(Employee input) {
		for (int i = 0; i < size; i++) {
			if (get(i).equals(input)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Remove all employees in collection that have same employee number as input. 
	 * pull all employees after the removed employee forward by 1, to keep the same order. 
	 * data must not contain null pointer before data[size].
	 * returns if input was contained in the data.
	 * @param input
	 * @return
	 */
	public boolean remove(Employee input) {
		boolean contain = false;
		// remove all employees in collection that have same employee number as input.
		// pull all employees after the removed employee forward by 1, to keep the same order.
		for(int i = 0; i < size; i++) {
			if(get(i).equals(input)) {
				for(int j = i; j < (size - 1); j++) {
					try {
						set(j,get(j + 1));
						// note set throws null exception when there is null pointer.
						// since it won't have any, just don't do anything.
					}
					catch(NullPointerException e) {}
				}
				i--; 
				// read the same index again since value changed.
				size--;
				contain = true;
			}
		}
		return contain;
	}
	/**adds one employee object to this EmployeeSet instance such that the objects 
	 * in the employee array are in ascending order of employee nos. 
	 * input must not be null.
	 * data must already been sorted.
	 * 
	 * @param input
	 * @throws NullPointerException input can't be null.
	 */
	public void addOrdered(Employee input) throws NullPointerException {
		for(int i = 0; i < size; i++) {
			if(input.getNo() < get(i).getNo()) {
				add(i,input);
				return;
			}
		}
		// if input have larger number than all employee in collection
		// add it to last.
		add(input);
	}
	
	/*---------------------------------Read csv file methods----------------------------------------*/
	public static EmployeeSet read(String fileName) throws IOException {
		try {
			String[] VALUE_EXTRACTED = {"Employee Name", "Employee Number", "State", "Zip", "Age"};
			// preprocess
			BufferedReader inputFile = new BufferedReader(new FileReader(fileName));
			String metaData = inputFile.readLine();
			int columnCount = findColumnCount(metaData);
			Object[] metaArray = toObjectArray(metaData, columnCount);
			// Create an int array that contains the index of values that need to be extracted
			int valueExtractedInt[] = new int[VALUE_EXTRACTED.length];
			// Linear search for the index with the name for values needed. (looking in meta data)
			for (int i = 0; i < VALUE_EXTRACTED.length; i++) {
				for (int j = 0; j < columnCount; j++) {
					if (metaArray[j].equals(VALUE_EXTRACTED[i])) {
						valueExtractedInt[i] = j;
						break;
					}
				}
			}
			// create the EmployeeSet that will contain the data
			EmployeeSet data = new EmployeeSet();
			
			// convert each line(String) to Object array,
			// then to Employee, and add them to EmployeeSet
			String tempString;
			while ((tempString = inputFile.readLine()) != null) {
				// convert input string to object array, then extract the data for Employee class, convert it to Employee
				// then add to EmployeeSet
				Object[] tempArray = toObjectArray(tempString, columnCount);// Stores the current working row in object array form.
				// Create a temp Employee that contains the info
				Employee tempEmployee = new Employee();
				// Set Employee's data to the temp employee
				tempEmployee.setName((String)tempArray[valueExtractedInt[0]]);
				try {
					tempEmployee.setNo(Integer.parseInt((String)tempArray[valueExtractedInt[1]]));
				}
				catch(NumberFormatException e) {
					tempEmployee.setNo(0);
				}
				tempEmployee.setState((String)tempArray[valueExtractedInt[2]]);
				try {
					tempEmployee.setZipCode(Integer.parseInt((String)tempArray[valueExtractedInt[3]]));
				}
				catch(NumberFormatException e) {
					tempEmployee.setZipCode(0);
				}
				try {
					tempEmployee.setAge(Integer.parseInt((String)tempArray[valueExtractedInt[4]]));
				}
				catch(NumberFormatException e) {
					tempEmployee.setAge(0);
				}
				// set tempEmployee's advisor to empty array since there is no data.
				tempEmployee.setAdvisors(new int[] {});
				// add function throws NullPointerException, but there shouln't be any null value.
				try {
					data.addOrdered(tempEmployee);
				}
				catch(Exception e) {
					
				}
			}
			inputFile.close();
			return data;
			
		}
		catch (FileNotFoundException e) {
			System.out.println(fileName + " is not found.");
			return null;
		}
	}
	/**
	 * Change a string to a Object(String) array that obeys the CSV rule
	 * @param processingString
	 * @param arraySize
	 * @return Object array form of the array
	 */
	private static Object[] toObjectArray(String processingString, int arraySize) {
		Object[] processingArray = new Object[arraySize]; 
		int currentWorkingIndex = 0; // the index of array that's doing string concatenation.
		boolean insideQutation = false;
		
		// initiate empty string to all elements of the array.
		for (int i = 0; i < arraySize; i++) {
			processingArray[i] = "";
		}
		
		// loop though the characters in the string
		for (int i = 0; i < processingString.length(); i++) {
			
			// if a comma is reached
			if (processingString.charAt(i) == ',') {
				//  while outside quotation mark, start to work on next index
				if(!insideQutation) {
					currentWorkingIndex++;
					continue;
				}
				
				// while inside quotation mark, concat the character current working index.
				processingArray[currentWorkingIndex] = processingArray[currentWorkingIndex].toString().concat(Character.toString(processingString.charAt(i)));
				continue;
			}
			
			// if a quotation mark is reached 
			if (processingString.charAt(i) == '"') {
				// while the next character is also quotation mark, concat a quotation mark to current working index
				// skip next character
				if (processingString.charAt(i + 1) == '"') {
					processingArray[currentWorkingIndex] = processingArray[currentWorkingIndex].toString().concat(Character.toString(processingString.charAt(i)));
					i++;
					continue;
				}
				
				// if next character is not quotation mark, toggle insideQutation
				// do not concat anything
				insideQutation = !insideQutation;
				continue;
			}
			
			// newline characters are assumed to not exist in dataset.
			
			// all other characters are simply concated to current working index
			processingArray[currentWorkingIndex] = processingArray[currentWorkingIndex].toString().concat(Character.toString(processingString.charAt(i)));
			
		}
		
		return processingArray;
	}
	/**
	 * Returns the amont of rows in a column
	 * The name is backward
	 * @param metaData
	 * @return row count
	 */
	private static int findColumnCount(String metaData) {
		int arraySize = 1;
		boolean insideQutation = false;
		// loop though all characters of the String
		for (int i = 0; i < metaData.length(); i++) {
			// if a comma is reached while outside quotation marks, arraySize should increase.
			if(metaData.charAt(i) == ',' && !insideQutation) {
				arraySize++;
				continue;
			}
			// if a comma is reached while inside quotation marks, arraySize shouldn't change.
			// if quotation mark is reached,
			if(metaData.charAt(i) == '"') {
				// if the next char is quotation mark, both character should be ignored, skip next character
				if (metaData.charAt(i + 1) == '"') {
					i++;
					continue;
				}
				// if the next char is not quotation mark, toggle insideQutation
				insideQutation = !insideQutation;
				continue;
			}
		}
		return arraySize;
	}
	
	/*---------------------------------Test methods in main method----------------------------------------*/
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Test empty constructor
		EmployeeSet empty = new EmployeeSet();
		System.out.println("Building new EmployeeSet with empty constructer.");
		
		// Test empty.size()
		System.out.println("empty.size() is " + empty.size());
		
		// Test empty.capacity()
		System.out.println("empty.capacity() is " + empty.capacity());
		
		// Create new Employee Object employeeA
		System.out.println("\nCreate new Employee object employeeA.");
		Employee employeeA = new Employee();
		// Set employeeA values.
		System.out.println("Setting employeeA values.");
		employeeA.setName("Aname");
		employeeA.setNo(10);
		employeeA.setState("NM");
		employeeA.setAge(101);
		employeeA.setAdvisors(new int[]{});
		
		// Testing add(new Employee());
		System.out.println("Testing empty.add(employeeA)");
		empty.add(employeeA);
		
		// Testing empty.get(i)
		System.out.println("empty.get(0) contains: \n" + empty.get(0) +"\n");
		
		// Testings contain
		System.out.println("testing empty.contain(employeeA)");
		System.out.println("empty.contain(employeeA) returned \n" + empty.contains(employeeA));
		
		// Create new Employee Object employeeA
		System.out.println("\nCreate new Employee object employeeB.");
		Employee employeeB = new Employee();
		System.out.println("Setting employeeB values.");
		employeeB.setName("Bname");
		employeeB.setNo(20);
		employeeB.setState("NM");
		employeeB.setAge(110);
		employeeB.setAdvisors(new int[]{});
		System.out.println("EmployeeB have value :\n" + employeeB);
		
		System.out.println("testing empty.contain(employeeB)");
		System.out.println("empty.contain(employeeB) returned \n" + empty.contains(employeeB));
		
		// Testings adding exceed 10 capacity
		System.out.println("\nTesting adding 20 more employees to empty\n");
		for(int i = 0; i < 20; i++) {
			empty.add(employeeA);
		}
		
		System.out.println("empty contains values: ");
		for(int i = 0; i < empty.size(); i++) {
			System.out.println(empty.get(i));
		}
		System.out.println();
		System.out.println("empty.size() returns " + empty.size());
		System.out.println("empty.capacity() returns " + empty.capacity());
		
		System.out.println("Testing remove()");
		System.out.println("add employeeB to empty");
		empty.add(employeeB);
		System.out.println("remove employeeA from empty, returned: " + empty.remove(employeeA) + "\n");
		
		System.out.println("empty now have size " + empty.size() + " and capacity " + empty.capacity() + "\n");
		System.out.println("empty contains values: ");
		for(int i = 0; i < empty.size(); i++) {
			System.out.println(empty.get(i));
		}
		System.out.println();
		
		// building new EmployeeSet setA with copy constructor
		EmployeeSet setA = new EmployeeSet(empty);
		System.out.println("Building new EmployeeSet setA with copy constructor EmployeeSet(empty)");
		System.out.println("setA contains values: ");
		for(int i = 0; i < setA.size(); i++) {
			System.out.println(setA.get(i));
		}
		System.out.println();
		
		// testing addordered with new EmployeeSet reading from csv
		System.out.println("Creating new EmployeeSet cvs that read from csv and test addOrdered");
		EmployeeSet csv = read("core_dataset.csv");
		
		System.out.println("csv is ordered with size " + csv.size() + " and capacity " + csv.capacity());
		System.out.println("cvs contains \n" );
		for (int i = 0; i < csv.size(); i++) {
			System.out.println(csv.get(i));
		}
		
		// testing add ordered 
		System.out.println("Testing add ordered");
		System.out.println("building new EmployeeSet order");
		EmployeeSet order = new EmployeeSet();
		System.out.println("order.addOrdered( employee with random numbers)");
		for(int i = 0; i < 20; i++) {
			Employee temp = new Employee();
			temp.setName("Name");
			temp.setAge(0);
			temp.setState("State");
			temp.setAdvisors(new int[] {});
			temp.setZipCode(0);
			temp.setNo((int)(Math.random() * 100));
			order.addOrdered(temp);
		}
		
		System.out.println("order have size " + order.size() + " and capcity " + order.capacity() + "\n");
		System.out.println("order contains values: ");
		for(int i = 0; i < order.size(); i++) {
			System.out.println(order.get(i));
		}
	}
}


