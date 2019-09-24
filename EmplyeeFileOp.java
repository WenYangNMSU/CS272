

import java.util.Scanner;
import java.io.*;
/**This class can read from and write to csv files </br>
 * 
 * features (only supported for files with meta data as first row): </br>
 *    - Support values that contain quotation marks and/or commas. </br>
 *    - Extract selected rows given names of header </br>
 *    - Parse selected rows to integer given names of the header </br>
 *    - Extract selected columns above or below a given age limit </br>
 * 
 * known limitation </br>
 *     Does <b>not</b> support line breaker within values due to the limitation of java.util.Scanner. </br>
 *         ie: "firstName \n lastName" </br>
 * 
 * Remember to change file path when testing (eclipse file import system isn't working as expected) </br>
 * 
 * !IMPORTANT </br>
 * Rows and columns were swapped in code and comments below due to early misunderstanding,</br>
 * I will try to fix that during refactoring.</br>
 *  
 * @author Wen Yang
 *
 */
public class EmplyeeFileOp {
	// Constants
	// !IMPORTANT 
	// all constants are case sensitive
	
	// determine the values that will be kept.
	public static final String[] VALUE_EXTRACTED = {"Employee Name", "Employee Number", "State", "Zip", "Age", "Sex"};
	// determine what values need to be parsed to Integer.
	public static final String[] PARSE_VALUE = {"Age"};
	// determine the limited age.
	public static final int AGE_LIMIT = 30;
	// determine if the program looks for people under age or over age (inclusive for both)
	public static final boolean AGE_UNDER = true;
	// determine the default input file path, please write the file full path when using eclipse
	public static String inputPath = "C:\\Users\\Wen\\Desktop\\core_dataset.csv";
	// determine the output file path
	public static String outputPath = "C:\\Users\\Wen\\Desktop\\young_employee.csv";
	
	
	public static Object[][] read(String fileName) {
		
		try {
			
			// This counts the rows of the file.
			int rowCount = 0;
			
			File inputFile = new File(fileName);
			
			// This scanner is used for determining the row and column of the file.
			Scanner prelimiaryScaner = new Scanner(inputFile);
			
			// Used the first row of the file to determine the amount of column, add 1 to row
			String metaData = prelimiaryScaner.nextLine();
			rowCount++;
			
			int columnCount = findColumnCount(metaData);
			
			// determine the amount of rows in the file.
			while(prelimiaryScaner.hasNext()) {
				prelimiaryScaner.nextLine();
				rowCount++;
			}
			
			prelimiaryScaner.close();
			
			// Create a 2D array with the correct number of rows and column for the file. [row][column], start from 0.
			Object fileContent[][] = new Object[rowCount][columnCount];
			
			// Initiate all elements to empty string 
			// in case the file missed some trailing commas
			// avoid having null values.
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					fileContent[i][j] = "";
				}
			}
			
			// create a scanner that take input.
			Scanner fileScan = new Scanner(inputFile);
			String tempString; // Stores the current working row.
			
			// fill the 2d array with file content
			for (int i = 0; i < rowCount; i++) {
				tempString = fileScan.nextLine();
				// convert input string to object array
				Object[] tempArray = toObjectArray(tempString, columnCount);// Stores the current working row in object array form.
				
				// input the current working row into the 2d array.
				fileContent[i] = tempArray;
			}
			fileScan.close();
			
			// Extract the needed values from the content
			// Create an int array that correspondence with the index of the needed values.
			int valueExtractedInt[] = new int[VALUE_EXTRACTED.length];
			
			// Linear search for the index with the name for values needed. (looking in meta data)
			for (int i = 0; i < VALUE_EXTRACTED.length; i++) {
				for (int j = 0; j < columnCount; j++) {
					if (fileContent[0][j].equals(VALUE_EXTRACTED[i])) {
						valueExtractedInt[i] = j;
						break;
					}
				}
			}
			
			// Create a new array with the same row size and column size that's equals to the number of values needed.
			Object[][] extractedFileContent = new Object[rowCount][VALUE_EXTRACTED.length];
			
			// input the extracted values to the new array.
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < VALUE_EXTRACTED.length; j++) {
					extractedFileContent[i][j] = fileContent[i][valueExtractedInt[j]];
				}
			}
			
			// Create an int array that correspondence with the name for values that needed to be parsed to int
			int parseValueInt[] = new int[PARSE_VALUE.length];
			
			// Linear search for the index for values that need to be parsed to int (looking in meta data).
			for (int i = 0; i < PARSE_VALUE.length; i++) {
				for (int j = 0; j < columnCount; j++) {
					if (extractedFileContent[0][j].equals(PARSE_VALUE[i])) {
						parseValueInt[i] = j;
						break;
					}
				}
			}
			
			// Parse the values that need to parsed to int
			// trim the string before parse in case extra spaces
			// if there are no valid input, return the original value
			// don't parse first row
			for (int i = 1; i < rowCount; i++) {
				for (int j = 0; j < PARSE_VALUE.length; j++) {
					try {
						extractedFileContent[i][parseValueInt[j]] = Integer.parseInt(extractedFileContent[i][parseValueInt[j]].toString().trim());
					}
					catch (NumberFormatException e) {}
				}
			}
			
			// returns the extracted and parsed informations
			return extractedFileContent;
		}
		
		catch (FileNotFoundException e) {
			System.out.printf("File \"%s\" is not found.\n", fileName);
		}
		// return null for error cases
		return null;
	}
	
	// This method finds the amount of column in a line.
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

	public static Object[][] findAgeGroup(Object[][] parsedDataSet) {
		// Linear search for the index of age column
		int indexOfAge = -1;
		
		for (int i = 0; i < parsedDataSet[0].length; i++) {
			if (parsedDataSet[0][i].equals("Age")) {
				indexOfAge = i;
				break;
			}
		}
		
		// if indexOfAge is still uninitiated, throw exception age isn't found.
		try {
			
			// count the amount of people that are under/over age. need to include meta data.
			int resultArraySize = 1;
			
			// looking for people that under age
			if (AGE_UNDER) {
				// Identity the amount of people under age
				// don't include meta data
				for (int i = 1; i < parsedDataSet.length; i++) {
					// if the data can't be cast to an int, don't do anything
					try {
						if ((int)parsedDataSet[i][indexOfAge] <= AGE_LIMIT) {
							resultArraySize++;
						}
					}
					catch (ClassCastException e) {}
				}
				
				// assign the column and rows of result array.
				Object resultArray[][] = new Object[resultArraySize][parsedDataSet[0].length];
				
				// input the first column of the data
				for (int i = 0; i < parsedDataSet[0].length; i++) {
					resultArray[0][i] = parsedDataSet[0][i];
				}
				
				// the current result row that haven't been used
				// start from second row due to meta data
				int currentResultRow = 1;
				
				// input the filtered data to resultArray
				// do not input first row
				for (int i = 1; i < parsedDataSet.length; i++) {
					// if the age is smaller than limit, input the row into current result row.
					// if data can't be cast to a int, don't add it
					try {
						if ((int)parsedDataSet[i][indexOfAge] <= AGE_LIMIT) {
							for (int j = 0; j < parsedDataSet[0].length; j++) {
								resultArray[currentResultRow][j] = parsedDataSet[i][j];
							}
							currentResultRow++;
						}
					}
					catch (ClassCastException e) {}
				}
				
				return resultArray;
			}
			
			else {
				// Identity the amount of people over age
				// don't include meta data
				for (int i = 1; i < parsedDataSet.length; i++) {
					// if the data can't be cast to an int, don't do anything
					try {
						if ((int)parsedDataSet[i][indexOfAge] >= AGE_LIMIT) {
							resultArraySize++;
						}
					}
					catch (ClassCastException e) {}
				}
				
				// assign the column and rows of result array.
				Object resultArray[][] = new Object[resultArraySize][parsedDataSet[0].length];
				
				// input the first column of the data
				for (int i = 0; i < parsedDataSet[0].length; i++) {
					resultArray[0][i] = parsedDataSet[0][i];
				}
				
				// start from second row due to meta data
				int currentResultRow = 1;
				
				// input the filtered data to resultArray
				// do not input first row
				for (int i = 1; i < parsedDataSet.length; i++) {
					// if the age is bigger than limit, input the row into current result row.
					// if data can't be cast to a int, don't add it
					try {
						if ((int)parsedDataSet[i][indexOfAge] >= AGE_LIMIT) {
							for (int j = 0; j < parsedDataSet[0].length; j++) {
								resultArray[currentResultRow][j] = parsedDataSet[i][j];
							}
							currentResultRow++;
						}
					}
					catch (ClassCastException e) {}
				}
				
				return resultArray;
			}
		}
		
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Age is not found in dataset.");
			return null;
		}
	}
	
	public static void write(Object[][] array2D, String fileName) {
		// Make everything in array to string
		for (int i = 0; i < array2D.length; i++) {
			for (int j = 0; j < array2D[0].length; j++) {
				array2D[i][j] = array2D[i][j].toString();
			}
		}
		
		String temp; // used to modify string without changing string in array.
		
		// create output file if it don't exist.
		File fileBuilder = new File(fileName);
		try {
			if (fileBuilder.createNewFile()) {
				System.out.println(fileName + "not found \nNew file \"" + fileName + "\" created.");
			}
			FileWriter fileWriter = new FileWriter(outputPath);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
		    
		    for (int i = 0; i < array2D.length; i ++) {
		    	for (int j = 0; j < array2D[0].length; j++) {
		    		temp = (String) array2D[i][j];
		    		// make all quotes 2 quotes
		    		temp = temp.replace("\"", "\"\"");
		    		
		    		// put quotes around elements if element have comma or quotation marks
		    		if (temp.contains("\"") || temp.contains(",")) {
		    			printWriter.print("\"");
		    		}
		    		printWriter.print(temp);
		    		if (temp.contains("\"") || temp.contains(",")) {
		    			printWriter.print("\"");
		    		}
		    		if (j != array2D[0].length - 1)
		    		printWriter.print(",");
		    	}
		    	printWriter.println();
		    }
		    
		    printWriter.close();
		} 
		catch (IOException e) {
			System.out.println("Unable to create output file," + fileName + "\nplease check your output path and permission settings.");
			e.printStackTrace();
		}
		
			
	}
	
	public static void main(String[] args) {
		Object[][] result = read(inputPath);
		result = findAgeGroup(result);
		
		write(result, outputPath);
		
		
	}
}