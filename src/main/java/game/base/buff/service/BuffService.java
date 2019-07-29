package game.base.buff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.model.BuffContext;
import game.base.buff.model.BuffParamEnum;
import game.base.buff.resource.BuffResource;
import game.base.fight.model.pvpunit.BaseCreatureUnit;

/**
 * @author : ddv
 * @since : 2019/7/24 10:42 AM
 */
@Component
public class BuffService {

    @Autowired
    private BuffManager buffManager;

    /**
     * 创建buff 自行初始化
     *
     * @param configId
     * @return
     */
    public BaseCreatureBuff createBuffByConfigId(long configId) {
        return buffManager.getBuffResourceById(configId).createBuff();
    }

    public BuffResource getBuffResource(long configId) {
        return buffManager.getBuffResourceById(configId);
    }

    /**
     * 为场景内的单位添加buff
     *
     * @param buffIdList
     * @param targetList
     * @param caster
     */
    public void addBuffInScene(List<Long> buffIdList, BaseCreatureUnit caster, List<BaseCreatureUnit> targetList) {
        if (targetList == null) {
            return;
        }
        targetList.forEach(targetUnit -> {
            buffIdList.forEach(configId -> {
                addBuffSingleUnit(caster, configId, targetUnit);
            });
        });
    }

    public void addBuffSingleUnit(BaseCreatureUnit caster, Long configId, BaseCreatureUnit targetUnit) {
        BuffResource buffResource = getBuffResource(configId);
        BaseCreatureBuff buff = createBuffByConfigId(configId);
        BuffContext context = BuffContext.valueOf(buffResource.getBuffConfig());

        context.addParam(BuffParamEnum.CASTER, caster);
        context.addParam(BuffParamEnum.Target, targetUnit);
        buff.init(buffResource, context);
        // buff开始启动
        buff.buffActive();
    }

}
