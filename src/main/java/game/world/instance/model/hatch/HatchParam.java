package game.world.instance.model.hatch;

import java.util.List;

import game.world.base.resource.CreatureResource;
import game.world.base.service.CreatureManager;
import game.world.instance.resource.HatchResource;

/**
 * 单位孵化器参数
 *
 * @author : ddv
 * @since : 2019/7/30 10:24 PM
 */

public class HatchParam {
    private BaseMapObjectHatch hatch;
    private CreatureResource creatureResource;
    private HatchResource hatchResource;

    public static HatchParam valueOf(HatchResource hatchResource) {
        HatchParam param = new HatchParam();
        param.creatureResource = CreatureManager.getInstance().getCreatureResource(hatchResource.getObjectId());
        param.hatch = MapHatchEnum.getById(param.creatureResource.getHatchTypeId()).getHatch();
        param.hatchResource = hatchResource;
        return param;
    }

    public CreatureResource getCreatureResource() {
        return creatureResource;
    }

    public void setCreatureResource(CreatureResource creatureResource) {
        this.creatureResource = creatureResource;
    }

    public BaseMapObjectHatch getHatch() {
        return hatch;
    }

    public <T> List<T> hatch() {
        return (List<T>)hatch.hatch(this);
    }

    public int getNum() {
        return hatchResource.getNum();
    }

    public HatchResource getHatchResource() {
        return hatchResource;
    }
}
