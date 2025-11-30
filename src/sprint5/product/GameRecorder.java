package sprint5.product;

import java.util.ArrayList;

import sprint5.product.SosGame.PlayerTurn;

public class GameRecorder {
  
  boolean isRecordingEnabled;
  ArrayList<Move> moves;
  
  public void startRecording(boolean isRecordingSelected) {
    this.isRecordingEnabled = isRecordingSelected;
    this.moves = new ArrayList<Move>();
  }
  
  public void recordMove(PlayerTurn player, char letter, int row, int col) {
    if (isRecordingEnabled) {
      moves.add(new Move(player, letter, row, col));
    }
  }
  
  public void saveGameToFile() {
    //TODO: use PrintWriter to print moves into file
  }
  
  public ArrayList<Move> loadGameFromFile(){
    //TODO: use File and Scanner to load moves into an arraylist
  }
  
  public class Move{
    PlayerTurn player;
    char letter;
    int row;
    int column;
    
    
    public Move(PlayerTurn player, char letter, int row, int column) {
      this.player = player;
      this.letter = letter;
      this.row = row;
      this.column = column;
    }
  }
}
