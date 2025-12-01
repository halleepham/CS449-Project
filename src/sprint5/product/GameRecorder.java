package sprint5.product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
    PrintWriter writer = new PrintWriter("GameRecording.txt");
    for (Move move : moves) {
      writer.println(move.toString());
    }
    writer.close();
  }
  
  public ArrayList<Move> loadGameFromFile() throws FileNotFoundException{
    Scanner gameFile = new Scanner(new File("GameRecording.txt"));
    ArrayList<Move> loadedMoves = new ArrayList<Move>();
    String move; 
    String[] fields;
    
    while (gameFile.hasNext()) {
      move = gameFile.nextLine();
      fields = move.split(",");
      loadedMoves.add(new Move(
          PlayerTurn.valueOf(fields[0]),
          fields[1].charAt(0),
          Integer.parseInt(fields[2]),
          Integer.parseInt(fields[3])
          ));
    }
    gameFile.close();
    return loadedMoves;
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
