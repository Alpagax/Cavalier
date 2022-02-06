import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

public class Board {
    //Attributs
    private final int board_side;
    private final int[][] board;
    private final Stack<RelativeMove> previous_moves;
    private int curr_pos_x, curr_pos_y;

    //Constructeur
    public Board(int pos_x, int pos_y, int board_side) {
        pos_x--;
        pos_y--;
        this.board_side = board_side;
        this.curr_pos_x = pos_x;
        this.curr_pos_y = pos_y;
        this.previous_moves = new Stack<RelativeMove>();

        this.board = new int[board_side][board_side];
        for (int i = 0; i < board_side; i++) {
            for (int j = 0; j < board_side; j++)
                this.board[i][j] = 0;
        }
        this.board[curr_pos_x][curr_pos_y] = 1;

        if (pos_x < 0 || pos_y < 0 || pos_x >= board_side || pos_y >= board_side)
            throw new IllegalArgumentException("Out of the chessboard");
    }

    /**
     * Trouve une solution au probleme du cavalier
     * Pour avoir une autre solution, inverser les places des if dans compute_possible_move()
     * @param depth est toujours égal à 1 quand on lance car il n' ya que une seule occupée
     * @return
     */



    public Optional<int[][]> solve(int depth) {
        if (depth <= 0)
            throw new IllegalArgumentException();

        if (this.is_complete())
            return Optional.of(this.board);

        Optional<int[][]> res = Optional.empty();
        var possible_moves = this.compute_possible_move();
        for (var move : possible_moves) {
            if (this.board[curr_pos_x + move.x][curr_pos_y + move.y] != 0)
                continue;
            this.do_move(move, depth);
            res = solve(depth + 1);

            if (res.isPresent())
                return res;

            this.undo_move();
        }

        return res;
    }


    /**    Trouve toutes les solutions du probleme du cavalier
     * @return le nombre de solutions
     */

    public long cavalier_all() {
        if (this.is_complete())
            return 1;

        long res = 0;
        var possible_moves = this.compute_possible_move();
        for (var move : possible_moves) {
            if (this.board[curr_pos_x + move.x][curr_pos_y + move.y] != 0)
                continue;
            this.do_move(move, 1);
            res += cavalier_all();
            this.undo_move();
        }

        return res;
    }


    /**
     * Verifie que le parcours du cavalier est complet
     * @return true quand un parcours est reussi
     */
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


    /**
     * Deplace le cavalier
     * @param m le prochain mouvement du cavalier
     * @param depth le nombre de cases empruntées
     */
    private void do_move(RelativeMove m, int depth) {
        curr_pos_x += m.x;
        curr_pos_y += m.y;
        this.previous_moves.add(m);
        this.board[curr_pos_x][curr_pos_y] = depth + 1;
    }

    /**
     * Ne prend aucun paramètre, déplace le cavalier à sa position précédente
     */
    private void undo_move() {
        this.board[curr_pos_x][curr_pos_y] = 0;
        RelativeMove m = previous_moves.pop();
        curr_pos_x -= m.x;
        curr_pos_y -= m.y;
    }

    /**
    *   On ajoute tous les mouvements possibles dans une liste contigue vide
    */
    private ArrayList<RelativeMove> compute_possible_move() {

        ArrayList<RelativeMove> result = new ArrayList<>();
        int new_x, new_y;

        if ((new_x = curr_pos_x - 1) >= 0 && (new_y = curr_pos_y + 2) < board_side)
            result.add(new RelativeMove(-1, 2));
        if ((new_x = curr_pos_x + 1) < board_side && (new_y = curr_pos_y - 2) >= 0)
            result.add(new RelativeMove(1, -2));
        if ((new_x = curr_pos_x - 1) >= 0 && (new_y = curr_pos_y - 2) >= 0)
            result.add(new RelativeMove(-1, -2));
        if ((new_x = curr_pos_x + 2) < board_side && (new_y = curr_pos_y + 1) < board_side)
            result.add(new RelativeMove(2, 1));
        if ((new_x = curr_pos_x - 2) >= 0 && (new_y = curr_pos_y + 1) < board_side)
            result.add(new RelativeMove(-2, 1));
        if ((new_x = curr_pos_x + 2) < board_side && (new_y = curr_pos_y - 1) >= 0)
            result.add(new RelativeMove(2, -1));
        if ((new_x = curr_pos_x - 2) >= 0 && (new_y = curr_pos_y - 1) >= 0)
            result.add(new RelativeMove(-2, -1));
        if ((new_x = curr_pos_x + 1) < board_side && (new_y = curr_pos_y + 2) < board_side)
            result.add(new RelativeMove(1, 2));
        return result;
    }


}