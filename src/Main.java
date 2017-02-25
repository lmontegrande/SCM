import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

	private static String[] commands = {"clone", "push", "pull", "exit"};
	
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
			String[] values = null;
			
			try{
				values = parseInput(command);
			} catch (Exception e) {
				System.out.println("Invalid Input");
			}
			
			if (values == null || values.length == 0) break;
			
			switch(values[values.length-1]){
			
				case "copy":
					// Handle Manifest
					handleManifest(values);
					// Create name
					String newFileName = createFileName(values[values.length-1]);
					// Copy file values
					CopyFile(values[1], values[2]);
					break;
					
				case "exit":
					isDone = true;
					break;
			}
		}
	}
	
	/**
	 * Handle Manifest
	 * @param args
	 */
	private static void handleManifest(String[] args) {
		ManifestRecordCreatorQMV recordCreator = new ManifestRecordCreatorQMV();
		try {
			recordCreator.run(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Copy File
	 * @param uri
	 * @param newFileName
	 */
	private static void CopyFile(String uri, String newFileName) {
		// Copy file to new location
		SCM_Copy copy = new SCM_Copy();
	}
	
	/**
	 * Parses the user's input
	 * @param input
	 * @return
	 */
	private static String[] parseInput(String input) throws Exception {
		// Parse values into [command type] [src] [destination] [file name]
		// the values array will vary depending on [command type]
		String[] values = input.split(" ");
		
		for(int x=0;x<commands.length;x++) {
			//
		}
		
		return values;
	}
	
	/**
	 * Is the string a valid URI
	 * @return
	 */
	private static boolean isValidPath(String path) {
		String p = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";
		return Pattern.matches(p, path);
	}
	
	/**
	 * Uses algorithm provided by Siska for creating a SCM file name
	 * @param filePath
	 * @return
	 */
	private static String createFileName(String filePath) {
		String newFileName = "";
		long checkSum = -1;
		long Bytes = -1;
		
		String[] uriParsed = filePath.split("/");
		
		newFileName = uriParsed[uriParsed.length-1];
		
		File file = new File(filePath);
		checkSum = file.length();
		
		// Parse filePath to get the file's name
		// Run Algorithm to create name
		// [CheckSum] + [#Bytes] + [FileName] + .java
		
		return newFileName;
	}
	
}
