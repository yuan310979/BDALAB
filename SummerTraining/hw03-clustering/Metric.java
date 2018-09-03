import java.util.ArrayList;

public class Metric{
	private ArrayList<ArrayList<Double>> metric;

	public Metric(){
		this.metric = new ArrayList<ArrayList<Double>>();
	}

	public Metric(ArrayList<ArrayList<Double>> metric){
		this.metric = metric;
	}

	public ArrayList<ArrayList<Double>> getMetric(){
		return metric;
	}

	public static Metric getDistanceMetricFromPoints(ArrayList<Point> arrPts){
		ArrayList<ArrayList<Double>> tmpMetric = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < arrPts.size(); i++){
			ArrayList<Double> tmpRow = new ArrayList<Double>();
			for(int j = 0; j < arrPts.size(); j++){
				tmpRow.add(Point.getEulcliDistance(arrPts.get(i), arrPts.get(j)));
			}
			tmpMetric.add(tmpRow);
		}

		Metric m = new Metric(tmpMetric);
		return m;
	}

	public void printMetric(){
		System.out.print("[");
		for(int i = 0; i < metric.get(0).size(); i++){
			ArrayList<Double> row = metric.get(i);
			System.out.print("[ ");
			for(int j = 0; j < row.size(); j++){
				System.out.print(Math.round(row.get(j)*100d)/100d + " ");
			}
			System.out.println("]");
		}
		System.out.println("]");
	}
}