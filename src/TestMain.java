import Client.Client_Main;
import Server.Server_Main;
import Worker.Worker_Main;

public class TestMain {
    public static void main(String[] args) {
        Server_Main.main(null);
        Client_Main.main(null);
        Worker_Main.main(null);
    }
}
