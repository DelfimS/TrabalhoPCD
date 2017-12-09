package Server.Threads;

public abstract class ServerThread extends Thread {
    int id;

    public int getIdentity() {
        return id;
    }

    public abstract void notifyThread();
}
