package game.rpc.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import game.role.player.model.Player;
import game.rpc.model.ServiceStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import micro.constant.MicroServiceEnum;
import micro.match.MatchRequest;
import micro.match.MatchResponse;
import micro.match.MatchServiceGrpc;
import micro.service.GetSynRemoteServiceRequest;
import micro.service.MicroServiceGrpc;
import micro.service.getSynRemoteServiceRequestResponse;
import utils.TimeUtil;

/**
 * @author : ddv
 * @since : 2019/12/13 2:31 PM
 */

@Component
public class RpcService implements IRpcService {
    private static final Logger logger = LoggerFactory.getLogger(RpcService.class);

    // 服务中心 客户端存根都是线程安全的
    private ManagedChannel channel;

    private MicroServiceGrpc.MicroServiceBlockingStub microServiceFutureStub;

    private Map<MicroServiceEnum, ServiceStub> stubMap = new ConcurrentHashMap<>();

    @Override
    public void remoteCall(Player player, MicroServiceEnum serviceEnum, Object requestPacket) {
        GetSynRemoteServiceRequest request =
            GetSynRemoteServiceRequest.newBuilder().setServerName(serviceEnum.getServerName()).build();
        long start = TimeUtil.now();
        getSynRemoteServiceRequestResponse response = microServiceFutureStub.getService(request);
        logger.info("服务寻找阻塞调用 耗时[{}]ms", TimeUtil.now() - start);
        if (!response.getSuccess()) {
            logger.warn("服务[{}]当前不可用!", serviceEnum.getServerName());
            return;
        }

        if (serviceEnum == MicroServiceEnum.match_service) {
            MatchServiceGrpc.MatchServiceBlockingStub matchStub = MatchServiceGrpc.newBlockingStub(
                ManagedChannelBuilder.forAddress(response.getIp(), response.getPort()).usePlaintext(true).build());

            MatchResponse matchResponse = matchStub.match((MatchRequest)requestPacket);
            logger.info("玩家[{}] 匹配结果[{}]", player.getAccountId(), matchResponse);
        }

    }

    @Override
    public void init() {
        channel = ManagedChannelBuilder.forAddress("172.19.0.6", 1000).usePlaintext(true).build();
        microServiceFutureStub = MicroServiceGrpc.newBlockingStub(channel);
    }
}
