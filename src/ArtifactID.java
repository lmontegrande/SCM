import java.io.*;

/**
 * 
 * @author Sylvia Quach
 * @contact esquach@gmail.com
 * @description This class handles generating the Artifact ID (AID) code name of a file.
 *
 */
public final class ArtifactID {
    private static final int[] WEIGHTS = {1, 3, 11, 17};//weights for checksum
    
    private ArtifactID(){} //private constructor to prevent instantiation
        
    /**
     * Generate Artifact ID from a file
     * @param uri String uri of a file
     * @return AID code name of a file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String getAID(String uri) throws FileNotFoundException, IOException {
        int count = 0;//for selecting weight
        int sum = 0;//checksum value
        int chr;

        File file = new File(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        while((chr = reader.read()) != -1){ //reader returns -1 for end of stream
            if(chr > 31){ //ignore ascii control characters
                sum += chr*(WEIGHTS[count % 4]); //add to rolling checksum
                count++;
            }            
        }
        reader.close();
        
        int fileSize = (int)file.length();       
        String fileExt = getFileExtension(file);
        return String.format("%d.%d.%s", sum, fileSize, fileExt);
    }

    /**
     * Generate Artifact ID from a file
     * @param file
     * @return AID code name of a file
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String getAID(File file) throws FileNotFoundException, IOException{
        int count = 0;//for selecting weight
        int sum = 0;//checksum value
        int chr;
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        
        while((chr = reader.read()) != -1){ //reader returns -1 for end of stream
            if(chr > 31){ //ignore ascii control characters
                sum += chr*(WEIGHTS[count % 4]); //add to rolling checksum
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
    private static String getFileExtension(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
