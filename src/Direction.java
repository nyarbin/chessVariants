public class Direction {
   private int dX;
   private int dY;

   public Direction(int dX, int dY) {
      this.dX = dX;
      this.dY = dY;
   }

   public boolean isForward() {
      return dY > 0;
   }

   public boolean isRight() {
      return dX > 0;
   }

   public Direction forSym() {
      return new Direction(dX, -1 * dY);
   }

   public Direction sideSym() {
      return new Direction(-1 * dX, dY);
   }

   public Direction rotSym() {
      return new Direction(dY, dX);
   }

   public int xDist() {
      return dX;
   }

   public int yDist() {
      return dY;
   }
}
