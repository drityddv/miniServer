package net.model;

import java.util.Arrays;

import middleware.manager.ClazzManager;
import net.utils.ProtoStuffUtil;

/**
 * 数据包结构 序列号 + 数据长度 + 数据
 *
 * @author : ddv
 * @since : 2019/4/26 下午3:05
 */

public class PacketProtocol {
    /**
     * 序列号
     */
    private int id;
    /**
     * 长度
     */
    private int length;
    /**
     * 数据
     */
    private byte[] data;

    /**
     * 使用此方法一定要检查clazz在ClazzManager中有注册
     *
     * @param object
     * @return
     */
    public static PacketProtocol valueOf(Object object) {
        PacketProtocol protocol = new PacketProtocol();
        protocol.setId(ClazzManager.getIdByClazz(object.getClass()));
        protocol.setData(ProtoStuffUtil.serialize(object));
        protocol.setLength(protocol.getData().length);
        return protocol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PacketProtocol{" + "id=" + id + ", length=" + length + ", data=" + Arrays.toString(data) + '}';
    }
}
