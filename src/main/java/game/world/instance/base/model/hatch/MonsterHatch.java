package game.world.instance.base.model.hatch;

import java.util.ArrayList;
import java.util.List;

import game.map.visible.AbstractMapObject;
import game.map.visible.impl.MonsterMapObject;
import game.world.instance.base.resource.HatchResource;

/**
 * @author : ddv
 * @since : 2019/7/30 10:21 PM
 */

public class MonsterHatch extends BaseMapObjectHatch {

    @Override
    public List<AbstractMapObject> hatch(HatchParam param) {
        List<AbstractMapObject> resultList = new ArrayList<>();
        HatchResource hatchResource = param.getHatchResource();
        for (int i = 0; i < param.getNum(); i++) {
            MonsterMapObject monsterMapObject = MonsterMapObject.valueOf(param.getCreatureResource(), 0, 0);
            int bornX = hatchResource.getBornX();
            int bornY = hatchResource.getBornY();
            monsterMapObject.getCurrentGrid().setX(bornX);
            monsterMapObject.getCurrentGrid().setY(bornY);
            monsterMapObject.getFighterAccount().getCreatureUnit().setMapId(hatchResource.getMapId());
            resultList.add(monsterMapObject);
        }
        return resultList;
    }
}
