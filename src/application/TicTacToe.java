package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class InfoCentre {
	private StackPane pane;
	private Label message;
	private Button startGameButton;
	
	public InfoCentre() {
		pane = new StackPane();
		pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTRE_HEIGHT);
		pane.setTranslateX(UIConstants.APP_WIDTH / 2);
		pane.setTranslateY(UIConstants.INFO_CENTRE_HEIGHT / 2);
		
		message = new Label("Tic-Tac-Toe");
		message.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTRE_HEIGHT);
		message.setFont(Font.font(24));
		message.setAlignment(Pos.CENTER);
		message.setTranslateY(-20);
		pane.getChildren().add(message);
		
		startGameButton = new Button("Start New Game");
		startGameButton.setMinSize(135, 30);
		startGameButton.setTranslateY(20);
		pane.getChildren().add(startGameButton);
	}
	public StackPane getStackPane() {
		return pane;
	}
	 
	public void updateMessage(String message) {
		this.message.setText(message);
	}
	
	public void showStartButton() {
		startGameButton.setVisible(true);
	}
	
	public void hideStartButton() {
		startGameButton.setVisible(false);
	}
	
	public void setStartButtonOnAction(EventHandler<ActionEvent> onAction) {
		startGameButton.setOnAction(onAction);
	}
}
