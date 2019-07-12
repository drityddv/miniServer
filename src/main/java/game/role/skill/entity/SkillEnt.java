package game.role.skill.entity;

import javax.persistence.*;

import db.model.AbstractEntity;
import game.role.skill.model.SkillList;
import utils.ProtoStuffUtil;

/**
 * @author : ddv
 * @since : 2019/7/12 10:51 AM
 */
@Entity(name = "skill")
public class SkillEnt extends AbstractEntity<Long> {
    @Id
    @Column(columnDefinition = "bigint comment '角色id' ", nullable = false)
    private long playerId;

    @Transient
    private SkillList skillList;

    @Lob
    @Column(columnDefinition = "blob comment '玩家技能信息' ")
    private byte[] skillData;

    public static SkillEnt valueOf(long playerId) {
        SkillEnt ent = new SkillEnt();
        ent.playerId = playerId;
        ent.skillList = SkillList.valueOf();
        return ent;
    }

    @Override
    public void doSerialize() {
        if (skillList != null) {
            skillData = ProtoStuffUtil.serialize(skillList);
        }
    }

    @Override
    public void doDeserialize() {
        if (skillData != null) {
            skillList = ProtoStuffUtil.deserialize(skillData, SkillList.class);
        }
    }

    @Override
    public Long getId() {
        return playerId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public SkillList getSkillList() {
        return skillList;
    }
}
