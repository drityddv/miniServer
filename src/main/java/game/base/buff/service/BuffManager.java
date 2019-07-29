package game.base.buff.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import game.base.buff.resource.BuffResource;
import resource.anno.Static;

/**
 * @author : ddv
 * @since : 2019/7/24 10:42 AM
 */
@Component
public class BuffManager {

    private static BuffManager instance;

    @Static
    private Map<Long, BuffResource> buffResourceMap;

    public static BuffManager getInstance() {
        return instance;
    }

    @PostConstruct
    public void init() {
        instance = this;
    }

    public BuffResource getBuffResourceById(long configId) {
        return buffResourceMap.get(configId);
    }
}
