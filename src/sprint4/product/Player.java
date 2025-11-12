package sprint4.product;

public abstract class Player {
  protected char move;
  protected int points;
  
  public Player() {
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
  
  public void setPoints(int points) {
    this.points = points;
  }
}