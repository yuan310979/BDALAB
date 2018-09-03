import java.util.ArrayList;

public class Point{
	private double[] data;
	private int flag;

	public Point(double[] d, int flag){
		data = new double[d.length];
		for(int i = 0; i < d.length; i++){
			this.data[i] = d[i];
		}
		this.flag = flag;
	}

	public double[] getData(){
		return data;
	}

	public int getFlag(){
		return flag;
	}

	public static double getEulcliDistance(Point p1, Point p2){
		int size1 = p1.getData().length;
		int size2 = p2.getData().length;
		assert(size1 == size2);

		double sum = 0d;
		for(int i = 0; i < size1; i++){
			sum = sum + Math.pow((p1.getData()[i] - p2.getData()[i]), 2);
		}

		return sum;
	}

	public void printPoint(){
		System.out.print("Point: ( ");
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i] + " ");
		}
		System.out.println(")");
	}
}