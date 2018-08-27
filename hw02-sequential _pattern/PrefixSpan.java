import java.util.ArrayList;

public class PrefixSpan{
    /**
     *  Usage: java PrefixSpan [datasetFilename] [minSupportRate]
     *
     *  @param args[0] datasetFilename
     *  @param args[1] minSupportRate
     */

    private String datasetFilename;
    private double minSupportRate;

    private ArrayList<Sequence> sequentialPattern;
    private int totalPattern;

    public PrefixSpan(String datasetFilename, String minSupportRate){
        this.datasetFilename = datasetFilename;
        this.minSupportRate = Double.parseDouble(minSupportRate);
        this.sequentialPattern = new ArrayList<Sequence>();
        this.totalPattern = 0;
    }

    public void run(){
        
        long startTime = System.currentTimeMillis();

        FileIO io = new FileIO(this.datasetFilename);
        ArrayList<Transaction> listOfTrans = io.readFileFromTransDatabase();

        Database db = new Database(this.minSupportRate);
        db.importDatabaseByTrans(listOfTrans);
        // this function will remove single items which support is less than minSupport
        // and set singleItems to db
        db.removeItemLessThanMinSup();
        //db.printDatabase();

        ArrayList<String> frequentInitSingleItem = db.getInitSingleItems();
        
        for(String str : frequentInitSingleItem){
            ItemSet i = new ItemSet(str);

            // Initialize prefix as Sequence
            Sequence prefix = new Sequence(new ItemSet(str));

            // get projected database by item which is selected
            Database pd = db.getProjectedDatabase(i, null);
            sequentialPattern.add(prefix);
            recursiveSearch(prefix, pd);
        }
        
        totalPattern = totalPattern + frequentInitSingleItem.size();
        
        long endTime = System.currentTimeMillis();

        String fileName = String.format("%s_%f.txt", this.datasetFilename, this.minSupportRate);
        io.printSequentialPattern(sequentialPattern, fileName);
        System.out.println("Time: " + (endTime-startTime)/1000.0 + " sec");
        System.out.println("Total of Sequence: " + totalPattern);
    }

    public void recursiveSearch(Sequence prefix, Database pd){
        ArrayList<ItemSet> cands = pd.getNextCandidate(prefix);
        for(ItemSet cand : cands){
            Sequence newPrefix = prefix.cloneAndMergeWithNewCand(cand);
            Database newPD = pd.getProjectedDatabase(cand, prefix);
            sequentialPattern.add(newPrefix);
            recursiveSearch(newPrefix, newPD);
        }
        totalPattern = totalPattern + cands.size();
    }
}
