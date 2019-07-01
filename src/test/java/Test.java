import game.user.equip.constant.EquipPosition;
import game.user.equip.entity.EquipStorageEnt;
import game.user.equip.model.Equipment;
import net.utils.ProtoStuffUtil;
import spring.SpringContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : ddv
 * @since : 2019/4/30 下午2:28
 */

public class Test {

    @org.junit.Test
    public void run() {
//		Start.run();
		List<Integer> integerList = new ArrayList<>();
		integerList.add(1);
		integerList.add(2);
		integerList.add(3);
		integerList.add(4);

		integerList.forEach(integer -> {
			if(integer > 2){
				throw new UnsupportedOperationException();
			}
		});
	}

}
