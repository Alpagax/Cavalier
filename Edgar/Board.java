import java.util.Scanner;
import java.util.Optional;
import java.util.Stack;
import java.util.ArrayList;

public class Board {
    private int board_side;
    private int [][] board;
    private Stack<RelativeMove> previous_moves;
    private int curr_pos_x, curr_pos_y;

    //Constructeur
    public Board(int pos_x, int pos_y, int board_side) {
        this.board_side = board_side;
        this.curr_pos_x = pos_x;
        this.curr_pos_y = pos_y;

        this.board = new int[board_side][board_side];
        for (int i = 0; i < board_side; i++) {
            for (int j = 0; j < board_side; j++)
                this.board[i][j] = 0;
        }

        if (pos_x < 0 || pos_y < 0 || pos_x >= board_side || pos_y >= board_side)
            throw new IllegalArgumentException("Out of the chessboard");
    }

    public Optional<int[][]> solve(int depth) {
        if (depth <= 0)
            throw new IllegalArgumentException();

        if (this.is_complete())
            return Optional.of(this.board);

        Optional<int[][]> res = Optional.empty();
        var possible_moves = this.compute_possible_move();
        for (var move : possible_moves) {
            this.do_move(move, depth);
            res = solve(depth + 1);

            if (res.isPresent())
                return res;

            this.undo_move();
        }

        return res;
    }

    public int cavalier_all() {
        if (this.is_complete())
            return 1;

        int res = 0;
        var possible_moves = this.compute_possible_move();
        for (var move : possible_moves) {
            this.do_move(move, 1);
            res += cavalier_all();
            this.undo_move();
        }

        return res;
    }

    private boolean is_complete() {
        boolean res = true;
        for (int i = 0; i < this.board_side; i++) {
            for (int j = 0; j < this.board_side; j++) {
                if (this.board[i][j] == 0)
                    res = false;
            }
        }

        return res;
    }

    private void do_move(RelativeMove m, int depth) {
        curr_pos_x += m.x;
        curr_pos_y += m.y;
        this.board[curr_pos_x][curr_pos_y] = depth;
    }

    private void undo_move() {
        this.board[curr_pos_x][curr_pos_y] = 0;
        RelativeMove m = previous_moves.pop();
        curr_pos_x -= m.x;
        curr_pos_y -= m.y;
    }

    private ArrayList<RelativeMove> compute_possible_move() {

        ArrayList<RelativeMove> result = null;
        int new_x, new_y;
        if ((new_x = curr_pos_x + 1) < board_side && (new_y = curr_pos_y + 2) < board_side)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x - 1) >= 0 && (new_y = curr_pos_y + 2) < board_side)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x + 1) < board_side && (new_y = curr_pos_y - 2) >= 0)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x - 1) >= 0 && (new_y = curr_pos_y - 2) >= 0)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x + 2) < board_side && (new_y = curr_pos_y + 1) < board_side)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x - 2) >= 0 && (new_y = curr_pos_y + 1) < board_side)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x + 2) < board_side && (new_y = curr_pos_y - 1) >= 0)
            result.add(new RelativeMove(new_x, new_y));
        if ((new_x = curr_pos_x - 2) >= 0 && (new_y = curr_pos_y - 1) >= 0)
            result.add(new RelativeMove(new_x, new_y));

        return result;
    }


}