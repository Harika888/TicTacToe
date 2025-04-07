package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class TileBoard {
	
	private StackPane pane;
	private InfoCentre infoCentre;
	private Tile[][] tiles = new Tile[3][3];
	private Line winningLine;
	
	private char playerTurn = 'X';
	private boolean isEndOfGame = false;
	
	public TileBoard(InfoCentre infoCentre) {
		this.infoCentre = infoCentre;
		pane = new StackPane();
		pane.setMaxSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
		pane.setTranslateX(UIConstants.APP_WIDTH / 2);
		pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTRE_HEIGHT);
	
		addAllTiles();
		winningLine = new Line();
		pane.getChildren().add(winningLine);
	}
	
	private void addAllTiles() {
		for (int row=0; row<3 ; row++) {
			for (int col = 0;col<3;col++) {
				Tile tile = new Tile();
				tile.getStackPane().setTranslateX((col * 100) - 100);
				tile.getStackPane().setTranslateY((row * 100) - 100);
				pane.getChildren().add(tile.getStackPane());
				tiles[row][col] = tile;
			}
		}
		
	}
	public void startNewGame() {
		isEndOfGame = false;
		playerTurn = 'X';
		for (int row=0; row<3 ; row++) {
			for (int col = 0;col<3;col++) {
				tiles[row][col].setValue("");
			}
		}
		winningLine.setVisible(false);
	}
	public void changePlayerTurn() {
		if(playerTurn == 'X') {
			playerTurn = 'O';
		}else {
			playerTurn = 'X';
		}
		infoCentre.updateMessage("Player " + playerTurn + "'s turn");
	}
	public String getPlayerTurn() {
		return String.valueOf(playerTurn);
	}
	public StackPane getStackPane() {
		return pane;
	}
	public void checkForWinner() {
		checkRowsForWinner();
		checkcolsForWinner();
		checkTopLeftToBottomRightForWinner();
		checkTopRightToBottomLeftForWinner();
		checkForStalemate();
	}
	
	private void checkRowsForWinner() {
		for (int row=0; row<3 ; row++) {
			if(tiles[row][0].getValue().equals(tiles[row][1].getValue()) && 
					tiles[row][0].getValue().equals(tiles[row][2].getValue()) &&
					!tiles[row][0].getValue().isEmpty()) {
				String winner = tiles[row][0].getValue();
				endGame(winner, new WinningTiles(tiles[row][0], tiles[row][1],tiles[row][2]));
				return;
			}
		}
		
	}


	private void checkcolsForWinner() {
		if (!isEndOfGame) {
			for (int col=0; col<3 ; col++) {
				if(tiles[0][col].getValue().equals(tiles[1][col].getValue()) && 
						tiles[0][col].getValue().equals(tiles[2][col].getValue()) &&
						!tiles[0][col].getValue().isEmpty()) {
					String winner = tiles[0][col].getValue();
					endGame(winner, new WinningTiles(tiles[0][col],tiles[1][col],tiles[2][col]));
					return;
				}
			}
		}
		
		
	}

	private void checkTopLeftToBottomRightForWinner() {
		if(!isEndOfGame) {
			if(tiles[0][0].getValue().equals(tiles[1][1].getValue()) 
					&& tiles[0][0].getValue().equals(tiles[2][2].getValue()) && !tiles[0][0].getValue().isEmpty()) {
				String winner = tiles[0][0].getValue();
				endGame(winner, new WinningTiles(tiles[0][0], tiles[1][1], tiles[2][2]));
				return;
			}
		}
		
	}

	private void checkTopRightToBottomLeftForWinner() {
		if(!isEndOfGame) {
				if(tiles[0][2].getValue().equals(tiles[1][1].getValue()) 
						&& tiles[0][2].getValue().equals(tiles[2][0].getValue()) && !tiles[0][2].getValue().isEmpty()) {
					String winner = tiles[0][2].getValue();
					endGame(winner,  new WinningTiles(tiles[0][2], tiles[1][1], tiles[2][0]));
					return;
			}
		}
	}

	private void checkForStalemate() {
		if(!isEndOfGame) {
			for(int row=0;row<3;row++) {
				for(int col=0;col<3;col++) {
					if(tiles[row][col].getValue().isEmpty()) {
						return;
					}
				}
			}
			isEndOfGame = true;
			infoCentre.updateMessage("Stalemate...");
			infoCentre.showStartButton();
		}	
	}
	private void endGame(String winner, WinningTiles winningTiles) {
		isEndOfGame = true;
		drawWinningLine(winningTiles);
		infoCentre.updateMessage("Player " + winner + " wins!");
		infoCentre.showStartButton();
	}
	private void drawWinningLine(WinningTiles winningTiles) {
		winningLine.setStartX(winningTiles.start.getStackPane().getTranslateX());
		winningLine.setStartY(winningTiles.start.getStackPane().getTranslateY());
		winningLine.setEndX(winningTiles.end.getStackPane().getTranslateX());
		winningLine.setEndY(winningTiles.end.getStackPane().getTranslateY());
		winningLine.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
		winningLine.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
		winningLine.setVisible(true);
	}
	private class WinningTiles {
		Tile start;
		Tile middle;
		Tile end;
		public WinningTiles(Tile start, Tile middle, Tile end) {
			this.start = start;
			this.middle = middle;
			this.end = end;
			
		}
	}
	private class Tile{
		
		private StackPane pane;
		private Label label;
		
		public Tile() {
			pane = new StackPane();
			pane.setMaxSize(100, 100);
			
			Rectangle border = new Rectangle();
			border.setWidth(100);
			border.setHeight(100);
			border.setFill(Color.TRANSPARENT);
			border.setStroke(Color.BLACK);
			pane.getChildren().add(border);
			
			label = new Label("");
			label.setAlignment(Pos.CENTER);
			label.setFont(Font.font(24));
			pane.getChildren().add(label);
			
			pane.setOnMouseClicked(event -> {
				if(label.getText().isEmpty() && !isEndOfGame) {
					label.setText(getPlayerTurn());
					changePlayerTurn();
					checkForWinner();
				}
			});
		}
		
		public StackPane getStackPane() {
			return pane;
		}
		
		public String getValue() {
			return label.getText();
		}
		
		public void setValue(String value) {
			label.setText(value);
		}
	}

}
