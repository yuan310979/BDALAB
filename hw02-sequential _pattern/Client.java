public class Client{
    public static void main(String[] args){
        PrefixSpan ps = new PrefixSpan(args[0], args[1]);
        ps.run();
    }
}
