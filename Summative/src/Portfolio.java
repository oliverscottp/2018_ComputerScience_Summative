
public class Portfolio {
	Stock[] stocks = new Stock[10];
	
	public Portfolio(){
		generateStocks();
	}
	
	
	private void generateStocks(){
		stocks[0] = new Stock("Bepis Cola Inc");
		stocks[1] = new Stock("Appricot Computing");
		stocks[2] = new Stock("Doratus Chips");
		stocks[3] = new Stock("Ikua Furniture");
		stocks[4] = new Stock("Amazone");
		stocks[5] = new Stock("Ubysoft");
		stocks[6] = new Stock("Fedecks");
		stocks[7] = new Stock("Ubber");
		stocks[8] = new Stock("Nintenbo");
		stocks[9] = new Stock("Old Gravy");
				
	}
}
