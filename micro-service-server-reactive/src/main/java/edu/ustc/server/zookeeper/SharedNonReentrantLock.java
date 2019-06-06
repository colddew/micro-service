package edu.ustc.server.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 不可重入锁（只能获得一次锁，使用完后释放）
 */
public class SharedNonReentrantLock implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SharedNonReentrantLock.class);

    private InterProcessSemaphoreMutex lock;
    private String lockPath = "/lock/shareLock";
    private String clientName;
    private int i;

    public static final String ZOOKEEPER_CONNECT_STRING = "127.0.0.1:2181";

    public SharedNonReentrantLock(CuratorFramework client, String clientName) {
        lock = new InterProcessSemaphoreMutex(client, lockPath);
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
//            lock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				lock.release();
                logger.info("{} release first lock", clientName);
//                lock.release();
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
            SharedNonReentrantLock sharedReentrantLock = new SharedNonReentrantLock(client, i + "_client");
            Thread thread = new Thread(sharedReentrantLock);
            thread.start();
        }
    }
}