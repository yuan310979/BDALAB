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
    /** total number of transactions */
    private int numTrans;
    /** total number of items(maximum # of all items)*/
    private int numItems;
    /** the transaction filename */
    private String transFilename;
    /** minimum support */
    private double minSup;
    /** minimum confidence */
    private double minConf;

    public Apriori(String[] args){
        configure(args);
        execute();
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

            String tran;

            while((tran = br.readLine()) != null){
                // ignore empty line
                if(tran.matches("\\s*"))    continue;
                numTrans++;
                String[] tranStrArray = tran.replaceAll("\\s+", "").split(",");
                for(int i = 0; i < tranStrArray.length; i++){
                    int num = Integer.parseInt(tranStrArray[i]);
                    if(num+1 > numItems)    numItems = num+1;
                } 
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(fr != null)  fr.close();
                if(br != null)  br.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        log("=============== Configuration ===============");
        log("Dataset name: " + transFilename);
        log("Total transactions: " + numTrans);
        log("Total items: " + numItems);
        log("=========================================");
    }

    private void execute(){
        itemsets = new ArrayList<int[]>();
        genCandidateItemsetOfSize1();
        
        while(itemsets.size() > 1){
            genFrequentItemSet();
            break;
        }
    }

    private void genFrequentItemSet(){
        List<int[]> frequentItemsets = new ArrayList<int[]>();
        BufferedReader br = null;
        FileReader fr = null;
        try{
            fr = new FileReader(transFilename);
            br = new BufferedReader(fr);
        }
        catch(IOException e){
           e.printStackTrace(); 
        }

        // Calculate each itemset counts in all transactions
        int[] count = new int[itemsets.size()];

        // Use boolean array to show whether the item existed in certain transaction or not
        boolean[] trans = new boolean[numItems];

        for(int i = 0; i < numTrans; i++){
            String line = new String();
            try{
                line = br.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            lineToBooleanArray(line, trans);
            for(int j = 0; j < itemsets.size(); j++){
                boolean match = true;
                int[] cand = itemsets.get(j);
                for(int k = 0; k < cand.length; k++){
                    if(trans[cand[k]] == false){
                        match = false;
                        break;
                    }
                }
                if(match){
                    count[j]++;
                }
            }
        }
    }

    private void lineToBooleanArray(String line, boolean[] trans){
        if(line.matches("\\s*"))    return;
        // fill the boolean array with all false
        Arrays.fill(trans, false);
        String[] tranArray = line.replaceAll("\\s+", "").split(",");
        for(int i = 0; i < tranArray.length; i++){
            int index = Integer.parseInt(tranArray[i]);
            trans[index] = true;
        }
    }

    private void genCandidateItemsetOfSize1(){
        for(int i = 0; i < numItems; i++){
            int[] cand = {i};
            itemsets.add(cand);
        }
    }

    /** print out some messages */
    private void log(String str){
        System.err.println(str);
    }
}

