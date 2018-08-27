import java.util.ArrayList;

public class Sequence{
    private ArrayList<ItemSet> itemSetList;
    /* index of ItemSet in Sequence which is already to remove */
    private int indexItemset;
    /* index of Item in ItemSet which is already to remove */
    private int indexItem;

    public Sequence(){
        this.itemSetList = new ArrayList<ItemSet>(); 
        this.indexItemset = -1;
        this.indexItem = -1;
    }

    public Sequence(ItemSet i){
        this.itemSetList = new ArrayList<ItemSet>();
        itemSetList.add(i);
        this.indexItemset = -1;
        this.indexItem = -1;
    }

    public void setSequence(ArrayList<ItemSet> s){
        this.itemSetList = s;
    } 

    public ArrayList<ItemSet> getSequence(){
        return this.itemSetList;
    }

    public ItemSet getLastItemSet(){
        int size = this.itemSetList.size();
        return this.itemSetList.get(size-1);
    }

    /**
     *  - search wether it contains or not
     *  - set index of which itemset in sequence and which item in itemset 
     *
     *  @param key : the single item wanted to search
     *  @param prefix : current prefix
     *                  used for concatenated when key contains dash(_)
     */
    public boolean search(ItemSet key, Sequence prefix){
        this.indexItemset = -1;
        this.indexItem = -1;
        int size = key.getItems().size();
        // searched key size should be 1 or 2 (contained dash or not)
        assert size == 1 || size == 2;
        int found = -1;
        if(size == 1){
            String item = key.getItems().get(0);
            for(int i = 0; i < this.itemSetList.size(); i++){
                ItemSet is = this.itemSetList.get(i);
                found = is.searchSingleItem(key);
                if(found != -1){
                    this.indexItemset = i;
                    this.indexItem = found;
                    return true;
                }
            }
        }
        else{
            for(int i = 0; i < this.itemSetList.size(); i++){
                ItemSet is = this.itemSetList.get(i);
                found = is.searchItemWithDash(key, prefix);
                if(found != -1){
                    this.indexItemset = i;
                    this.indexItem = found;
                    return true;
                }
            }
        }
        return false;
    }

    public void append(ItemSet i){
        this.itemSetList.add(i);
    }

    /**
     *  @param cand : candidate itemset to be inserted to prefix
     *              size=1 ex: a
     *              size=2 ex: _a
     */
    public Sequence cloneAndMergeWithNewCand(ItemSet cand){
        // copy new sequence
        Sequence seq = this.copySequence();
        int candSize = cand.getItems().size();
        assert(candSize == 1 || candSize == 2);
        if(candSize == 1){
            seq.append(cand);
        }
        else{
            seq.getLastItemSet().append(cand.getItems().get(1));
        }

        return seq;
    }

    public Sequence copySequence(){
        Sequence copySeq = new Sequence();
        ItemSet tempItemSet;
        
        for(ItemSet i : this.itemSetList){
            ArrayList<String> items = (ArrayList<String>) i.getItems().clone();
            tempItemSet = new ItemSet(items);
            copySeq.append(tempItemSet);
        }

        return copySeq;
    }

    public Sequence getProjectedSequence(){
        Sequence tmpS = new Sequence();
        //System.out.println(""+this.indexItemset+" "+this.indexItem);
        
        if(indexItemset == -1)
            return tmpS;
        else{
            // selI : itemset which contains selected item
            ItemSet selI = this.itemSetList.get(indexItemset);
            ItemSet tmpI = new ItemSet();
            // itemset contains search item but not at the last position
            if(indexItem < selI.getItems().size()-1){
                tmpI.append("_");
                for(int i = indexItem+1; i < selI.getItems().size(); i++){
                    tmpI.append(selI.getItems().get(i));
                }
                tmpS.append(tmpI);
            }
            for(int i = indexItemset+1; i < this.itemSetList.size(); i++){
                tmpI = new ItemSet(this.itemSetList.get(i));
                tmpS.append(tmpI);
            }
        }

        return tmpS;
    }

    public void deleteSingleItem(String s){
        ArrayList<String> deleteItem = new ArrayList<String>();
        deleteItem.add(s);

        for(ItemSet i : this.itemSetList){
            ArrayList<String> tempItems = i.getItems();
            tempItems.removeAll(deleteItem);
        }
    }

    public void printSequence(){
        for(ItemSet i : this.itemSetList){
            i.printItems();
        }
        System.out.println();
    }
}
