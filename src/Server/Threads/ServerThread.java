package Server.Threads;

public abstract class ServerThread extends Thread {
    int id;

    public ServerThread(int id) {
        this.id=id;
    }

    public int getIdentity() {
        return id;
    }

    public abstract void notifyThread();
}
