import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PingPong implements Runnable {
    String word;
    Lock lock;

    public PingPong(String word, Lock lock) {
        this.word = word;
        this.lock = lock;
    }

    @Override
    public void run(){
        while(true){
            System.out.println("Received : " + word);
            lock.notifyAll();
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        Lock lock =  new ReentrantLock();
        while(true){
            ex.submit(new PingPong("ping", lock));
            ex.submit(new PingPong("pong", lock));
        }
    }
}