package scottPankratz.oliver;

public class Stock {
	private String name;

	private Industry industry;
	private double price;
	public double[] priceHistory = new double[GUIDriver.maxDays];
	private int day;
	
	private double trend;

	private double nextDaysPriceEvent;

	public Stock(String name, Industry industry) {
		day = 0;
		this.name = name;
		this.industry = industry;
		originalPrice();
		
		trend = 0;
		if(Math.random() >0.5) {
			//trend = 0.02;
		}
		else {
			//trend = -0.02;
		}
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
			price = price * (((Math.random()+0.05) * 10 + 95) / 100 +trend);
		}

		//Determining if an event will happen
		if (Math.random() < 0.25) {
			nextDaysPriceEvent = price * (((Math.random()+0.05) * 100 + 50) / 100+trend);
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
