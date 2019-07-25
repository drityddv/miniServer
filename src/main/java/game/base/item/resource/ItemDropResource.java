package game.base.item.resource;

import java.util.ArrayList;
import java.util.List;

import game.base.item.model.ItemDropParam;
import resource.anno.Init;
import resource.anno.MiniResource;
import resource.constant.CsvSymbol;

/**
 * @author : ddv
 * @since : 2019/7/25 3:41 PM
 */
@MiniResource
public class ItemDropResource {
    private long configId;
    private List<ItemDropParam> dropParamList;
    /**
     * 掉落配置 1:2:40%,6:1:100%
     */
    private String dropDataString;

    @Init
    private void init() {
        dropParamList = new ArrayList<>();
        String[] paramListString = dropDataString.replaceAll(CsvSymbol.Percent, CsvSymbol.Space).split(CsvSymbol.Comma);
        for (String paramList : paramListString) {
            String[] param = paramList.split(CsvSymbol.Colon);
            dropParamList.add(ItemDropParam.valueOf(configId, Long.parseLong(param[0]), Integer.parseInt(param[1]),
                Double.parseDouble(param[2]) / 100));
        }
    }

    public long getConfigId() {
        return configId;
    }

    public List<ItemDropParam> getDropParamList() {
        return dropParamList;
    }
}
