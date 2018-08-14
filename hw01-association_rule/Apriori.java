import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Apriori{
    /**
     * Main function parameter will have three parts: "Filename" "minSupport" "minConfidence"
     * ex: javac Apriori Mushroom 0.5 0.5
     */
    public static void main(String[] args) throws Exception{
        Apriori ap = new Apriori(args);
    } 

    /** the list of current itemsets */
    private List<int[]> itemsets;
    /** the set of all items */
    private HashSet<Integer> items;
    /** total number of transactions */
    private int numTrans;
    /** total number of items*/
    private int numItems;
    /** the transaction filename */
    private String transFilename;
    /** minimum support */
    private double minSup;
    /** minimum confidence */
    private double minConf;

    public Apriori(String[] args){
        configure(args);
    }

    private void configure(String[] args){
        if(args.length != 0)    transFilename = args[0]; 
        if(args.length >= 2)    minSup = Double.parseDouble(args[1]);
        else                    minSup = 0.8;
        if(args.length == 3)    minConf = Double.parseDouble(args[2]);
        else                    minConf = 0.8;

        BufferedReader br = null;
        FileReader fr = null;

        try{
            fr = new FileReader(transFilename);
            br = new BufferedReader(fr);
            items = new HashSet<Integer>();

            String tran;

            while((tran = br.readLine()) != null){
                // ignore empty line
                if(tran.matches("\\s*"))    continue;
                numTrans++;
                String[] tranStrArray = tran.replaceAll("\\s+", "").split(",");
                for(int i = 0; i < tranStrArray.length; i++){
                    items.add(Integer.parseInt(tranStrArray[i]));
                } 
            }
            numItems = items.size();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        log("=============== Configuration ===============");
        log("Dataset name: " + transFilename);
        log("Total transactions: " + numTrans);
        log("Total items: " + numItems);
        log("=========================================");
    }

    /** print out some messages */
    private void log(String str){
        System.err.println(str);
    }
}

