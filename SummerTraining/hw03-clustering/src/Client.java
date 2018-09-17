import java.util.ArrayList;

public class Client{
	public static void main(String[] args) throws DBSCANException{
		double eps = 1;
		int minPts = 10;

		if(args.length > 3){
			log("More parameter than expected. (should be less than 3)");
			throw new DBSCANException("Usage: java client <fileName> [eps] [minPts]");
		}
		else if(args.length == 3){
			eps = Double.parseDouble(args[1]);
			minPts = Integer.parseInt(args[2]);
		}
		else if(args.length == 2){
			log("Parameter minPts is null. Auto set it to 10.");
			eps = Double.parseDouble(args[1]);
		}
		else if(args.length == 1){
			log("Parameter eps is null. Auto set it to 1");
			log("Parameter minPts is null. Auto set it to 10.");
		}
		else if(args.length == 0){
			throw new DBSCANException("Parameter fileName should not be empty!");
		}

		FileIO io = new FileIO(args[0]);
		ArrayList<Point> pointArr = io.readFileFromDatasetAndGenPointArr();
		Metric metric  = Metric.getDistanceMetricFromPoints(pointArr);

		DBSCAN algo = new DBSCAN(metric, eps, minPts, pointArr);
		algo.run();
		algo.printResult();
		algo.drawResult();
	}

	public static void log(String str){
		System.out.println("DBSCAN: " + str);
	}
}