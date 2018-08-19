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

    private List<int[]> assoRuleLHS;

    /** the list of previous itemsets */
    private List<int[]> finalitemsets;
    /** the list of previous itemsets */
    private List<int[]> preitemsets;
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
        long startTime = System.currentTimeMillis();
        configure(args);
        execute();
        genAssociationRule();
        long endTime = System.currentTimeMillis();
        long collapsedTime = endTime - startTime;
        log("Execution Time: " + collapsedTime/1000.0 + "sec");
        outputMemoryConsumption();
    }

    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    private void outputMemoryConsumption(){
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);;
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
        preitemsets = new ArrayList<int[]>();
        itemsets = new ArrayList<int[]>();
        genCandidateItemsetOfSize1();
        genFrequentItemSet();
        //outputFrequentItemSet();
        while(itemsets.size() > 0){          
            if(genCandidateItemSetFromPreviousItemSet())
                break;
            genFrequentItemSet();
            //outputFrequentItemSet();
        }
    }

    private void outputFrequentItemSet(){
        for(int i = 0; i < itemsets.size(); i++){
            int[] tmp = itemsets.get(i);
            log(Arrays.toString(tmp));
        }
    }

    private void outputPreFrequentItemSet(){
        for(int i = 0; i < preitemsets.size(); i++){
            int[] tmp = preitemsets.get(i);
            log(Arrays.toString(tmp));
        }
    }

    private boolean genCandidateItemSetFromPreviousItemSet(){
        int currentItemSetSize = itemsets.get(0).length;
        List<int[]> newCandidateItemSet = new ArrayList<int[]>();
        for(int i = 0; i < itemsets.size(); i++){
            for(int j = i+1; j < itemsets.size(); j++){
                int numDifferent = 0;
                int[] newCand = new int[currentItemSetSize+1];

                int[] X = itemsets.get(i);
                int[] Y = itemsets.get(j);

                for(int k = 0; k < Y.length; k++){
                    newCand[k] = Y[k];
                }

                boolean found = true;
                for(int index = 0; index < X.length-1; index++){
                    if(X[index] != Y[index]){
                        found = false;
                        break;
                    }
                }
                if(found == true){
                    newCand[currentItemSetSize] = X[X.length-1];
                    Arrays.sort(newCand);
                    newCandidateItemSet.add(newCand);
                }
            }
        }

        if(newCandidateItemSet.size() == 0){
            outputFrequentItemSet();
            finalitemsets = itemsets;
            return true;
        }
        preitemsets = itemsets;
        itemsets = newCandidateItemSet;
        return false;
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
        // Print out candidate Itemset 
        /*
        for(int i = 0; i < itemsets.size(); i++){
            System.out.println(Arrays.toString(itemsets.get(i)) + ":" + count[i]);
        }
        */
        
        // Decide the frequent Itemset of size k(based on count[])
        for(int i = 0; i < itemsets.size(); i++){
            if(count[i] >= numTrans*minSup){
                frequentItemsets.add(itemsets.get(i));                
            }
        }

        if(frequentItemsets.size() == 0){
            outputPreFrequentItemSet();
            finalitemsets = preitemsets;
        }
        
        itemsets = frequentItemsets;
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
        for(int i = 1; i < numItems; i++){
            int[] cand = {i};
            itemsets.add(cand);
        }
    }

    private void genAssociationRule(){
        log("========== Generate association rule ==========");
        
        for(int i = 0; i < finalitemsets.size(); i++){
            printSubset(finalitemsets.get(i));
            // printAssociationRule(finalitemsets.get(i), finalitemsets.get(i).length);
        }

        log("===============================================");
    }

    private void printSubset(int[] arr){
        // input array length
        int n = arr.length;

        // use bit operation to get all possible subset
        // Ex:
        // if   arr = [1, 2, 3, 4]
        //      n = 4
        //      0000 => {}
        //      0010 => {2}
        //      0111 => {1,2,3}
        for(int i = 1; i < (1<<n)-1; i++){
            // i will be range from 1 to 2^n-1 (regardless of empty set and the origin set)
            // ex: {} -> {1,2,3,4}
            //     {1,2,3,4} -> {}

            // Store the rule's LHS(left hand side) and RHS(right hand side) in each for loop
            int[] LHS = new int[n];
            int[] RHS = new int[n];
            int indexLHS=0, indexRHS=0;

            // Print current subset
            for(int j = 0; j < n; j++){
                
                if( (i & (1<<j)) > 0){
                    LHS[indexLHS] = arr[j];
                    indexLHS++;
                    // System.out.print(arr[j]+" ");
                }
                else{
                    RHS[indexRHS] = arr[j];
                    indexRHS++;
                }
            }
            
            System.out.print("{ ");
            for(int j = 0; j < indexLHS; j++){
                System.out.print(LHS[j]+" ");
            }
            System.out.print("} --> { ");

            for(int j = 0; j < indexRHS; j++){
                System.out.print(RHS[j]+" ");
            }
            System.out.print("}");
            System.out.println();
        }
    }

    /** print out some messages */
    private void log(String str){
        System.err.println(str);
    }
}
