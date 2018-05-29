package scottPankratz.oliver;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GUIDriver extends Application {

	public static int maxDays = 50;

	private Button[] stockButtons = new Button[10];
	private Portfolio portfolio = new Portfolio(10000);

	private BorderPane pane = new BorderPane();
	private GridPane listStock = new GridPane();
	private BorderPane topPane = new BorderPane();

	private GridPane buyAndSellPane = new GridPane();

	// Shows things like money, total net worth etc (top left)
	private GridPane infoPane = new GridPane();
	// Text for the title and the amount of money they have
	private Text titleText = new Text();
	private Text money = new Text();
	private Text totalValue = new Text();

	// Centre pane giving info and graphs on stocks
	private BorderPane stockPane = new BorderPane();
	private Text currentStockName = new Text();
	private Text amtOfStockOwned = new Text();

	private HBox newsBox = new HBox(15);

	// Button to go to the next day
	private Button nxtDay = new Button("Next day");

	// The index of the currently selected stock
	private int currentSelectedStock;

	private int currentDay = 0;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * handles the placement of all buttons and labels and handles their
	 * functions
	 */
	public void start(Stage window) throws Exception {
	
		
		regenerateEverything();
		pane.setLeft(listStock);

		// Top of main pane
		titleText.setText("Stock Game");
		titleText.setFont(Font.font(60));

		topPane.setLeft(titleText);
		infoPane.add(new Text("Money : "), 0, 0);
		infoPane.add(money, 1, 0);
		infoPane.add(new Text("Total Value : "), 0, 1);
		infoPane.add(totalValue, 1, 1);
		topPane.setRight(infoPane);
		pane.setTop(topPane);

		Text news = new Text("News : ");
		news.setFont(Font.font(50));

		nxtDay.setFont(Font.font(30));
		newsBox.getChildren().add(nxtDay);
		newsBox.getChildren().add(news);
		
		nxtDay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (currentDay < maxDays - 1) {

					String[] messages = portfolio.goToNextDay();
					currentDay++;
					regenerateEverything();

					newsBox = new HBox(15);

					newsBox.getChildren().add(nxtDay);
					newsBox.getChildren().add(news);

					Text[] messagesText = new Text[messages.length];
					for (int i = 0; i < messages.length; i++) {
						messagesText[i] = new Text();
						messagesText[i].setFont(Font.font(20));
						messagesText[i].maxWidth(30);
						messagesText[i].setText(messages[i]);
						newsBox.getChildren().add(messagesText[i]);
					}
					pane.setBottom(newsBox);

				}
			}
		});

		pane.setBottom(newsBox);

		BorderPane nameAndAmt = new BorderPane();
		nameAndAmt.setLeft(currentStockName);
		nameAndAmt.setRight(amtOfStockOwned);

		stockPane.setTop(nameAndAmt);
		pane.setCenter(stockPane);

		// The buy and sell part on the right
		Button buyButton = new Button("Buy");
		Button sellButton = new Button("Sell");
		TextField buyAmt = new TextField();
		buyAmt.setPromptText("Amount to buy");
		TextField sellAmt = new TextField();
		sellAmt.setPromptText("Amount to sell");

		// Making it so you can only type integers
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
		// Buying/selling when button is clicked
		buyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (buyAmt.getText().trim().isEmpty() == false) {
					portfolio.buyStock(currentSelectedStock, Integer.valueOf(buyAmt.getText()));
				}
				regenerateEverything();

			}
		});

		sellButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (sellAmt.getText().trim().isEmpty() == false) {
					portfolio.sellStock(currentSelectedStock, Integer.valueOf(sellAmt.getText()));
				}
				regenerateEverything();

			}
		});
		// Adding the buy and sell buttons and the textfields to the
		// buyAndSellPane
		buyAndSellPane.add(buyButton, 0, 0);
		buyAndSellPane.add(buyAmt, 1, 0);
		buyAndSellPane.add(sellButton, 0, 1);
		buyAndSellPane.add(sellAmt, 1, 1);

		pane.setRight(buyAndSellPane);

		Scene scene = new Scene(pane, 1700, 750);

		// The start menu
		BorderPane mainMenu = new BorderPane();
		Button startButton = new Button("Start Game");
		startButton.setFont(Font.font(50));
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				window.setScene(scene);

			}
		});

		Text startText = new Text("How much can you make in " + maxDays + " days?");
		startText.setFont(Font.font(50));

		mainMenu.setTop(startText);
		mainMenu.setAlignment(startText, Pos.CENTER);

		mainMenu.setCenter(startButton);

		Scene menu = new Scene(mainMenu, 1700, 750);

		window.setTitle("Stock Game");
		window.setScene(menu);
		window.show();
	}

	/**
	 * generates all the stock buttons to have the name, price and the number
	 * owned
	 */
	private void generateStockButtons() {
		for (int i = 0; i < stockButtons.length; i++) {
			stockButtons[i] = new Button();
			stockButtons[i].setText(portfolio.getStockName(i) + "\n" + portfolio.getPrice(i) + "$" + "\nOwned : "
					+ String.valueOf(portfolio.getNumberOfStock(i)));
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

	/**
	 * Updates everything in the scene which can change
	 */
	private void regenerateEverything() {
		generateStockButtons();
		money.setText(String.valueOf(portfolio.getMoney()));
		totalValue.setText(String.valueOf(portfolio.totalWorth()));
		currentStockName.setText(portfolio.getStockName(currentSelectedStock));
		amtOfStockOwned.setText("Stock Owned : " + String.valueOf(portfolio.getNumberOfStock(currentSelectedStock)));

		NumberAxis xAxis = new NumberAxis(1, currentDay + 1, 1);

		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Days");
		yAxis.setLabel("Value");
		// creating the chart
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle(currentStockName.getText());
		lineChart.setLegendVisible(false);
		// defining a series
		XYChart.Series series = new XYChart.Series();

		// populating the series with data
		for (int i = 0; i < currentDay + 1; i++) {
			series.getData().add(new XYChart.Data(i + 1, portfolio.getPriceAtDate(currentSelectedStock, i)));
		}

		lineChart.getData().add(series);
		stockPane.setCenter(lineChart);

	}

}
