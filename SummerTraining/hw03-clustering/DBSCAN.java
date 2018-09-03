import java.util.ArrayList;

/**
	@param 
*/

public class DBSCAN{
	/** metric of each two points' distance */
	private Metric metric;

	/** maximum radius to consider point as core */
	private double eps;

	/** the number of which a core point should contain other points */
	private int minPts;

	public DBSCAN(Metric metric, double eps, int minPts) throws DBSCANException{
		setMetric(metric);
		setEps(eps);
		setMinPts(minPts);
	}

	public void setMetric(Metric metric) throws DBSCANException{
		if(metric == null){
			throw new DBSCANException("metric can not be empty!");
		}
		this.metric = metric;
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