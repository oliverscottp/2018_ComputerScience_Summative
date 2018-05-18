
public class Stock {
	private String name;
	
	private Industry industry;
	private double price;
	private double[][] priceHistory = new double[2][25];
	
	public Stock(String name, Industry industry){
		this.name = name;
		this.industry = industry;
		originalPrice();
	}
	private void originalPrice(){
		price = Math.random() * 500.0;
	}
	
	
	public void changePrice(double newPrice){
		price = newPrice;
	}
	
	public double getPrice(){
		double roundPrice = Math.round(price * 100.0) / 100.0;
		return roundPrice;
	}
	
	public String getName(){
		return name;
	}
}
