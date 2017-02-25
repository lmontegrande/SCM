import java.io.*;

public class CodeNameCreator {
        
    //Any sources she used will need to be sourced before submission.
   
    public String getAID(String uri) throws FileNotFoundException, IOException {
        int weights[] = {1, 3, 11, 17};
        int sum = 0;//5940
        int fileSize = 0;//12
        int chr;
        int count = 0;

        //File file = new File("test.txt");//file contains HELLO WORLD, in the project folder
        File file = new File(uri);//file contains HELLO WORLD, in the project folder
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        while((chr = reader.read()) != -1){ //reader returns -1 for end of stream
            if(chr > 31){ //ignore ascii control characters
                sum += chr*(weights[count % 4]); //add to rolling checksum
                System.out.println(chr+"*"+weights[count % 4]);//test output
                count++;
            }            
        }

        reader.close();
        fileSize = (int)file.length();
        
        String AIDCode = sum + "." + fileSize + ".java";
        return AIDCode;
    }    
}
