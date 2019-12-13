package micro.match.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;
import micro.match.*;

/**
 *
 * 匹配接口
 *
 * @author : ddv
 * @since : 2019/12/10 9:17 PM
 */

public class MatchServiceImpl extends MatchServiceGrpc.MatchServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);
    /**
     * 错误做法 应该找独立服务存取状态
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void match(MatchRequest request, StreamObserver<MatchResponse> responseObserver) {
        logger.info("玩家[{} {} {}] 请求匹配...", request.getName(), request.getServerId(), request.getLevel());
        MatchResponse response = MatchResponse.newBuilder().setServerId(request.getServerId())
            .setName(request.getName()).setSuccess(true).setMatchId(atomicInteger.getAndIncrement()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void matchCancel(MatchCancelRequest request, StreamObserver<MatchCancelResponse> responseObserver) {
        logger.info("玩家[{} {}] 请求取消匹配...", request.getName(), request.getServerId());
        MatchCancelResponse response = MatchCancelResponse.newBuilder().setServerId(request.getServerId())
            .setName(request.getName()).setSuccess(true).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
