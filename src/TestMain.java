import Client.Client_Main;
import Server.Server_Main;
import Worker.Worker_Main;

public class TestMain {
    public static void main(String[] args) {
        Server_Main.main(new String[]{"..\\TrabalhoPCD\\news29out"});
        Client_Main.main(new String[]{null});
        Worker_Main.main(new String[]{null});
    }
}
