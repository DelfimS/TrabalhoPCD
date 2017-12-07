package Server.Threads;

import Server.Server;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SenderThreadWorker extends SenderThread {

    public SenderThreadWorker(Socket socket, Server server, int id, ObjectOutputStream out) {
        super(socket, server, id, out);
    }

}
