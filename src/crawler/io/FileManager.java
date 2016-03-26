package crawler.io;
import java.io.*;
import java.util.ArrayList;

public class FileManager {
	public FileManager (){	
	}
	
    public ArrayList<String> readNameFromPath(String pathFile) {
        String line = null;
        ArrayList<String> row = new ArrayList<String>();
        
        try {
            FileReader fileReader = new FileReader(pathFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	row.add(line.toString());
            }
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Errore apertura File: '" + pathFile + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Errore lettura File: '" + pathFile + "'");                  
        }
        return row;
    }
    
    public int writeFile(String pathFile){
    	// To implement
    	return 0;
    }
}