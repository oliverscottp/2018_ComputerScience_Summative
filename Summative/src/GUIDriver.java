import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIDriver extends Application{
	Button[] stockButtons = new Button[10];
	Portfolio portfolio = new Portfolio(10000);

	BorderPane pane = new BorderPane();
	GridPane listStock = new GridPane();
	BorderPane topPane = new BorderPane();
	
	GridPane buyAndSellPane = new GridPane();
	
	//Shows things like money, total net worth etc (top left)
	GridPane infoPane = new GridPane();
	//Text for the title and the amount of money they have
	Text titleText = new Text();
	Text money = new Text();
	Text totalValue = new Text();
	
	//Centre pane giving info and graphs on stocks
	BorderPane stockPane = new BorderPane();
	Text currentStockName = new Text();
	Text amtOfStockOwned = new Text();

	
	
	//The index of the currently selected stock
	int currentSelectedStock;
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage window) throws Exception {
	
		regenerateEverything();
		pane.setLeft(listStock);
		
		//Top of main pane
		titleText.setText("Stock Game");
		titleText.setFont(Font.font(60));;
		topPane.setLeft(titleText);
		infoPane.add(new Text("Money : "), 0, 0);
		infoPane.add(money, 1, 0);
		infoPane.add(new Text("Total Value : "), 0, 1);
		infoPane.add(totalValue, 1, 1);
		topPane.setRight(infoPane);
		pane.setTop(topPane);
		
		
		
		HBox nameAndAmt = new HBox();
		nameAndAmt.getChildren().add(currentStockName);
		nameAndAmt.getChildren().add(amtOfStockOwned);
		
		stockPane.setTop(nameAndAmt);
		pane.setCenter(stockPane);
		
		//The buy and sell part on the right
		Button buyButton = new Button("Buy");
		Button sellButton = new Button("Sell");
		TextField buyAmt = new TextField();
		buyAmt.setPromptText("Amount to buy");
		TextField sellAmt = new TextField();
		sellAmt.setPromptText("Amount to sell");
		
		//Making it so you can only type integers
		buyAmt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    buyAmt.setText(oldValue);
                }
            }
        });
		sellAmt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}")) {
                    buyAmt.setText(oldValue);
                }
            }
        });
		//Buying/selling when button is clicked
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {	
				portfolio.buyStock(currentSelectedStock, Integer.valueOf(buyAmt.getText()));
				regenerateEverything();

			}
		});
		
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {		
				portfolio.sellStock(currentSelectedStock, Integer.valueOf(sellAmt.getText()));
				regenerateEverything();

			}
		});
		//Adding the buy and sell buttons and the textfields to the buyAndSellPane
		buyAndSellPane.add(buyButton, 0, 0);
		buyAndSellPane.add(buyAmt, 1, 0);
		buyAndSellPane.add(sellButton, 0, 1);
		buyAndSellPane.add(sellAmt, 1, 1);
		
		pane.setRight(buyAndSellPane);
		
		
		
		
		Scene scene = new Scene(pane, 750, 750);

		window.setTitle("Stock Game");
		window.setScene(scene);
		window.show();
	}
	
	private void generateStockButtons(){
		for(int i = 0 ; i < stockButtons.length ; i ++){
			stockButtons[i] = new Button();
			stockButtons[i].setText(portfolio.getStockName(i) +"\n" + portfolio.stocks[i].getPrice() +"$");
			stockButtons[i].setMinWidth(150);
			stockButtons[i].setAlignment(Pos.BASELINE_LEFT);
			int j = i;
			stockButtons[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					currentSelectedStock = j;
					regenerateEverything();
				}
			});
			listStock.add(stockButtons[i], 0, i);
		}
	}
	
	private void regenerateEverything(){
		generateStockButtons();
		money.setText(String.valueOf(portfolio.getMoney())); 
		totalValue.setText(String.valueOf(portfolio.totalWorth()));
		currentStockName.setText(portfolio.getStockName(currentSelectedStock));
		amtOfStockOwned.setText(String.valueOf(portfolio.numOfStock[currentSelectedStock]));
	}
	
}
