package sprint5.product;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import sprint5.product.SosGame.PlayerTurn;

public class GameRecorder {
  
  private boolean isRecordingEnabled;
  private ArrayList<Move> moves;
  
  public void startRecording(boolean isRecordingSelected) {
    this.isRecordingEnabled = isRecordingSelected;
    this.moves = new ArrayList<Move>();
  }
  
  public void recordMove(PlayerTurn player, char letter, int row, int col) {
    if (isRecordingEnabled) {
      moves.add(new Move(player, letter, row, col));
    }
  }
  
  public void saveGameToFile() throws FileNotFoundException {
    PrintWriter gameFile = new PrintWriter("GameRecording.txt");
    for (Move move : moves) {
      gameFile.println(move.toString());
    }
    gameFile.close();
  }
  
  public ArrayList<Move> loadGameFromFile(){
    //TODO: use File and Scanner to load moves into an arraylist
    return null;
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

    @Override
    public String toString() {
      return player + "," + letter + "," + row + "," + column;
    }
    
  }
}
