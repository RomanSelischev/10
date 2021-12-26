package game;

public interface Board {
    Position getPosition();
    Cell getCell();
    GameResult makeMove(Move move, int no);
}
