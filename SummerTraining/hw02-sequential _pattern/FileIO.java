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

    public ArrayList<Transaction> readFileFromTransDatabase(){
        ArrayList<Transaction> tmpListOfTrans = new ArrayList<Transaction>(); 
        try{
            String str;
            while((str = br.readLine()) != null){
                // SID TID ITEM#
                ArrayList<String> arr = this.splitStringToThreePart(str);
                Transaction tmpTrans = new Transaction(arr.get(0), arr.get(1), arr.get(2)); 
                tmpListOfTrans.add(tmpTrans);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        Collections.sort(tmpListOfTrans, new Comparator(){
            public int compare(Object t1, Object t2){
                Integer t1SID = Integer.parseInt(((Transaction) t1).getSID());
                Integer t2SID = Integer.parseInt(((Transaction) t2).getSID());
                int sComp = t1SID.compareTo(t2SID);
                if(sComp != 0){
                    return sComp;
                }

                Integer t1TID = Integer.parseInt(((Transaction) t1).getTID());
                Integer t2TID = Integer.parseInt(((Transaction) t2).getTID());
                int tComp = t1TID.compareTo(t2TID);
                if(tComp != 0){
                    return tComp;
                }

                Integer t1ITEM = Integer.parseInt(((Transaction) t1).getITEM());
                Integer t2ITEM = Integer.parseInt(((Transaction) t2) .getITEM());
                int iComp = t1ITEM.compareTo(t2ITEM);
                return iComp;
            }
        });
            
        return tmpListOfTrans;
    }

    public ArrayList<String> splitStringToThreePart(String str){
        StringTokenizer st = new StringTokenizer(str, " ");
        ArrayList<String> tmpList = new ArrayList<String>();
        while(st.hasMoreTokens()){
            tmpList.add(st.nextToken());
        }

        return tmpList;
    }

    public void printSequentialPattern(ArrayList<Sequence> arr, String fileName){
        PrintWriter pw;
        try{
            pw = new PrintWriter(fileName, "UTF-8");
            for(Sequence seq : arr){
                for(ItemSet i : seq.getSequence()){
                    String tmpStr = new String();
                    tmpStr = tmpStr.concat("( ");
                    for(String str : i.getItems()){
                        tmpStr = tmpStr.concat(str + " ");
                    } 
                    tmpStr = tmpStr.concat(")");
                    pw.print(tmpStr);
                }
                pw.println();
            }
            pw.println("Total pattern: " + arr.size());
            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
