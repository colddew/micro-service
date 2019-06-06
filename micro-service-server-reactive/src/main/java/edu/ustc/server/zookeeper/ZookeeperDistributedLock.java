package edu.ustc.server.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class ZookeeperDistributedLock {

    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();

        final InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
        if (mutex.acquire(1, TimeUnit.MINUTES)) {
            try {
                // business logic
            } finally {
                mutex.release();
            }
        }

        client.close();
    }
}
