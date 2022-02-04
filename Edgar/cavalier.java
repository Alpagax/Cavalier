public class cavalier {
    public static void main(String[] args) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int length = Integer.parseInt(args[2]);
            var b = new Board(x, y, length);
            System.out.print(b.solve(1));


    }
}