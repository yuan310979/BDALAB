import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class Database{
    private ArrayList<Sequence> sequenceList;
    private ArrayList<String> singleItems;
    private double minSupportRate;

    public Database(){
        this.sequenceList = new ArrayList<Sequence>();
        this.singleItems = new ArrayList<String>();
    }

    public Database(double minSupportRate){
        this.sequenceList = new ArrayList<Sequence>();
        this.singleItems = new ArrayList<String>();
        this.minSupportRate = minSupportRate;
    }

    public Database(ArrayList<Sequence> l){
        this.sequenceList = new ArrayList<Sequence>(l);
        this.singleItems = new ArrayList<String>();
    }

    public ArrayList<Sequence> getDatabase(){
        return this.sequenceList;
    }

    public ArrayList<String> getInitSingleItems(){
        return this.singleItems;
    }

    /**
     *  import dataset from
     *  "SID TID ITEM"
     *  to
     *  each Sequence(each sequence represent each SID)
     *
     *  @param trans : list of Transaction read from dataset
     */
    public void importDatabaseByTrans(ArrayList<Transaction> trans){
        String preSID = trans.get(0).getSID();
        String preTID = trans.get(0).getTID(); 
        ItemSet tmpItemSet = new ItemSet();
        Sequence tmpSequence = new Sequence();
        tmpItemSet.append(trans.get(0).getITEM());

        trans.remove(0);
        for(Transaction t : trans){
            if(preSID.compareTo(t.getSID()) == 0){
                if(preTID.compareTo(t.getTID()) == 0){
                    tmpItemSet.append(t.getITEM());
                }
                else{
                    tmpSequence.append(tmpItemSet);
                    tmpItemSet = new ItemSet();
                    tmpItemSet.append(t.getITEM());
                }
            }
            else{
                tmpSequence.append(tmpItemSet);
                this.sequenceList.add(tmpSequence);
                tmpSequence = new Sequence();
                tmpItemSet = new ItemSet();
                tmpItemSet.append(t.getITEM());
            }

            preSID = t.getSID();
            preTID = t.getTID();
        }
        tmpSequence.append(tmpItemSet); 
        this.sequenceList.add(tmpSequence);
    }

    public void removeItemLessThanMinSup(){
        HashMap<String, Integer> itemMap = new HashMap<>();
        int minSupport = (int) (this.sequenceList.size() * this.minSupportRate);
        
        for(Sequence seq : this.sequenceList){
            for(ItemSet i : seq.getSequence()){
                for(String s : i.getItems()){
                    if(!itemMap.containsKey(s)){
                        itemMap.put(s, 1);
                    }
                }
            }
        }

        for(Map.Entry entry : itemMap.entrySet()){
            int count = 0;
            String key = (String) entry.getKey();
            ItemSet k = new ItemSet(key);
            for(Sequence seq : this.sequenceList){
                if(seq.search(k, null) == true)
                    count++;
            }

            itemMap.put(key, count);
        }

        for(Map.Entry entry : itemMap.entrySet()){
            String key = (String) entry.getKey();
            int count = (int) entry.getValue();

            if(count < minSupport){
                for(Sequence seq: this.sequenceList){
                    seq.deleteSingleItem(key);
                }
                continue;
            }
            else{
                this.singleItems.add(key);        
            }
        }

        Collections.sort(singleItems, new Comparator(){
            public int compare(Object o1, Object o2){
                Integer i1 = Integer.parseInt((String) o1);
                Integer i2 = Integer.parseInt((String) o2);
                return i1.compareTo(i2);
            }
        });
    }

    public Database getProjectedDatabase(ItemSet i, Sequence prefix){
        ArrayList<Sequence> tmpSequenceList = new ArrayList<Sequence>();
        for(Sequence seq : this.sequenceList){
            seq.search(i, prefix);
            tmpSequenceList.add(seq.getProjectedSequence());
        }

        Database pd = new Database(tmpSequenceList);

        return pd;
    }

    public ArrayList<ItemSet> getNextCandidate(Sequence prefix){
        ArrayList<ItemSet> cand = new ArrayList<ItemSet>();
        ItemSet tmpItemSet;
        HashMap<ItemSet, Integer> itemMap = new HashMap<>();
        int minSupport = (int) (this.sequenceList.size() * this.minSupportRate);

        for(Sequence seq : this.sequenceList){
            for(ItemSet i : seq.getSequence()){
                boolean hasDash = false;
                for(String str : i.getItems()){
                    if(str.equals("_")){
                        hasDash = true;
                    }
                    else{
                        tmpItemSet = new ItemSet();
                        if(hasDash == true){
                            tmpItemSet.append("_");
                        }
                        tmpItemSet.append(str);
                        if(!itemMap.containsKey(tmpItemSet)){
                            itemMap.put(tmpItemSet, 1);
                        }
                    }
                }
            }
        }

        for(Map.Entry entry : itemMap.entrySet()){
            int count = 0;
            ItemSet key = (ItemSet) entry.getKey();
            for(Sequence seq : this.sequenceList){
                if(seq.search(key, prefix) == true)
                    count++;
            }

            itemMap.put(key, count);
        }

        for(Map.Entry entry : itemMap.entrySet()){
            ItemSet key = (ItemSet) entry.getKey();
            int count = (int) entry.getValue();

            if(count >= minSupport){
                cand.add(key);     
            }
        }

        return cand;
    }

    public void printDatabase(){
        for(Sequence seq : sequenceList){
            seq.printSequence();
        }
    }
}
