package edu.ustc.server.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ZookeeperCurator {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperCurator.class);

    public static void main(String[] args) throws Exception {
        process();
    }

    private static void process() throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",
                new RetryNTimes(10, 5000));
        client.start();

        // monitor child node
        List<String> children = client.getChildren().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                logger.info("monitor: {}", event);
            }
        }).forPath("/");
        logger.info("children: {}", children);

        addListener(client);

        // create node
        String result = client.create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/test", "Data".getBytes());
        logger.info("result: {}", result);

        // set node
        client.setData().forPath("/test", "111".getBytes());
        client.setData().forPath("/test", "222".getBytes());

        // delete node
        logger.info("path: {}", client.checkExists().forPath("/test"));
        client.delete().withVersion(-1).forPath("/test");
        logger.info("path: {}", client.checkExists().forPath("/test"));

        client.close();
        logger.info("client close");
    }

    private static void addListener(CuratorFramework client) {

        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                logger.info("event: {}", event);
            }
        });

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                logger.info("connect state: {}", newState);
            }
        });

        client.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
            @Override
            public void unhandledError(String message, Throwable e) {
                logger.info("error event: {}", message);
            }
        });
    }
}
