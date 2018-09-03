import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.PrintWriter;

public class FileIO{
	private String filePath;

    private FileReader fr;
    private BufferedReader br;

    public FileIO(String path){
	    this.filePath = path;

        try{
            fr = new FileReader(this.filePath);
            br = new BufferedReader(fr);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // public ArrayList<Point> readFileFromDataset(){

    // }
}