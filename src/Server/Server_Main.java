package Server;

public class Server_Main {
    public static void main(String[] args) {
        if (!args[0].isEmpty())
        new Server(args[0]).init();
        else System.out.println("Path da pasta que contem as noticias como primeiro argumento");
    }
}
