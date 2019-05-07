package game.user.login.service;

import game.common.Ii8n;
import game.common.exception.RequestException;
import game.user.login.entity.UserEnt;
import game.user.login.model.Person;
import game.user.login.packet.CM_UserLogin;
import game.user.login.packet.CM_UserLogout;
import game.user.login.packet.CM_UserRegister;
import game.user.login.packet.SM_LoginSuccess;
import net.model.PacketProtocol;
import net.model.USession;
import net.utils.PacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
			RequestException.throwException(Ii8n.USER_NOT_EXIST);
		}

		if (!userEnt.getPassword().equals(password)) {
			RequestException.throwException(Ii8n.PASSWORD_ERROR);
		}

		// 暂时单点会把相同accountId的session干掉 后续sessionManager需要做支持

		// 下发登陆成功状态,注册session
		session.putSessionAttribute("accountId", accountId);
		PacketUtil.send(session, new SM_LoginSuccess());
	}

	@Override
	public void logout(USession session, CM_UserLogout request) {

	}

	@Override
	public void register(USession session, CM_UserRegister request) {
		logger.info("register invoked ...");
		String accountId = request.getAccountId();

		UserEnt userEnt = loginManager.load(accountId);

		if (userEnt != null) {
			RequestException.throwException(Ii8n.USER_EXIST);
		}

		Person person = Person.valueOf();
		person.setName(request.getName());
		person.setIdCard(request.getIdCard());

		userEnt = UserEnt.valueOf(accountId);
		userEnt.setPassword(request.getPassword());
		userEnt.setUsername(request.getUsername());
		userEnt.setPerson(person);

		loginManager.saveEntity(userEnt);
	}

	@Override
	public UserEnt getUserEnt(USession session) {
		return loginManager.load((String) session.getAttributes().get("accountId"));
	}
}
