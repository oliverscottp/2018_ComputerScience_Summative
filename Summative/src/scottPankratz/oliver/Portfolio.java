package scottPankratz.oliver;

import java.util.ArrayList;

public class Portfolio {
	static int numOfDifferentStocks = 10;
	private static Stock[] stocks = new Stock[numOfDifferentStocks];
	private int[] numOfStock = new int[numOfDifferentStocks];
	private double money;

	public Portfolio(double startingMoney) {
		money = startingMoney;
		generateStocks();
		for (int i = 0; i < numOfStock.length; i++) {
			numOfStock[i] = 0;
		}
	}

	/**
	 * generates all the names and industries of the 10 stocks.
	 */
	private void generateStocks() {
		stocks[0] = new Stock("Bepis Cola Inc", Industry.FOOD);
		stocks[1] = new Stock("Appricot Computing", Industry.TECH);
		stocks[2] = new Stock("Boblaws", Industry.FOOD);
		stocks[3] = new Stock("Ikua Furniture", Industry.FURNITURE);
		stocks[4] = new Stock("American Beagle", Industry.CLOTHING);
		stocks[5] = new Stock("Ubysoft", Industry.VIDEOGAMES);
		stocks[6] = new Stock("Fedecks", Industry.DELIVERY);
		stocks[7] = new Stock("Ubber", Industry.TECH);
		stocks[8] = new Stock("Nintenbo", Industry.VIDEOGAMES);
		stocks[9] = new Stock("Old Gravy", Industry.CLOTHING);

	}

	/**
	 * Buys the given number of stock at the given price
	 * 
	 * @param stockNum
	 *            The stock index number
	 * @param amtOfStock
	 *            The amount of stock they want to buy
	 * @return Returns "Stock bought" if they have enough money, returns
	 *         "invalid" if they dont.
	 */
	public String buyStock(int stockNum, int amtOfStock) {
		if (stocks[stockNum].getPrice() * amtOfStock > money) {
			return "Invalid Amount Of Money";
		} else {
			money -= stocks[stockNum].getPrice() * amtOfStock;
			numOfStock[stockNum] += amtOfStock;
			return "Stock bought";

		}

	}

	/**
	 * Sells the given amount of stock at the stocks price
	 * 
	 * @param stockNum
	 *            The stock index number
	 * @param amtOfStock
	 *            The amount of stock they want to sell
	 * @return Returns sold if they have enough stock, returns invalid amount if
	 *         they dont have enough
	 */
	public String sellStock(int stockNum, int amtOfStock) {
		if (numOfStock[stockNum] < amtOfStock) {
			return "Invalid Amount Of Stock";
		} else {
			money += stocks[stockNum].getPrice() * amtOfStock;
			numOfStock[stockNum] -= amtOfStock;
			return "Stock Sold";

		}
	}

	public String[] goToNextDay() {
		ArrayList<String> messages = new ArrayList<String>();
		for (int i = 0; i < stocks.length; i++) {
			String temp = stocks[i].updatePrice();
			if (temp != null) {
				messages.add(temp);
			}
		}

		String[] messageArray = new String[messages.size()];
		messages.toArray(messageArray);
		
		return messageArray;
	}

	/**
	 * Returns stock name given the stock index number
	 * 
	 * @param stockNumber
	 *            The index number for the stock in the array
	 * @return The string (name) associated with the stock
	 */
	public String getStockName(int stockNumber) {
		return stocks[stockNumber].getName();
	}

	/**
	 * Returns the money to 2 decimal places
	 * 
	 * @return amount of money, 2 decimal places
	 */
	public double getMoney() {
		double roundMoney = Math.round(money * 100.0) / 100.0;
		return roundMoney;
	}

	/**
	 * gets the price of a given stock at a given date
	 * @param stockIndex	the index of the stock
	 * @param day	the day you want the price
	 * @return	The price of the stock at that day
	 */
	public double getPriceAtDate(int stockIndex, int day) {
		return stocks[stockIndex].getPriceAtDay(day);
	}

	/**
	 * calculates the total worth of the account, which includes money and the value of all stocks owned
	 * @return
	 */
	public double totalWorth() {
		double rounded = money;

		for (int i = 0; i < numOfStock.length; i++) {
			rounded += stocks[i].getPrice() * numOfStock[i];
		}
		rounded = Math.round(rounded * 100.0) / 100.0;
		return rounded;

	}
	
	/**
	 * gets the stocks current price
	 * @param i	the index of the desired stock
	 * @return	The current price of the stock
	 */
	public double getPrice(int i) {
		return stocks[i].getPrice();
	}
	
	/**
	 * gets the number of stock owned
	 * @param i		the index of the stock
	 * @return	The number of stock that is owned
	 */
	public int getNumberOfStock(int i) {
		return numOfStock[i];
	}
	
	/**
	 * sets the trend for all stocks in a desired industry
	 * @param trend		the trend of the stock (+ or - a decimal) to make the stock have a higher chance of going up or down
	 * @param ind		the industry to set the trend for all desired stock
	 * @param days		the number of days the trend will last for
	 * @param endOfTrendPriceChange		an up or down price change that the stock will get at the end of the trend
	 * @param exclude	the string of any stock in the industry to not include
	 */
	public static void setStockTrendByIndustry(double trend, Industry ind, int days, double endOfTrendPriceChange,String exclude) {
		for(int i = 0; i < stocks.length; i++) {
			if(stocks[i].getIndustry() == ind && stocks[i].getName() != exclude && stocks[i].eventHappening() == false) {
				stocks[i].setTrend(trend, days,endOfTrendPriceChange);
			}
		}
	}
}
