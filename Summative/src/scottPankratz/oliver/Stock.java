package scottPankratz.oliver;

import java.util.Random;
/**
 * holds a single stock, the stock will update it's price when is called to go to the next day.
 * Has random events that can happen to it which will change its price up or down for the next
 * random number of days.
 * keeps a price history.
 * can return all needed information of the stock
 * @author Oliver Scott Pankratz
 *
 */
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
	 * 
	 * @param name
	 *            the stocks desired name
	 * @param industry
	 *            the stock desired industry
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
	 * 
	 * @param newPrice
	 *            the new price of the stock
	 */
	public void changePrice(double newPrice) {
		price = newPrice;
	}

	/**
	 * sets a stocks trend
	 * 
	 * @param trend
	 *            an up or down percentage for the stock during the trend
	 * @param days
	 *            the length of the trend
	 * @param endOfTrendPriceChange
	 *            a price change the stock should undergo when the trend finishes
	 */
	public void setTrend(double trend, int days, double endOfTrendPriceChange) {
		this.endOfTrendPriceChange = endOfTrendPriceChange;
		this.trend = trend;
		daysOfTrend = days;
	}

	/**
	 * subtracts a day from the time left in the trend
	 */
	public void subtractTrendDay() {
		if (daysOfTrend >= 1)
			daysOfTrend--;
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
				endOfTrendMessage = "The security flaw in\n" + name + "\nhas been patched";
				trend = -0.04;
				int numDays = r.nextInt(5) + 2;
				daysOfTrend = numDays;
				Portfolio.setStockTrendByIndustry(0.02, industry, numDays, 0, name);

			} else if (industry == Industry.FOOD) {
				if (r.nextInt(2) == 0) {
					returnString = "Disease outbreak!\nmany sick because of\n" + name + "\nfoot lettuce";
					endOfTrendMessage = "Food contamination from\n" + name + "\nis now under control";
					endOfTrendPriceChange = 0.03;
					trend = -0.04;
					int numDays = r.nextInt(3) + 3;
					daysOfTrend = numDays;
					Portfolio.setStockTrendByIndustry(0.03, industry, numDays, 0, name);
				} else {
					returnString = name + "\nis offering free\nfood to the homeless";
					endOfTrendMessage = "The free food for homeless by\n" + name + " has ended";
					endOfTrendPriceChange = -0.05;
					trend = 0.02;
					int numDays = 7;
					daysOfTrend = numDays;
				}
			} else if (industry == Industry.CLOTHING) {
				if (r.nextInt(2) == 0) {
					returnString = "The CEO of " + name + "\nslipped on a tshirt\nand broke their neck";
					endOfTrendMessage = "A new CEO for " + name + "\nhas been appointed";
					trend = -0.03;
					int numDays = r.nextInt(3) + 2;
					daysOfTrend = numDays;
					Portfolio.setStockTrendByIndustry(0.01, industry, numDays, 0, name);
				} else {
					returnString = "UH OH, " + name + "\nhas a shortage of shoelaces\npeople can't tie their shoes!";
					endOfTrendMessage = name + " has found\nthe shoelaces\nan ex-employee stole them all";
					trend = -0.02;
					int numDays = r.nextInt(5) + 3;
					daysOfTrend = numDays;
					Portfolio.setStockTrendByIndustry(0.025, industry, numDays, 0, name);
				}
			} else if (industry == Industry.DELIVERY) {
				int i = r.nextInt(2);
				if (i == 0) {
					returnString = "A murderer is diguising them self as a " + name + "\nemployee, be careful";
					endOfTrendMessage = "The murderer has been caught\n" + name
							+ " has compensated all the families effected";
					endOfTrendPriceChange = 0.17;
					trend = -0.045;
					int numDays = r.nextInt(8) + 4;
					daysOfTrend = numDays;
				} else if (i == 1) {
					returnString = "Fedecks is now givin\nfree puppies through the mail";
					endOfTrendMessage = "Fedecks will no longer\ngive free puppies";
					endOfTrendPriceChange = -0.06;
					trend = 0.025;
					int numDays = r.nextInt(5) + 3;
					daysOfTrend = numDays;
				}
			} else if (industry == Industry.FURNITURE) {
				int i = r.nextInt(2);
				if (i == 0) {
					returnString = name + " has a shortage of\nswedish meatballs! people\nare rioting in the store";
					endOfTrendMessage = name + " has switched to new\nmystery meat, everyone\ngets meatballs!";
					endOfTrendPriceChange = 0.1;
					trend = -0.035;
					int numDays = r.nextInt(3) + 3;
					daysOfTrend = numDays;
				} else if (i == 1) {
					returnString = name + " can now be\nassembled by a robot at your house";
					endOfTrendMessage = "One of the "+name+"\nrobots killed someones\npet pigeon";
					endOfTrendPriceChange = -0.15;
					trend = 0.06;
					int numDays = r.nextInt(2) + 2;
					daysOfTrend = numDays;
				}
			} else if (industry == Industry.VIDEOGAMES) {
				int i = r.nextInt(2);
				if (i == 0) {
					returnString = "A new study has come out showing\na strong link between\nvideogames and animal torture";
					endOfTrendMessage = "The videogame study has\nbeen proven false";
					endOfTrendPriceChange = 0.17;
					trend = -0.04;
					int numDays = r.nextInt(15) + 2;
					Portfolio.setStockTrendByIndustry(-0.04, Industry.VIDEOGAMES, numDays, 0.17, null);
				} else if (i == 1) {
					returnString = name + " is giving away 1 of their\ngames for free!\n Many players are playing and\n buying in game items";
					endOfTrendMessage = name + " no longer is giving away their game for free";
					endOfTrendPriceChange = -0.05;

					trend = 0.04;
					int numDays = r.nextInt(5) + 3;
					daysOfTrend = numDays;
					Portfolio.setStockTrendByIndustry(-0.01, industry, numDays, 0, name);
				}
			}
		}

		price = price * ((((double) (r.nextInt(10001))) / 1000 + 95) / 100 + trend);

	

		if (daysOfTrend == 1) {

			// if (daysOfTrend == 0) {
			trend = 0;
			returnString = endOfTrendMessage;
			endOfTrendMessage = null;
			price *= 1 + endOfTrendPriceChange;
			endOfTrendPriceChange = 0;
			// }
		}

		day++;

		if (day <= GUIDriver.maxDays) {
			priceHistory[day] = price;
		}

		

		return returnString;
	}

	/**
	 * determines if their is an event (or trend) currently happening to the stock
	 * 
	 * @return true or false, if an event is or is not happening
	 */
	public boolean eventHappening() {
		if (daysOfTrend != 0) {
			return true;
		}
		return false;
	}

	/**
	 * gets the stock current price
	 * 
	 * @return the stocks price
	 */
	public double getPrice() {
		double roundPrice = Math.round(price * 100.0) / 100.0;
		return roundPrice;
	}

	/**
	 * gets the stock price at a given day
	 * 
	 * @param day
	 *            the day to get price at
	 * @return the price on that day
	 */
	public double getPriceAtDay(int day) {
		return priceHistory[day];
	}

	/**
	 * gets the industry the stock is in
	 * 
	 * @return the stocks industry
	 */
	public Industry getIndustry() {
		return industry;
	}

	/**
	 * gets the stocks name
	 * 
	 * @return the stock name
	 */
	public String getName() {
		return name;
	}

}
