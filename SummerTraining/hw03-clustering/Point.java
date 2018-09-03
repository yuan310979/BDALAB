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

	public void printPoint(){
		System.out.print("Point: ( ");
		for(int i = 0; i < data.length; i++){
			System.out.print(data[i] + " ");
		}
		System.out.println(")");
	}
}