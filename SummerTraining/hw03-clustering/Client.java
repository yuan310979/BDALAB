import java.util.ArrayList;

public class Client{
	public static void main(String[] args) throws DBSCANException{
		double eps;
		int minPts;

		if(args.length > 3){
			log("More parameter than expected. (should be less than 3)");
			throw new DBSCANException("Usage: java client <fileName> [eps] [minPts]");
		}
		else if(args.length == 2){
			log("Parameter minPts is null. Auto set it to 10.");
			minPts = 10;
			eps = Double.parseDouble(args[1]);
		}
		else if(args.length == 1){
			log("Parameter eps is null. Auto set it to 1");
			log("Parameter minPts is null. Auto set it to 10.");
			minPts = 10;
			eps = 1;;
		}
		else{
			throw new DBSCANException("Parameter fileName should not be empty!");
		}

		FileIO io = new FileIO(args[0]);
		ArrayList<Point> pointArr = io.readFileFromDatasetAndGenPointArr();
		Metric metric  = Metric.getDistanceMetricFromPoints(pointArr);
		
		DBSCAN algo = new DBSCAN(metric, eps, minPts);
	}

	public static void log(String str){
		System.out.println("DBSCAN: " + str);
	}
}