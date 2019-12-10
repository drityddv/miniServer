package rpc.test.client;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rpc.test.TestRpcService.TestPacketRequest;
import rpc.test.TestRpcService.TestPacketResponse;
import rpc.test.TestRpcService.TestRpcServiceGrpc;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/12/9 5:11 PM
 */

public class TestRpcClient {
    private static final Logger logger = LoggerFactory.getLogger(TestRpcClient.class);

    private ManagedChannel channel;
    private TestRpcServiceGrpc.TestRpcServiceBlockingStub serviceStub;

    public static void main(String[] args) {
        TestRpcClient client = new TestRpcClient();
        client.init();
        client.run();
    }

    private void run() {
        TestPacketRequest request = TestPacketRequest.newBuilder().setServerId("20").build();
        logger.info("[{} {}]", TimeUtil.now(), request);
        TestPacketResponse response = serviceStub.testMethod(request);
        logger.info("[{} {}]", TimeUtil.now(), response);
    }

    private void init() {
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", 1000).usePlaintext(true).build();
        serviceStub = TestRpcServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
