import javafx.util.Pair;

public class Stock {
	private String name;

	private Industry industry;
	private double price;
	public double[] priceHistory = new double[GUIDriver.maxDays];
	private int day;

	private double nextDaysPriceEvent;

	public Stock(String name, Industry industry) {
		day = 0;
		this.name = name;
		this.industry = industry;
		originalPrice();
	}

	private void originalPrice() {
		price = Math.random() * 500.0;
		priceHistory[day] = price;
	}

	public void changePrice(double newPrice) {
		price = newPrice;
	}

	/**
	 * updates the price for the next day. Events may happen which change the price differently
	 * @return	Any news that come along with changing prices
	 */
	public String updatePrice() {
		String returnString = null;

		if (nextDaysPriceEvent != 0.0) {
			price = nextDaysPriceEvent;
			nextDaysPriceEvent = 0.0;
		} else {
			price = price * ((Math.random() * 10 + 95) / 100);
		}

		//Determining if an event will happen
		if (Math.random() < 0.25) {
			nextDaysPriceEvent = price * ((Math.random() * 100 + 50) / 100);
			if(nextDaysPriceEvent < price)
				returnString = name +" will fall";
			if(nextDaysPriceEvent >= price)
				returnString = name +" will go up";
		}

		day++;

		if (day <= GUIDriver.maxDays) {
			priceHistory[day] = price;
		}


		
		
		return returnString;
	}

	public double getPrice() {
		double roundPrice = Math.round(price * 100.0) / 100.0;
		return roundPrice;
	}

	public String getName() {
		return name;
	}
	

}
