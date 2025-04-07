package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private InfoCentre infoCentre;
	private TileBoard tileBoard;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
			initLayout(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initLayout(BorderPane root) {
		initInfoCentre(root);
		initTileBoard(root);
		
	}

	private void initInfoCentre(BorderPane root) {
		infoCentre = new InfoCentre();
		infoCentre.setStartButtonOnAction(startNewGame());
		root.getChildren().add(infoCentre.getStackPane());
		
	}
	private EventHandler<ActionEvent> startNewGame(){
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				infoCentre.hideStartButton();
				infoCentre.updateMessage("Player X's Turn");
				tileBoard.startNewGame();
			}
		};
	}

	private void initTileBoard(BorderPane root) {
		tileBoard = new TileBoard(infoCentre);
		root.getChildren().add(tileBoard.getStackPane());
		
	}


	public static void main(String[] args) {
		launch(args);
	}
}
