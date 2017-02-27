//CECS 543 Sec. 02
//Spring 2017
//Group Name: QMV
//The following is part of a semester-long project for 
//the aforementioned course, section, term, and group.

//Sources used for file/folder creation:
//https://docs.oracle.com/javase/7/docs/api/java/io/File.html
//https://docs.oracle.com/javase/7/docs/api/java/io/File.html#createNewFile()
//https://docs.oracle.com/javase/7/docs/api/java/io/File.html#mkdir()

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

/**
 * 
 * @author Sean Vidal
 * @contact notationdevice@gmail.com
 * @description This class handles managing the manifest whenever the repo is changed.
 *
 */
public class ManifestRecordCreatorQMV {

    public void manifestCreator(int trigger, String srcPath, String dstPath) throws FileNotFoundException, IOException {
        
        //Java documentation consulted for appending a timestamp to file name:
        //https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
        //Time stamp format, via substring: YYYY-MM-DD
        String timeStamp = java.time.LocalDateTime.now().toString().substring(0, 10);
        
        //placeholder for user command
        String triggerDetails = "";
        switch (trigger){case 1: triggerDetails = "create";}
        
        //Source for open file dialog box and subsequent selection: 
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html
        //https://docs.oracle.com/javase/8/docs/api/java/awt/Component.html
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html#showDialog-java.awt.Component-java.lang.String-
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html#setFileSelectionMode-int-
    
        //System.out.println("Source Folder Selection:");
        //File SourceFolder = getPath();
        //String sourceFolderName = SourceFolder.getAbsolutePath() + "\\";
        String sourceFolderName = srcPath;
            
        //System.out.println("Destination Folder Selection:");
        //File DestinationFolder = getPath();
        //String destinationFolderName = DestinationFolder.getAbsolutePath() + "\\";
        File DestinationFolder = new File(dstPath);
        String destinationFolderName = dstPath;
        
        if (!destinationFolderName.substring(destinationFolderName.length()-2, destinationFolderName.length()-1).equals("\\"))
        	destinationFolderName = destinationFolderName.concat("\\");
        
        //Java documentation consulted for creating a file and writing text to it:
        //https://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html
        PrintWriter manifestWriter = new PrintWriter(destinationFolderName + "Manifest - " +  timeStamp + ".txt");
        //manifestWriter.println("Manifest - " + timeStamp);
        manifestWriter.println("SCM-1");
        manifestWriter.println("Date Created: " + java.time.LocalDateTime.now().toString());
        manifestWriter.println("Command: "+triggerDetails+" "+srcPath+" "+dstPath);
        manifestWriter.println("Source Path: "+srcPath);
	manifestWriter.println("Target Path: "+dstPath);
       
        //Source for directory traversal:
        //https://docs.oracle.com/javase/7/docs/api/java/io/File.html#list()
        RecursivelyAddToManifest(manifestWriter, sourceFolderName);
        
        manifestWriter.close();        
    }
 
    private void RecursivelyAddToManifest(PrintWriter manifestWriter, String sourceFolderName) throws FileNotFoundException, IOException {
    	File sourceFolder = new File(sourceFolderName);
    	String folderTraversal[] = sourceFolder.list();
    	
    	for (int i = 0; i < folderTraversal.length; i++){
    		String currentFileName = sourceFolder.getAbsolutePath() + "\\" + folderTraversal[i];
    		File currentFile = new File(currentFileName);
    		if (currentFile.isDirectory()) {
				RecursivelyAddToManifest(manifestWriter, currentFileName);
    		} else {
    			manifestWriter.println(ArtifactID.getAID(sourceFolderName + "\\" + folderTraversal[i]) + " created by " + currentFileName);
    		}
        }
    }
    
    //getPath() is incomplete; sample output is provided.
    public File getPath(){
        JFileChooser selectFolder = new JFileChooser();
        selectFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectFolder.showOpenDialog(null);
        File Folder = selectFolder.getSelectedFile();
        return Folder;
    }
    
    public void run(String[] args) throws IOException {
        int trigger;
        trigger = 1;    
        manifestCreator(trigger, args[1], args[2]); 
    }
}   