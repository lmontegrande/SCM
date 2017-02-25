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

public class ManifestRecordCreatorQMV {

    public void manifestCreator(int trigger) throws FileNotFoundException, IOException {
        
        //Java documentation consulted for appending a timestamp to file name:
        //https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
        //Time stamp format, via substring: YYYY-MM-DD
        String timeStamp = java.time.LocalDateTime.now().toString().substring(0, 10);
         
        String triggerDetails = "";
        switch (trigger){case 1: triggerDetails = "Triggered by: Create Repository";}
        
        //Source for open file dialog box and subsequent selection: 
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html
        //https://docs.oracle.com/javase/8/docs/api/java/awt/Component.html
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html#showDialog-java.awt.Component-java.lang.String-
        //https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html#setFileSelectionMode-int-
    
        System.out.println("Source Folder Selection:");
        File SourceFolder = getPath();
        String sourceFolderName = SourceFolder.getAbsolutePath() + "\\";
            
        System.out.println("Destination Folder Selection:");
        File DestinationFolder = getPath();
        String destinationFolderName = DestinationFolder.getAbsolutePath() + "\\";
        
        //Java documentation consulted for creating a file and writing text to it:
        //https://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html
        PrintWriter manifestWriter = new PrintWriter(destinationFolderName + "Manifest - " +  timeStamp + ".txt");
        manifestWriter.println("Manifest - " + timeStamp);
        manifestWriter.println("Date Created: " + java.time.LocalDateTime.now().toString());
        manifestWriter.println(triggerDetails);
       
        //Source for directory traversal:
        //https://docs.oracle.com/javase/7/docs/api/java/io/File.html#list()
        String folderTraversal[] = SourceFolder.list();
        CodeNameCreator newCode = new CodeNameCreator();
        
        for (int i = 0; i < folderTraversal.length; i++){
            System.out.println(folderTraversal[i]);
            manifestWriter.println(newCode.getAID(sourceFolderName + folderTraversal[i]) + 
                    " created by " + sourceFolderName);
        }
        manifestWriter.close();        
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
        manifestCreator(trigger); 
    }
}   