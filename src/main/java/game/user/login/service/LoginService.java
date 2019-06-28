package game.user.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.game.attribute.id.AttributeIdEnum;
import game.common.I18N;
import game.common.exception.RequestException;
import game.common.packet.SM_Message;
import game.user.login.entity.UserEnt;
import game.user.login.event.PlayerLoginBeforeEvent;
import game.user.login.model.Person;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.CM_UserRegister;
import game.user.login.packet.SM_LoginSuccess;
import game.user.player.model.Player;
import middleware.manager.SessionManager;
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

        // 暂时单点会把相同accountId的session干掉 后续sessionManager需要做支持

        // 下发登陆成功状态,注册session
        session.putSessionAttribute("accountId", accountId);
        SessionManager.registerPlayerSession(accountId, session);

        Player player = SpringContext.getPlayerService().getPlayerByAccountId(accountId);
        SpringContext.getEventBus().pushEventSyn(PlayerLoginBeforeEvent.valueOf(player));

        // 重新计算属性
        player.getAttributeContainer().containerRecompute();

        player.getAttributeContainer().putAttributesWithRecompute(AttributeIdEnum.BASE_EQUIPMENT,
            SpringContext.getPlayerService().getResource(2).getAttributeList(), false);

        PacketUtil.send(session, new SM_LoginSuccess());
    }

    @Override
    public void logout(USession session, CM_UserLogout request) {

        // 这里要check一下用户是否已经登录,因为心跳也会调用这个接口,用户可能一直没登录
        String accountId = SimpleUtil.getAccountIdFromSession(session);
        if (accountId == null) {
            SessionManager.removeSession(session.getChannel());
            session.getChannel().close();
            return;
        }

        Player player = SimpleUtil.getPlayerFromSession(session);
        PacketUtil.send(session, SM_Message.valueOf(I18N.OPERATION_SUCCESS));

        SessionManager.removeSession(session.getChannel());
        SessionManager.removePlayerSession(player.getAccountId());

        SpringContext.getSceneMapService().logOut(player);

        session.getChannel().close();
    }

    @Override
    public void register(USession session, CM_UserRegister request) {
        String accountId = request.getAccountId();
        UserEnt userEnt = loginManager.load(accountId);

        if (userEnt != null) {
            RequestException.throwException(I18N.USER_EXIST);
        }

        Person person = Person.valueOf();
        person.setName(request.getName());
        person.setIdCard(request.getIdCard());

        userEnt = UserEnt.valueOf(accountId);
        userEnt.setPassword(request.getPassword());
        userEnt.setUsername(request.getUsername());
        userEnt.setPerson(person);

        loginManager.saveEntity(userEnt);

        PacketUtil.send(session, SM_Message.valueOf(I18N.OPERATION_SUCCESS));
    }

    @Override
    public UserEnt getUserEnt(USession session) {
        return loginManager.load((String)session.getAttributes().get("accountId"));
    }

}
