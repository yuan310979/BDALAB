import java.util.ArrayList;

/**
	@param 
*/

public class DBSCAN{
	/** name of input dataset */
	private String fileName;

	/** maximum radius to consider point as core */
	private double eps;

	/** the number of which a core point should contain other points */
	private int minPts;

	public DBSCAN(String fileName, double eps, int minPts) throws DBSCANException{
		setFileName(fileName);
		setEps(eps);
		setMinPts(minPts);
	}

	public void setFileName(String fileName) throws DBSCANException{
		if(fileName == null){
			throw new DBSCANException("filename can not be empty!");
		}
		this.fileName = fileName;
	}

	public void setEps(Double eps){
		this.eps = eps;
	}

	public void setMinPts(int minPts){
		this.minPts = minPts;
	}

	public void log(String str){
		System.out.println("DBSCAN: " + str);
	}
}