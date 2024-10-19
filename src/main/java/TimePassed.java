import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;

public class TimePassed {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void countTimeSec() {
        final Runnable everySec = new Runnable() {
            int num = 1;
            public void run() {
                System.out.println(num);
                num++; }
        };

        final ScheduledFuture<?> everySecPassed = scheduler.scheduleAtFixedRate(everySec, 1, 1, SECONDS);
    }

    public void countTimeFiveSec() {
        final Runnable fiveSec = new Runnable() {
            public void run() {
                System.out.println("Минуло 5 секунд");
            }
        };

        final ScheduledFuture<?> fiveSecPassed = scheduler.scheduleAtFixedRate(fiveSec, 5, 5, SECONDS);
    }

    public static void main(String[] args) {
        TimePassed bp = new TimePassed();
        bp.countTimeSec();
        bp.countTimeFiveSec();
    }
}