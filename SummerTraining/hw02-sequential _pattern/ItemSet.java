import java.util.ArrayList;

public class ItemSet{
    private ArrayList<String> items;

    public ItemSet(){
        this.items = new ArrayList<String>();
    }

    public ItemSet(String s){
        this.items = new ArrayList<String>();
        this.append(s);
    }

    public ItemSet(ItemSet i){
        this.items = new ArrayList<String>(i.getItems());
    }

    public ItemSet(ArrayList<String> arrStr){
        this.items = new ArrayList<String>();
        this.setItems(arrStr);

    }

    public ItemSet(ItemSet i, String s){
        this.items = new ArrayList<String>(i.getItems());
        this.items.add(s);
    }

    public void setItems(ArrayList<String> items){
        this.items = items;
    }

    public ArrayList<String> getItems(){
        return items;
    }


    /**
     *  return last element of current ItemSet
     *      - in order to deal with the selected frequent item with dash(_)
     *          - getLastItemSet()
     *          - getLastItem() 
     *          - and concatenate it with the selected frequent item
     */
    public String getLastItem(){
        int size = this.items.size();
        return this.items.get(size-1);
    }

    /**
     *  @param key single string without dash(_)
     *  @return the position of searched itemset if success, or return -1
     */
    public int searchSingleItem(ItemSet key){
        for(int i = 0; i < this.items.size(); i++){
            String str = key.getItems().get(0);
            if(str.equals(this.items.get(i))){
                if(i == 0){
                    return i;
                }
                else if(i != 0 && !this.items.get(i-1).equals("_")){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     *  @param key: two strings
     *              - key[0]: _
     *              - key[1]: selected ID
     *  @param prefix  
     *  @return the position of searched itemset if success, or return -1
     */
    public int searchItemWithDash(ItemSet key, Sequence prefix){
        // get the item without dash
        String str = key.getItems().get(1);
        // merge item with the last itemset of current prefix
        ItemSet tmpItems = new ItemSet(prefix.getLastItemSet(), str);
        // concatenated itemset size
        int tmpItemsSize = tmpItems.getItems().size();

        boolean hasDash = false;
        
        if(this.items.size() == 0){
            return -1;
        }

        if("_".equals(this.items.get(0))){
            hasDash = true;
        }

        // Case 1: search _X directly
        for(int i = 0; i < this.items.size(); i++){
            if(hasDash == true && str.equals(this.items.get(i))){
                return i;
            }
        }

        // Case 2: search the concatenated itemset
        int indexConcatItemSet = 0;
        for(int i = 0; i < this.items.size(); i++){
            String str1 = this.items.get(i);
            String str2 = tmpItems.getItems().get(indexConcatItemSet);
            if(str1.equals(str2)){
                indexConcatItemSet++;
            }
            if(indexConcatItemSet == tmpItemsSize){
                return i;
            }
        }

        return -1;
    }

    public void append(String s){
        this.items.add(s);
    }

    public void printItems(){
        System.out.print("( ");
        for(int i = 0; i < items.size(); i++){
            System.out.print(items.get(i) + " ");
        }
        System.out.print(")");
    }
}


