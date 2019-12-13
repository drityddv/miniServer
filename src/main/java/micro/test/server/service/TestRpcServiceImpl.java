package micro.test.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;
import micro.test.TestRpcService.TestPacketRequest;
import micro.test.TestRpcService.TestPacketResponse;
import micro.test.TestRpcService.TestRpcServiceGrpc;

/**
 * @author : ddv
 * @since : 2019/12/9 4:28 PM
 */

public class TestRpcServiceImpl extends TestRpcServiceGrpc.TestRpcServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(TestRpcServiceGrpc.TestRpcServiceImplBase.class);

    @Override
    public void testMethod(TestPacketRequest request, StreamObserver<TestPacketResponse> responseObserver) {
        logger.info("服务调用,参数serverId [{}]", request.getServerId());
        TestPacketResponse response = TestPacketResponse.newBuilder().setStatsCode(200).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
