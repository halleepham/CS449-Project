package sprint4.product;

public class ComputerPlayer extends Player {

  @Override
  public int[] selectMove(SosGame game) {
    // TODO Auto-generated method stub
    return pickFirstEmptyMove(game);
  }

  private int[] pickSosMove(SosGame game) {
    // TODO: implement logic after getting player logic working
    return null;
  }
  
  private int[] pickSafeMove(SosGame game) {
    // TODO: implement logic after getting player logic working
    return null;
  }
  
  // temporary logic to pick first empty cell to make sure computer plays works
  private int[] pickFirstEmptyMove(SosGame game) {
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == SosGame.Cell.EMPTY) {
          return new int[] {row, col};
        }
      }
    }
    return null;
  }
}
