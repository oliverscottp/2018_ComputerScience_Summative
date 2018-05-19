
public class Stock {
	private String name;
	
	private Industry industry;
	private double price;
	private double[] priceHistory = new double[25];
	private int day;
	
	public Stock(String name, Industry industry){
		day = 0;
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
	
	public void updatePrice(){
		priceHistory[day] = price;
		price = price * ((Math.random() * 10 + 95)/100);
		day++;
		for(int i = 0 ; i < 25 ; i++){
			System.out.println(priceHistory[i]);
		}
	}
	
	public double getPrice(){
		double roundPrice = Math.round(price * 100.0) / 100.0;
		return roundPrice;
	}
	
	public String getName(){
		return name;
	}
}
