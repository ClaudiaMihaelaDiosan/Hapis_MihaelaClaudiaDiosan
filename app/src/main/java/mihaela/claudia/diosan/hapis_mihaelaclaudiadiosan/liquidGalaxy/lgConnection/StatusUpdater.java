package mihaela.claudia.diosan.hapis_mihaelaclaudiadiosan.liquidGalaxy.lgConnection;

public class StatusUpdater implements Runnable {
    private volatile boolean cancelled;

    private LGConnectionManager lgConnectionManager;

    public StatusUpdater(LGConnectionManager lgConnectionManager) {
        this.lgConnectionManager = lgConnectionManager;
    }

    public void run() {
        try {
            while (!cancelled) {
                lgConnectionManager.tick();
                Thread.sleep(200L); //TICKS every 200ms
            }
        } catch (InterruptedException e) {

        }
    }

    public void cancel() {
        cancelled = true;
    }
}
