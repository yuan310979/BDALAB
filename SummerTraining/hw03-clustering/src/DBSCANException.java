/**
	Exception class to handle error about DBSCAN
*/

public class DBSCANException extends Exception{
	public DBSCANException(String str){
		super("DBSCAN: " + str);
	}
}