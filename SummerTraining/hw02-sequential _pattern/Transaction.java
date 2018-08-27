public class Transaction{
    private String SID;
    private String TID;
    private String ITEM;

    public Transaction(String SID, String TID, String ITEM){
        this.SID = SID;
        this.TID = TID;
        this.ITEM = ITEM;
    }

    public String getSID(){
        return this.SID;
    } 

    public String getTID(){
        return this.TID;
    }

    public String getITEM(){
        return this.ITEM;
    }

    public void printTrans(){
        System.out.println(this.SID + " " + this.TID + " " + this.ITEM);
    }
}
