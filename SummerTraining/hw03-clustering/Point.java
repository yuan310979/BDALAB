import java.util.ArrayList;

public class Point{
	private double[] data;

	public Point(double[] d){
		data = new double[d.length];
		for(int i = 0; i < d.length; i++){
			data[i] = d[i];
		}
	}

	public void printPoint(){
		System.out.print("Point: ( ");
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i] + " ");
		}
		System.out.println(")");
	}
}