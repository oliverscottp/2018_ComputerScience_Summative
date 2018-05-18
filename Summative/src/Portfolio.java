
public class Portfolio {
	int numOfDifferentStocks = 10;
	public Stock[] stocks = new Stock[numOfDifferentStocks];
	public int[] numOfStock = new int[numOfDifferentStocks];
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
		stocks[2] = new Stock("Doratus Chips", Industry.FOOD);
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
	 * @param stockNum	The stock index number
	 * @param amtOfStock	The amount of stock they want to buy
	 * @return	Returns "Stock bought" if they have enough money, returns "invalid" if they dont.
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
	 * @param stockNum	The stock index number
	 * @param amtOfStock	The amount of stock they want to sell
	 * @return	Returns sold if they have enough stock, returns invalid amount if they dont have enough
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

	/**
	 * Returns stock name given the stock index number
	 * @param stockNumber	The index number for the stock in the array
	 * @return	The string (name) associated with the stock
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
	
	public double totalWorth(){
		double rounded = money;
		
		for(int i = 0 ; i < numOfStock.length ; i++){
			rounded+=stocks[i].getPrice() * numOfStock[i];
		}
		rounded = Math.round(rounded * 100.0) / 100.0;
		return rounded;
				
	}
}
