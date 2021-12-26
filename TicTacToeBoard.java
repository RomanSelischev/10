package game;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0",
            Cell.T, '-',
            Cell.L, '|'
    );
    private static final Map<Integer, Cell> NEXT = Map.of(
            1, Cell.X,
            2, Cell.O,
            3, Cell.T,
            4, Cell.L
    );

    private final Cell[][] field; // cells
    private Cell turn;
    private int empty = 0;
    private int playersCnt;
    private String space = " ";
    private int divN;
    private int divM;


    public TicTacToeBoard() {
        field = new Cell[MnkConst.n][MnkConst.m];
        for (int r = 0; r < MnkConst.n; r++) {
            for (int c = 0; c < MnkConst.m; c++) {
                field[r][c] = Cell.N;
            }
        }
        this.playersCnt = MnkConst.players.size();
        turn = Cell.X;
    }


    @Override
    public Cell getTurn() { //public Cell getCell()
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move, int no) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = NEXT.get(no % playersCnt + 1);
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        int count = 0;
        for (int r = 0; r < MnkConst.n; r++) {
            for (int c = 0; c < MnkConst.m; c++) {
                if (field[r][c] == Cell.E) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        for (int r = 0; r < MnkConst.n; r++) {
            int count = 0;
            for (int c = 0; c < MnkConst.m; c++) {
                if (field[r][c] == turn) {
                    count++;
                }
            }
            if (count == MnkConst.k) {
                return true;
            }
        }
        for (int c = 0; c < MnkConst.n; c++) {//
            int count = 0;
            for (int r = 0; r < MnkConst.m; r++) {
                if (field[r][c] == turn) {
                    count++;
                }
            }
            if (count == MnkConst.k) {
                return true;
            }
        }
       
        if (count(move, 1, 1) >= MnkConst.k || count(move, 1, -1) >= MnkConst.k) {
            return true;
        }

    }
    private int count(Move move, int dv, int du) {
        return checker(move.getColumn(), move.getRow(), dv, du) +
                checker(move.getColumn(), move.getRow(), -dv, -du) - 1;
    }

    private int checker(int v, int u, int vDelta, int uDelta) {
        int cnt = 0;
        while (v >= 0 && u >= 0 && v < MnkConst.n && u < MnkConst.m
                && field[v][u] == turn) {
            v += vDelta;
            u += uDelta;
            cnt++;
        }
        return cnt;
    }


    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < MnkConst.n
                && 0 <= move.getCol() && move.getCol() < MnkConst.m
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(space.repeat(1 + Integer.toString(MnkConst.n).length()));
        /*for (int c = 0; c < MnkConst.m; c++) { 
            sb.append(c).append(space.repeat(Integer.toString(MnkConst.m).length() - Integer.toString(c).length() + 1));
        }*/
        sb.append('\n');
        for (int r = 0; r < MnkConst.n; r++) {
            sb.append(r).append(space.repeat(1 + Integer.toString(MnkConst.n).length() - Integer.toString(r).length()));
            for (int c = 0; c < MnkConst.m; c++) {
                sb.append(SYMBOLS.get(field[r][c])).append(space.repeat(Integer.toString(MnkConst.m).length()));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    
}
