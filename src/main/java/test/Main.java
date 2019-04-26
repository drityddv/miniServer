package test;

import net.model.PacketProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : ddv
 * @since : 2019/4/24 下午2:39
 */

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args){
		PacketProtocol packetProtocol = new PacketProtocol();
		logger.info(packetProtocol.toString());
	}
}
