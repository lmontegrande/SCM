import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/*
Reference for copying directory:
http://javatutorialhq.com/java/example-source-code/io/file/copy-folder-java-using-java-io-package/
https://www.youtube.com/watch?v=cUxoLssRyAE
*/

/**
 * 
 * @author Sylvia Quach
 * @contact esquach@gmail.com
 * @description The SMC copy class handles copying a directory from one location to another.
 *
 */
public class SCM_Copy {
   
    public void run(String srcDirString, String destDirString) throws IOException {
        File srcDir = new File(srcDirString);
        File destDir = new File(destDirString);
        
        SCM_Copy testCopy = new SCM_Copy();
        testCopy.copyDirectory(srcDir, destDir);
        new File(destDir.getAbsolutePath()+"\\Activity").mkdir();//create Activity folder
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
                //create code named artifact in the leaf folder
                File artifact = new File(dest.getAbsolutePath(), ArtifactID.getAID(src));
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
        
    }
    
    
}