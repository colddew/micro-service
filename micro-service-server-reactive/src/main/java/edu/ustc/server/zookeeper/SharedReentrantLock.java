package edu.ustc.server.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 可重入锁（可以多次获得锁不会被阻塞，释放时也需释放多把锁）
 */
public class SharedReentrantLock implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SharedReentrantLock.class);

    private InterProcessMutex lock;
    private String lockPath = "/lock/shareLock";
    private String clientName;
    private int i;

    public static final String ZOOKEEPER_CONNECT_STRING = "127.0.0.1:2181";

    public SharedReentrantLock(CuratorFramework client, String clientName) {
        lock = new InterProcessMutex(client, lockPath);
        this.clientName = clientName;
    }

    public void run() {
        try {
            Thread.sleep((new Random().nextInt(2000)));
            // add first lock
            lock.acquire();
            if (lock.isAcquiredInThisProcess()) {
                logger.info("{} get lock", clientName);
                i++;
            }
            // add second lock
            lock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                logger.info("{} release first lock", clientName);
                lock.release();
                logger.info("{} release second lock", clientName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        CuratorFramework client = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONNECT_STRING, new ExponentialBackoffRetry(1000, 3));
        client.start();

        for (int i = 0; i < 5; i++) {
            SharedReentrantLock sharedReentrantLock = new SharedReentrantLock(client, i + "_client");
            Thread thread = new Thread(sharedReentrantLock);
            thread.start();
        }
    }
}