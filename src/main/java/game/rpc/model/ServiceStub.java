package game.rpc.model;

import io.grpc.stub.AbstractStub;

/**
 * @author : ddv
 * @since : 2019/12/13 3:30 PM
 */

public class ServiceStub {

    private AbstractStub blockStub;
    private AbstractStub nonBlockStub;
}
