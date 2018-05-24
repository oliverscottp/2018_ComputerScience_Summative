package scottPankratz.oliver;

import java.util.Random;

public class Stock {
	private String name;

	private Industry industry;
	private double price;
	public double[] priceHistory = new double[GUIDriver.maxDays];
	private int day;

	private double trend;

	private double nextDaysPriceEvent;

	private boolean priceHasChanged = false;
	
	Random r = new Random();
	

	public Stock(String name, Industry industry) {

		day = 0;
		this.name = name;
		this.industry = industry;
		originalPrice();

		trend = 0;
		if (Math.random() > 0.5) {
			// trend = 0.02;
		} else {
			// trend = -0.02;
		}
	}

	private void originalPrice() {
		price = Math.random() * 500.0;
		priceHistory[day] = price;
	}

	public void changePrice(double newPrice) {
		price = newPrice;
		priceHasChanged = true;
	}
	
	public void changePrice(double newPrice, double trend, int days){
		
	}

	/**
	 * updates the price for the next day. Events may happen which change the
	 * price differently
	 * 
	 * @return Any news that come along with changing prices
	 */

	public String updatePrice(int day) {
		String returnString = null;

		if (priceHasChanged == false) {
			
			if (nextDaysPriceEvent != 0.0) {
				price = nextDaysPriceEvent;
				nextDaysPriceEvent = 0.0;
			} else {
				price = price * (((Math.random()) * 10 + 95) / 100 + trend);
			}

			// Determining if an event will happen
			if (Math.random() < 0.25) {
				nextDaysPriceEvent = price * (((Math.random()) * 100 + 50) / 100 + trend);
				if (nextDaysPriceEvent < price)
					returnString = name + " will fall";
				if (nextDaysPriceEvent >= price)
					returnString = name + " will go up";
			}

			day++;

			if (day <= GUIDriver.maxDays) {
				priceHistory[day] = price;
			}
		}
		priceHasChanged = false;

		return returnString;
	}

	public double getPrice() {
		double roundPrice = Math.round(price * 100.0) / 100.0;
		return roundPrice;
	}

	public String getName() {
		return name;
	}
	
	public Industry getIndustry(){
		return industry;
	}

}
