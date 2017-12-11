package Server.Threads;

public abstract class ServerThread extends Thread {
    int id;

    ServerThread(int id) {
        this.id = id;
    }

    public int getIdentity() {
        return id;
    }

    public abstract void notifyThread();

}
