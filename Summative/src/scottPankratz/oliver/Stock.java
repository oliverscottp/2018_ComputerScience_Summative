package scottPankratz.oliver;

import java.util.Random;

public class Stock {
	private String name;

	private Industry industry;
	private double price;
	private double[] priceHistory = new double[GUIDriver.maxDays];
	private int day;

	private double trend;
	private int daysOfTrend = 0;
	private String endOfTrendMessage;
	private double endOfTrendPriceChange = 0;

	private double nextDaysPriceEvent;

	Random r = new Random();

	/**
	 * Constructor, sets the stocks name and industry
	 * @param name	the stocks desired name
	 * @param industry	the stock desired industry
	 */
	public Stock(String name, Industry industry) {
		day = 0;
		this.name = name;
		this.industry = industry;
		originalPrice();

		trend = 0;

	}

	/**
	 * randomly generates the stocks original price (between 0 and 500)
	 */
	private void originalPrice() {
		price = Math.random() * 500.0;
		priceHistory[day] = price;
	}

	/**
	 * changes the stocks price
	 * @param newPrice	the new price of the stock
	 */
	public void changePrice(double newPrice) {
		price = newPrice;
	}

	/**
	 * sets a stocks trend
	 * @param trend		an up or down percentage for the stock during the trend
	 * @param days		the length of the trend
	 * @param endOfTrendPriceChange		a price change the stock should undergo when the trend finishes
	 */
	public void setTrend(double trend, int days, double endOfTrendPriceChange) {
		this.endOfTrendPriceChange = endOfTrendPriceChange;
		this.trend = trend;
		daysOfTrend = days;
	}

	/**
	 * updates the price for the next day. Events may happen which change the price
	 * differently. 
	 * 
	 * @return Any news that come along with changing prices
	 */
	public String updatePrice() {
		String returnString = null;

		// Determining if an event will happen, these events will start this current day
		if (Math.random() < 0.10 && eventHappening() == false) {
			if (industry == Industry.TECH) {
				returnString = "Secuity flaw found in\n" + name;
				endOfTrendMessage = "The security flaw in " + name + " has been patched";
				trend = -0.04;
				int numDays = r.nextInt(5) + 2;
				daysOfTrend = numDays;
				Portfolio.setStockTrendByIndustry(0.02, industry, numDays, 0, name);

			} else if (industry == Industry.FOOD) {
				if (r.nextInt(2) == 0) {
					returnString = "Disease outbreak!\nmany sick because of " + name;
					endOfTrendMessage = "Food contamination from " + name + " is now under control";
					endOfTrendPriceChange = 0.03;
					trend = -0.04;
					int numDays = r.nextInt(3) + 3;
					daysOfTrend = numDays;
					Portfolio.setStockTrendByIndustry(0.03, industry, numDays, 0, name);
				} else {
					returnString = name + "\nis offering free food to the homeless";
					endOfTrendMessage = "The free food for homeless by\n" + name + " has ended";
					endOfTrendPriceChange = -0.05;
					trend = 0.02;
					int numDays = 7;
					daysOfTrend = numDays;
				}
			} else if (industry == Industry.CLOTHING) {
				returnString = "The CEO of " + name + "\nslipped on a tshirt and broke their neck";
				endOfTrendMessage = "A new CEO for " + name + "\nhas been appointed";
				trend = -0.03;
				int numDays = r.nextInt(3) + 2;
				daysOfTrend = numDays;
				Portfolio.setStockTrendByIndustry(0.01, industry, numDays, 0, name);
			} else if (industry == Industry.DELIVERY) {
				returnString = "A murderer is diguising them self as a " + name + "\nemployee, be careful";
				endOfTrendMessage = "The murderer has been caught\n" + name
						+ " has compensated all the families effected";
				endOfTrendPriceChange = 0.15;
				trend = -0.045;
				int numDays = r.nextInt(8) + 4;
				daysOfTrend = numDays;
			} else if (industry == Industry.FURNITURE) {

			} else if (industry == Industry.VIDEOGAMES) {
				returnString = "A new study has come out showing\na strong link between\nvideogames and animal torture";
				endOfTrendMessage = "The videogame study has\nbeen proven false";
				endOfTrendPriceChange = 0.17;
				trend = -0.04;
				int numDays = r.nextInt(15) + 2;
				Portfolio.setStockTrendByIndustry(-0.04, Industry.VIDEOGAMES, numDays, 0.17, null);
			}
		}

		price = price * ((((double) (r.nextInt(10001))) / 1000 + 95) / 100 + trend);

		/*
		 * if(Math.random() < 0.15 && eventHappening() == false) { if(industry ==
		 * Industry.FURNITURE) }
		 */

		if (daysOfTrend >= 1) {
			daysOfTrend--;
			if (daysOfTrend == 0) {
				trend = 0;
				returnString = endOfTrendMessage;
				endOfTrendMessage = null;
				price *= 1 + endOfTrendPriceChange;
				endOfTrendPriceChange = 0;
			}
		}

		day++;

		if (day <= GUIDriver.maxDays) {
			priceHistory[day] = price;
		}

		return returnString;
	}

	/**
	 * determines if their is an event (or trend) currently happening to the stock
	 * @return	true or false, if an event is or is not happening
	 */
	public boolean eventHappening() {
		if (daysOfTrend != 0) {
			return true;
		}
		return false;
	}

	/**
	 * gets the stock current price
	 * @return	the stocks price
	 */
	public double getPrice() {
		double roundPrice = Math.round(price * 100.0) / 100.0;
		return roundPrice;
	}
	
	/**
	 * gets the stock price at a given day
	 * @param day	the day to get price at
	 * @return		the price on that day
	 */
	public double getPriceAtDay(int day) {
		return priceHistory[day];
	}

	/**
	 * gets the industry the stock is in
	 * @return	the stocks industry
	 */
	public Industry getIndustry() {
		return industry;
	}

	/**
	 * gets the stocks name
	 * @return	the stock name
	 */
	public String getName() {
		return name;
	}

}
