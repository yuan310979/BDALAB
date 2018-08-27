/**
	This class is used to record the maximum usage of memory while executing this algorithm.
	It is implemented as "singleton" design pattern.
*/

/**
	Singleton Pattern:
	In software engineering, the singleton pattern is a software design pattern that restricts the instantiation of a class to one object.
*/

public class MemoryLogger{
	// restrict outer class to initiate Memory Logger
	private static MemoryLogger instance = new MemoryLogger();

	// variable to store maximum memory usage
	public static double maxMemory = 0;

	public static MemoryLogger getInstance(){
		return instance;
	}

	public static double getMaxMemory(){
		return maxMemory;
	}

	/**
		Reset the recorded maximum of memory usasge.
	*/
	public static void reset(){
		maxMemory = 0;
	}


	/**
		Check the current meory usage and record it if higher than previous record.
	*/
	public void checkMemory(){
		double currentMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024d / 1024d;
	}
}