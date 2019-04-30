package game.user.login.service;

import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.SM_LoginSuccess;
import io.netty.channel.Channel;
import net.model.PacketProtocol;
import net.model.USession;
import net.utils.ProtoStuffUtil;
import org.springframework.stereotype.Component;

/**
 * @author : ddv
 * @since : 2019/4/30 上午11:10
 */
@Component
public class LoginService implements ILoginService {

	@Override
	public void login(USession session, CM_UserLogin request) {

		Channel channel = session.getChannel();
		SM_LoginSuccess sm = new SM_LoginSuccess();
		PacketProtocol protocol = new PacketProtocol();
		protocol.setId(3);
		protocol.setData(ProtoStuffUtil.serialize(sm));
		protocol.setLength(protocol.getData().length);

		session.putSessionAttribute("accountId", request.getAccountId());

		channel.writeAndFlush(protocol);
	}
}
