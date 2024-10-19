import java.util.concurrent.ArrayBlockingQueue;

public class FizzBuzz {
    private static int n = 30;
    private static int i = 1;
    final static ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(n);
    private static final Object monitor = new Object();

    Thread threadA = new Thread(() -> fizz(n));
    Thread threadB = new Thread(() -> buzz(n));
    Thread threadC = new Thread(() -> fizzbuzz(n));
    Thread threadD = new Thread(() -> numbers(n));

    private static void fizz(int n) {
        while (i <= n) {
            synchronized (monitor) {
                if (i % 3 == 0 && i % 5 != 0) {
                    abq.add("fizz");
                    i++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private static void buzz(int n) {
        while (i <= n) {
            synchronized (monitor) {
                if (i % 5 == 0 && i % 3 != 0) {
                    abq.add("buzz");
                    i++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private static void fizzbuzz(int n) {
        while (i <= n) {
            synchronized (monitor) {
                if (i % 5 == 0 && i % 3 == 0) {
                    abq.add("fizzbuzz");
                    i++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private static void numbers(int n) {
        while (i <= n) {
            synchronized (monitor) {
                if (i % 3 != 0 && i % 5 != 0) {
                    abq.add(String.valueOf(i));
                    i++;
                    monitor.notifyAll();
                } else {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public int getN() {
        return n;
    }

    public void number(){
        System.out.println(abq.poll());
    }

    private void createAbq(){
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FizzBuzz d = new FizzBuzz();
        d.createAbq();
        for (int k = 0; k < d.getN(); k++) {
            d.number();
        }
    }
}