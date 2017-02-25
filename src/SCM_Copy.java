import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


//http://javatutorialhq.com/java/example-source-code/io/file/copy-folder-java-using-java-io-package/
//https://www.youtube.com/watch?v=cUxoLssRyAE

public class SCM_Copy {
   
    public void run(String srcDirString, String destDirString) throws IOException {
        File srcDir = new File(srcDirString);
        File destDir = new File(destDirString);
        
        SCM_Copy testCopy = new SCM_Copy();
        testCopy.copyDirectory(srcDir, destDir);
        System.out.println("Copied directory");
    }
    
    /**
     * Copy a directory from a source file to a destination file
     * @param src
     * @param dest
     * @throws IOException 
     */
    public void copyDirectory(File src, File dest) throws IOException{
        
        if(src.isDirectory()){//the source is a folder
            dest.mkdir();//make a new folder at the destination
            String files[] = src.list();//file names into array
            for(String fileName : files){
                //create source/destination target for each file
                File srcFile = new File(src, fileName);
                File destFile = new File(dest, fileName);
                copyDirectory(srcFile, destFile);//recursive call
            }
        }
        else{//the source is a file
            if(new File(dest.getAbsolutePath()).mkdir()){//create leaf folder
                System.out.println(src.getName()+" leaf folder create");
                
                //create code named artifact in the leaf folder
                File artifact = new File(dest.getAbsolutePath(), getAID(src));
                copyFile(src, artifact);//copy the file contents
            }
            
            
        }
        
        
    }
    
    /**
     * Copy a source file to a destination file by byte
     * @param src
     * @param dest
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void copyFile(File src, File dest) throws FileNotFoundException, IOException{
        
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];
        int length;

        while((length = in.read(buffer)) > 0 ){//copy file by byte
            out.write(buffer, 0, length);
        }
        
        in.close();
        out.close();
        
        System.out.println(src+" copied to "+dest);
        
    }
    
    /**
     * Generate Artifact ID from a file
     * @param file
     * @return AID code name of a file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private String getAID(File file) throws FileNotFoundException, IOException{
        int weights[] = {1, 3, 11, 17};
        int count = 0;//for selecting weight
        int sum = 0;//checksum value
        int chr;
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        
        while((chr = reader.read()) != -1){ //reader returns -1 for end of stream
            if(chr > 31){ //ignore ascii control characters
                sum += chr*(weights[count % 4]); //add to rolling checksum
                count++;
            }            
        }
        reader.close();
        
        int fileSize = (int)file.length();
        String fileExt = getFileExtension(file);
        return String.format("%d.%d.%s", sum, fileSize, fileExt);
    }
    
    /**
     * Get the file extension from a file
     * @param file
     * @return file extension without the "." 
     */ 
    private String getFileExtension(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}