import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.HashSet;
import java.util.Collections;

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

	/** DBSCAN constant */
	private static final int CORE = 0;
	private static final int BORDER = 1;
	private static final int NOISE = 2;

	/** points which has been visited already */
	private HashSet<Integer> visitedPoints;

	/** result of DBSCAN  */
	private ArrayList<ArrayList<Integer>> clusteringResult;

	public DBSCAN(Metric metric, double eps, int minPts) throws DBSCANException{
		setMetric(metric);
		setEps(eps);
		setMinPts(minPts);
		visitedPoints = new HashSet<Integer>();
		clusteringResult = new ArrayList<ArrayList<Integer>>();
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

	public ArrayList<ArrayList<Integer>> getResult(){
		return this.clusteringResult;
	}

	public void run(){
		// number of points
		int size = metric.getMetric().get(0).size();

		for(int i = 0; i < size; i++){
			if(!visitedPoints.contains(i)){
				// neighbors will grow while the following while loop
				// search by BFS method
				visitedPoints.add(i);
				ArrayList<Integer> neighbors = getNeighbors(i);
				if(neighbors.size() >= minPts){
					int ind = 0;
					while(ind < neighbors.size()){
						int qIndex = neighbors.get(ind);
						if(!visitedPoints.contains(qIndex)){
							visitedPoints.add(qIndex);
							ArrayList<Integer> qNeighbor = getNeighbors(qIndex);
							if(qNeighbor.size() >= minPts){
								neighbors = mergeNeighbors(neighbors, qNeighbor);
							}
						}	
						ind++;	
					}
					Collections.sort(neighbors);
					clusteringResult.add(neighbors);
				}	
			}
		}
	}

	public ArrayList<Integer> mergeNeighbors(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
		for(int i = 0; i < arr2.size(); i++){
			if(!arr1.contains(arr2.get(i))){
				arr1.add(arr2.get(i));
			}
		}
		return arr1;
	}

	/**
		Type:	0 => core 	(point which contains more than minPts points in eps radius)
				1 => border (point which contains in some core points radius range, but not contains much points in eps radius itself.)
				2 => noise 	(point which is neither core nor border)
	*/
	public int getPointType(int index){
		int count = 0;
		ArrayList<Double> row = metric.getMetric().get(index);
		for(int i = 0; i < row.size(); i++){
			if(i != index && row.get(i) <= eps){
				count++;
			}
		}
		if(count >= minPts){
			return CORE;
		}
		else
			return -1;
	}

	/**
		calculate how many neighbors(in the radius of eps)
		does the point which indexed by index have
	*/
	public ArrayList<Integer> getNeighbors(int index){
		ArrayList<Integer> tmpArr = new ArrayList<Integer>();
		ArrayList<Double> row = metric.getMetric().get(index);
		for(int i = 0; i < row.size(); i++){
			if(i != index && row.get(i) <= eps){
				tmpArr.add(i);
			}
		}

		return tmpArr;
	}

	public void printResult(){
		System.out.println("=================== DBSCAN Clustering ===================");
		for(int i = 0; i < clusteringResult.size(); i++){
			ArrayList<Integer> cluster = clusteringResult.get(i);
			System.out.println("Cluster " + i);
			for(int j = 0; j < cluster.size(); j++){
				System.out.print(cluster.get(j) + " ");
			}
			System.out.println();
		}
		System.out.println("=========================================================");
	}

	public void log(String str){
		System.out.println("DBSCAN: " + str);
	}
}