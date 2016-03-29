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
    
    public void writeFile(String pathFile, String log){
    	try {

			File file = new File(pathFile);

			// Se il file non esiste, lo creo
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(log);
			bw.close();

			System.out.println("File di Log scritto correttamente!");

		} catch (IOException e) {
			System.out.println(
	                "Errore scrittura File di Log col seguente path: '" + pathFile + "'");
		}
    }
}