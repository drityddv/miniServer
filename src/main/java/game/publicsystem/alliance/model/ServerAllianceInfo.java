package game.publicsystem.alliance.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/8/4 1:35 AM
 */

public class ServerAllianceInfo {

    private long serverId;

    private Map<Long, Alliance> allianceMap = new HashMap<>();

    public ServerAllianceInfo() {

    }

    public static ServerAllianceInfo valueOf(long serverId) {
        ServerAllianceInfo allianceInfo = new ServerAllianceInfo();
        allianceInfo.serverId = serverId;
        return allianceInfo;
    }

    public void init() {

    }

    public void addAlliance(Alliance alliance) {
        allianceMap.put(alliance.getAllianceId(), alliance);
    }

    public Alliance getAlliance(long allianceId) {
        return allianceMap.get(allianceId);
    }

    public long getServerId() {
        return serverId;
    }

    public Map<Long, Alliance> getAllianceMap() {
        return allianceMap;
    }
}
