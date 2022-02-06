public class cavalier {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int length = Integer.parseInt(args[2]);
        var b = new Board(x, y, length);
        var board = b.solve(1);
        if (board.isEmpty()) {
            System.out.println("Pas de solution");
        }
        var p = board.get();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(p[i][j] + " ");
            }
            System.out.println();
        }
    }
}