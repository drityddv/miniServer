package game.user.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.executor.util.ExecutorUtils;
import game.base.manager.SessionManager;
import game.base.message.I18N;
import game.base.message.exception.RequestException;
import game.base.message.packet.SM_Message;
import game.role.player.entity.PlayerEnt;
import game.role.player.model.Player;
import game.user.login.entity.UserEnt;
import game.user.login.event.PlayerLogoutEvent;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.CM_UserRegister;
import game.user.login.packet.SM_LoginSuccess;
import game.world.base.command.scene.EnterMapCommand;
import io.netty.channel.Channel;
import net.model.USession;
import net.utils.PacketUtil;
import spring.SpringContext;
import utils.SimpleUtil;

/**
 * @author : ddv
 * @since : 2019/4/30 上午11:10
 */
@Component
public class LoginService implements ILoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LoginManager loginManager;

    @Override
    public void login(USession session, CM_UserLogin request) {

        String accountId = request.getAccountId();
        String password = request.getPassword();

        UserEnt userEnt = loginManager.load(accountId);

        if (userEnt == null) {
            RequestException.throwException(I18N.USER_NOT_EXIST);
        }

        if (!userEnt.getPassword().equals(password)) {
            RequestException.throwException(I18N.PASSWORD_ERROR);
        }

        // 如果有同账号session 挤掉那个session
        USession oldSession = SessionManager.getSessionByAccountId(accountId);
        if (oldSession != null) {
            Channel channel = oldSession.getChannel();
            SessionManager.removePlayerSession(accountId);
            SessionManager.removeSession(channel);
            channel.close();
        }

        // 下发登陆成功状态,注册session
        session.putSessionAttribute("accountId", accountId);
        SessionManager.registerPlayerSession(accountId, session);

        Player player = SpringContext.getPlayerService().loadPlayer(accountId);
        PacketUtil.send(session, SM_LoginSuccess.valueOf());

        ExecutorUtils.submit(EnterMapCommand.valueOf(player, 1, 1));
    }

    @Override
    public void logout(USession session, CM_UserLogout request) {

        // 这里要check一下用户是否已经登录,因为心跳也会调用这个接口,用户可能一直没登录
        String accountId = SimpleUtil.getAccountIdFromSession(session);

        // 客户端一直没登录直接把连接关掉
        if (accountId == null) {
            SessionManager.removeSession(session.getChannel());
            session.getChannel().close();
            return;
        }

        // 如果没创建角色就不用抛事件了 因为做业务都是基于player
        PlayerEnt playerEnt = SpringContext.getPlayerService().getPlayerWithoutCreate(accountId);
        if (playerEnt != null) {
            SpringContext.getEventBus().pushEventSyn(PlayerLogoutEvent.valueOf(playerEnt.getPlayer()));
        }

        PacketUtil.send(session, SM_Message.valueOf(I18N.FORCED_OFFLINE));
        SessionManager.removeSession(session.getChannel());
        SessionManager.removePlayerSession(accountId);
        session.getChannel().close();
    }

    @Override
    public void register(USession session, CM_UserRegister request) {
        String accountId = request.getAccountId();
        UserEnt userEnt = loginManager.load(accountId);
        if (userEnt != null) {
            RequestException.throwException(I18N.USER_EXIST);
        }

        userEnt = UserEnt.valueOf(request.getAccountId(), request.getPassword(), request.getUsername(),
            request.getName(), request.getIdCard());
        loginManager.saveEntity(userEnt);
        PacketUtil.send(session, SM_Message.valueOf(I18N.OPERATION_SUCCESS));
    }

    @Override
    public UserEnt getUserEnt(USession session) {
        return loginManager.load((String)session.getAttributes().get("accountId"));
    }

}
