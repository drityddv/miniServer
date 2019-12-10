package rpc.test.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;
import rpc.test.TestRpcService.TestPacketRequest;
import rpc.test.TestRpcService.TestPacketResponse;
import rpc.test.TestRpcService.TestRpcServiceGrpc;

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
