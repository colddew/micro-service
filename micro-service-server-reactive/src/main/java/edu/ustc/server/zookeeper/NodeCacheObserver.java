package edu.ustc.server.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeCacheObserver {

    private static final Logger logger = LoggerFactory.getLogger(NodeCacheObserver.class);

    // zookeeper cluster connect string, separated by commas
    public static final String ZOOKEEPER_CONNECT_STRING = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONNECT_STRING, new ExponentialBackoffRetry(1000, 3));
        client.start();

        String path = client.create().creatingParentsIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL).forPath("/observer", "data".getBytes());
        final NodeCache nodeCache = new NodeCache(client, path, false);
        nodeCache.start();

        nodeCache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                if (nodeCache.getCurrentData() != null) {
                    logger.info(nodeCache.getPath());
                }
            }
        });
    }
}