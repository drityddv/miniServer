package micro.test.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListenableFuture;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import micro.constant.MicroServiceEnum;
import micro.match.MatchServiceGrpc;
import micro.service.MicroServiceGrpc;
import micro.service.RegisteServiceRequest;
import micro.service.RegisteServiceResponse;
import micro.test.TestRpcService.TestRpcServiceGrpc;

/**
 * @author : ddv
 * @since : 2019/12/9 5:11 PM
 */

public class TestRpcClient {
    private static final Logger logger = LoggerFactory.getLogger(TestRpcClient.class);

    private ManagedChannel channel;
    private TestRpcServiceGrpc.TestRpcServiceBlockingStub serviceStub;
    private MatchServiceGrpc.MatchServiceFutureStub matchServiceFutureStub;
    private MicroServiceGrpc.MicroServiceFutureStub microServiceFutureStub;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        TestRpcClient client = new TestRpcClient();
        client.init();
        client.run();
    }

    private void run() {
        // TestPacketRequest request = TestPacketRequest.newBuilder().setServerId("20").build();
        // logger.info("[{} {}]", TimeUtil.now(), request);
        // TestPacketResponse response = serviceStub.testMethod(request);
        // logger.info("[{} {}]", TimeUtil.now(), response);

        RegisteServiceRequest request = RegisteServiceRequest.newBuilder().setId("1").setIp("10.9.6.80").setPort(2000)
            .setServiceName(MicroServiceEnum.match_service.name()).build();
        ListenableFuture<RegisteServiceResponse> future = microServiceFutureStub.registerService(request);
        future.addListener(() -> {
            try {
                logger.info("服务注册回调 [{}] [{}]", request, future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", 1000).usePlaintext(true).build();
        microServiceFutureStub = MicroServiceGrpc.newFutureStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
