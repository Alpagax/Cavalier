public class cavalier_all {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int length = Integer.parseInt(args[2]);
        var b = new Board(x, y, length);
        long res = b.cavalier_all();
        System.out.println("Nombre de solutions : " + res);
    }
}