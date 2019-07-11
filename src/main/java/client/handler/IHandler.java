package client.handler;

/**
 * @author : ddv
 * @since : 2019/7/10 下午10:20
 */

public interface IHandler<T> {

    /**
     * 处理客户端响应包
     *
     * @param t
     */
    void handler(T t);
}
