package net.model;

import java.util.Arrays;

/**
 * 数据包结构
 * 序列号 + 数据长度 + 数据
 *
 * @author : ddv
 * @since : 2019/4/26 下午3:05
 */

public class PacketProtocol {
	/**
	 * 序列号
	 */
	private byte id;
	/**
	 * 长度
	 */
	private int length;
	/**
	 * 数据
	 */
	private byte[] data;

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
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
		return "PacketProtocol{" +
				"id=" + id +
				", length=" + length +
				", data=" + Arrays.toString(data) +
				'}';
	}
}
