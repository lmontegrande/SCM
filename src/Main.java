import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * @author Leo Montegrande
 * @contact lmontegrande91@gmail.com
 * @description This is the main class of the SCM program.  The program waits 
 * for user input and then parses the string to determine the user's commands
 * and the paramaters passed in.   The class will error check to make sure the
 * user puts in the correct information.
 *
 */
public class Main {

	private static String[] commands = {"create", "exit"};
	
	public static void main(String[] args) {
		handleInput();
	}

	/**
	 * Main loop.  Wait's for user input then handles input
	 */
	public static void handleInput() {
		Scanner in = new Scanner(System.in);
		boolean isDone = false;
		System.out.println("Enter a command (ex: [command] [source] [target])");
		
		while (!isDone) {
			String command = in.nextLine();
			String[] values = null;
			
			try {
				values = parseInput(command);
			} catch (Exception e) {
				System.out.println("Invalid Input");
				continue;
			}
			
			if (values == null || values.length == 0) break;
			
			switch(values[0]){
			
				case "create":
					// Copy file values
					System.out.println("Cloning Files from " + values[1] + " to " + values[2]);
					createRepo(values);
					// Handle Manifest
					System.out.println("Generating Manifest at " + values[2]);
					createManifest(values);
					break;
					
				case "exit":
					System.out.println("Exiting Program...");
					isDone = true;
					break;
				default:
					System.out.println("Not a recognized command");
			}
		}
	}
	
	/**
	 * Handle Manifest
	 * @param args
	 */
	private static void createManifest(String[] values) {
		ManifestRecordCreatorQMV recordCreator = new ManifestRecordCreatorQMV();
		try {
			recordCreator.run(values);
		} catch (Exception e) {
			System.out.println("Error Generating Manifest");
		}
	}
	
	/**
	 * Copy File
	 * @param uri
	 * @param newFileName
	 */
	private static void createRepo(String[] values) {
		// Copy file to new location
		SCM_Copy copy = new SCM_Copy();
		try {
			copy.run(values[1], values[2]);
		} catch (Exception e) {
			System.out.println("Error Copying Repo");
		}
	}
	
	/**
	 * Parses the user's input
	 * @param input
	 * @return
	 * @throws Exception 
	 */
	private static String[] parseInput(String input) throws Exception {
		// Parse values into [command type] [src] [destination] [file name]
		// the values array will vary depending on [command type]
		// Remove "/" character at the end of URI
		String[] values = input.split(" ");
		
		switch(values[0]) {
			case "create":
				if (!isValidPath(values[1]) || !isValidPath(values[2]))
					throw new Exception();
				break;
			case "exit":
				break;
		}
		
		return values;
	}
	
	/**
	 * Is the string a valid URI
	 * @return
	 */
	private static boolean isValidPath(String path) {
		
		if (!path.contains(":")) {
			path = System.getProperty("user.dir") + "\\" + path;
		}
		
		String p = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9\\s_.-]+)+\\\\?";
		return Pattern.matches(p, path);
	}
}
