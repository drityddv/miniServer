package game.user.task.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.user.task.model.TaskInfo;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/8/2 3:22 PM
 */

@Entity(name = "task")
public class TaskEnt extends AbstractEntity<String> {

    @Transient
    private TaskInfo taskInfo;
    @Lob
    @Column(columnDefinition = "blob comment '玩家任务数据' ")
    private byte[] taskInfoData;

    @Id
    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8 COLLATE utf8_bin comment '账号Id' ", nullable = false)
    private String accountId;

    public static TaskEnt valueOf(String accountId) {
        TaskEnt ent = new TaskEnt();
        ent.accountId = accountId;
        ent.taskInfo = TaskInfo.valueOf();
        return ent;
    }

    @Override
    public void doSerialize() {
        if (taskInfo != null) {
            taskInfoData = ProtoStuffUtil.serialize(taskInfo);
        }
    }

    @Override
    public void doDeserialize() {
        if (taskInfoData != null) {
            taskInfo = ProtoStuffUtil.deserialize(taskInfoData, TaskInfo.class);
        }
    }

    @Override
    public String getId() {
        return accountId;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public String getAccountId() {
        return accountId;
    }
}
