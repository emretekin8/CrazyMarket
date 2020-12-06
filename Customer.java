import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Customer {

	private static String formatText = "%-15s %-15s %-15s %-15s\n";

	public static List<Customer> customerList = new ArrayList<Customer>();

	int id;
	// datafield tiplerini degistirebilirsiniz
	double arrivalTime; // musteri gelis zamani-
	// bekleme zamanini hesaplamada kullanabilirsiniz
	double removalTime;
	// Process Time
	double processTime;

	Customer(int id) {
		this.id = id;
		this.arrivalTime = getArrivalTime();
		this.processTime = getProcessTime();
		customerList.add(this);
	}

	public String format(double d) {

		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		return decimalFormat.format(d);
	}

	public static void printHead() {
		System.out.printf(formatText, "ID", "Arrival Time", "Removal Time", "Process Time");
	}

	public void printItem() {
		System.out.printf(formatText, this.id, format(this.arrivalTime), format(this.removalTime),
				format(this.processTime));
	}

	private double getArrivalTime() {
		return Math.random() * 2; // Gives a number between [0, 2]
	}

	private double getProcessTime() {
		return (Math.random() * 2) + 1; // Gives a number between [1, 3]
	}
}
