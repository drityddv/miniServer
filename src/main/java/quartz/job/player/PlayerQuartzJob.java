package quartz.job.player;

import java.util.Set;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import game.role.player.model.Player;

/**
 * @author : ddv
 * @since : 2019/7/9 上午12:03
 */

public class PlayerQuartzJob implements Job {

    private Player player;
    private long playerId;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        Set<String> strings = jobDataMap.keySet();
        Object player = context.get("player");
        System.out.println(playerId);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
