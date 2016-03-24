
import java.io.*;
import java.util.ArrayList;

public class InputTXT {
	
    public static ArrayList<String> LeggiRighe() {

    	 // Nome del file da leggere
        String fileName = "persone.txt";

        String line = null;
        
        ArrayList<String> righe = new ArrayList<String>();
        
        try {
            
            FileReader fileReader = 
                new FileReader(fileName);

            
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	righe.add(line.toString());
            }   
            
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Errore apertura File: '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Errore lettura File: '" 
                + fileName + "'");                  
        }
        
        return righe;
    }
}
