import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		handleInput();
	}

	/**
	 * Main loop.  Wait's for user input then handles input
	 */
	public static void handleInput() {
		Scanner in = new Scanner(System.in);
		boolean isDone = false;
		
		while (!isDone) {
			String command = in.nextLine();
			ArrayList<String> values = parseInput(command);
			
			if (values.isEmpty()) break;
			
			switch(values.get(0)){
			
				case "copy":
					String newFileName = createFileName(values.get(2));
					break;
					
				case "exit":
					isDone = true;
					break;
			}
		}
	}
	
	/**
	 * Parses the user's input
	 * @param input
	 * @return
	 */
	private static ArrayList parseInput(String input) {
		ArrayList values = new ArrayList();
		
		// Parse values into [command type] [target] [destination] [file name]
		// the values arraylist will vary depending on [command type]
		
		return values;
	}
	
	/**
	 * Uses algorithm provided by Siska for creating a SCM file name
	 * @param filePath
	 * @return
	 */
	private static String createFileName(String filePath) {
		String newFileName = "";
		
		// Parse filePath to get the file's name
		// Run Algorithm to create name
		
		return newFileName;
	}
	
}
