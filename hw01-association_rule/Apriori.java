import java.io.BufferedReader;
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
        
    }
}

