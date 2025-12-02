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
  private int boardSize;
  private String mode;
  
  public GameRecorder() {
    moves = new ArrayList<Move>();
  }
  
  public void startRecording(boolean isRecordingSelected, int boardSize, String mode) {
    this.isRecordingEnabled = isRecordingSelected;
    moves.clear();
    this.boardSize = boardSize;
    this.mode = mode;
  }
  
  public void recordMove(PlayerTurn player, char letter, int row, int col) {
    if (isRecordingEnabled) {
      moves.add(new Move(player, letter, row, col));
    }
  }
  
  public void saveGameToFile() throws FileNotFoundException {
    PrintWriter writer = new PrintWriter("GameRecording.txt");
    saveSettingsToFile(writer);
    saveMovesToFile(writer);
    writer.close();
  }
  
  private void saveSettingsToFile(PrintWriter pw) {
    pw.println("BOARD_SIZE=" + boardSize);
    pw.println("MODE=" + mode);
  }
  
  private void saveMovesToFile(PrintWriter pw) {
    for (Move move : moves) {
      pw.println(move.toString());
    }
  }
  
  public ArrayList<Move> loadMovesFromFile() throws FileNotFoundException{
    Scanner gameFile = new Scanner(new File("GameRecording.txt"));
    ArrayList<Move> loadedMoves = new ArrayList<Move>();
    String move; 
    String[] fields;
    
    gameFile.nextLine();
    gameFile.nextLine();
    
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
  
  public int loadBoardSizeFromFile() throws FileNotFoundException {
    Scanner gameFile = new Scanner(new File("GameRecording.txt"));
    String line = gameFile.nextLine();
    gameFile.close();
    return Integer.parseInt(line.split("=")[1]);
  }
  
  public String loadModeFromFile() throws FileNotFoundException {
    Scanner gameFile = new Scanner(new File("GameRecording.txt"));
    gameFile.nextLine();
    String line = gameFile.nextLine();
    gameFile.close();
    return line.split("=")[1];
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

    public PlayerTurn getPlayer() {
      return player;
    }

    public char getLetter() {
      return letter;
    }

    public int getRow() {
      return row;
    }

    public int getColumn() {
      return column;
    }

    @Override
    public String toString() {
      return player + "," + letter + "," + row + "," + column;
    }
    
  }
}
