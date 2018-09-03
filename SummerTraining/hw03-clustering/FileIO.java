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

    public ArrayList<Point> readFileFromDatasetAndGenPointArr(){
    	ArrayList<Point> pointArr = new ArrayList<>();

    	try{
            String str;
            while((str = br.readLine()) != null){
                String[] arr = str.split("\\s+");
                double[] data = new double[arr.length-1];
                for(int i = 0; i < arr.length-1; i++){
                	data[i] = Double.parseDouble(arr[i]);
                }
                int flag = Integer.parseInt(arr[arr.length-1]);
                Point p = new Point(data, flag);
                pointArr.add(p);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return pointArr;
	}
}
