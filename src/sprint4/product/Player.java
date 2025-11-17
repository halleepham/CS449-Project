package sprint4.product;

public class Player {
  protected char move;
  protected int points;
  
  public Player() {
    this.move = 'S';
    this.points = 0;
  }
  
  public char getMove() {
    return move;
  }
  
  public void setMove(char move) {
    this.move = move;
  }
  
  public int getPoints() {
    return points;
  }
  
  public void addPoints(int points) {
    this.points += points;
  }
  
  public int[] selectMove(SosGame game) {
    return null;
  }
}