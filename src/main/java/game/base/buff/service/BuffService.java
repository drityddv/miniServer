package game.base.buff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.base.buff.model.BaseCreatureBuff;
import game.base.buff.resource.BuffResource;
import game.base.effect.model.BuffContext;
import game.base.effect.model.BuffContextParamEnum;
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

    public boolean isAllowAddBuff() {
        return false;
    }

    /**
     * 为场景内的单位添加buff
     *
     * @param buffIdList
     * @param targetList
     * @param caster
     */
    public void addBuffInScene(List<Long> buffIdList, BaseCreatureUnit caster, List<BaseCreatureUnit> targetList) {

        buffIdList.forEach(configId -> {

            targetList.forEach(targetUnit -> {
				addBuffSingleUnit(caster, configId, targetUnit);
            });
        });

    }

	public void addBuffSingleUnit(BaseCreatureUnit caster, Long configId, BaseCreatureUnit targetUnit) {
		BuffResource buffResource = getBuffResource(configId);
		BaseCreatureBuff buff = createBuffByConfigId(configId);
		BuffContext context = BuffContext.valueOf(buffResource.getBuffContext());

		context.addParam(BuffContextParamEnum.CASTER, caster);
		context.addParam(BuffContextParamEnum.Target, targetUnit);
		buff.init(buffResource, context);
		// buff开始启动
		buff.buffActive();
	}

}