package game.base.game.attribute;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.base.game.attribute.computer.IAttributeComputer;
import game.base.game.attribute.computer.impl.GeneralAttributeComputer;

/**
 * @author : ddv
 * @since : 2019/6/20 下午10:21
 */

public enum AttributeType {

    /**
     * 最大生命值
     */
    MAX_HP(1, "最大生命值", 1, GeneralAttributeComputer.class) {
        // @Override
        // public AttributeType[] getEffectAttrs() {
        // return new AttributeType[] {MAX_HP};
        // }

        @Override
        public AttributeType[] getCalculateRateAttributes() {
            return new AttributeType[] {MAX_HP_RATE};
        }
    },
    /**
     * 最大法力值
     */
    MAX_MP(2, "最大法力值", 1, GeneralAttributeComputer.class) {
        // @Override
        // public AttributeType[] getCalculateRateAttributes() {
        // return new AttributeType[] {MAX_MP_RATE};
        // }
    },

    /**
     * 物理攻击上限
     */
    PHYSICAL_ATTACK_UPPER(3, "物理攻击上限", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {BASIC_ATTACK, PHYSICAL_ATTACK_UPPER};
        }

        @Override
        public AttributeType[] getCalculateRateAttributes() {
            return new AttributeType[] {PHYSICAL_ATTACK_RATE};
        }
    },

    /**
     * 物理攻击上限
     */
    PHYSICAL_ATTACK_LOWER(4, "物理攻击下限", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {BASIC_ATTACK, PHYSICAL_ATTACK_LOWER};
        }

        @Override
        public AttributeType[] getCalculateRateAttributes() {
            return new AttributeType[] {PHYSICAL_ATTACK_RATE};
        }
    },

    /**
     * 法术攻击上限
     */
    MAGIC_ATTACK_UPPER(5, "法术攻击上限", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {BASIC_ATTACK, MAGIC_ATTACK_UPPER};
        }

        @Override
        public AttributeType[] getCalculateRateAttributes() {
            return new AttributeType[] {MAGIC_ATTACK_RATE};
        }
    },

    /**
     * 法术攻击下限
     */
    MAGIC_ATTACK_LOWER(6, "法术攻击下限", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {BASIC_ATTACK, MAGIC_ATTACK_LOWER};
        }

        @Override
        public AttributeType[] getCalculateRateAttributes() {
            return new AttributeType[] {MAGIC_ATTACK_RATE};
        }
    },
    /**
     * 物理护甲
     */
    PHYSICAL_ARMOR(7, "物理护甲", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {PHYSICAL_ARMOR};
        }
    },
    /**
     * 法术护甲
     */
    MAGIC_ARMOR(8, "法术护甲", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {MAGIC_ARMOR};
        }
    },
    /**
     * 基础攻击力 所有攻击属性都享受这个加成
     */
    BASIC_ATTACK(9, "基础攻击", 1, GeneralAttributeComputer.class) {
        @Override
        public AttributeType[] getCalculateAttrs() {
            return new AttributeType[] {BASIC_ATTACK};
        }
    },

    // ======================= 60 - 80 百分比属性 暂时上限和下限使用一个百分比加成属性
    MAX_HP_RATE(60, "最大生命值万分比加成", 3) {
        @Override
        public AttributeType[] getEffectAttrs() {
            return new AttributeType[] {MAX_HP};
        }
    },

    MAX_MP_RATE(61, "最大法力值万分比加成", 3) {
        @Override
        public AttributeType[] getEffectAttrs() {
            return new AttributeType[] {MAX_MP};
        }
    },

    MAGIC_ATTACK_RATE(62, "法术攻击力百分比加成", 3) {
        @Override
        public AttributeType[] getEffectAttrs() {
            return AttributeTypeConst.MAGIC_ATTACK_LIST;
        }
    },

    PHYSICAL_ATTACK_RATE(63, "物理攻击力百分比加成", 3) {
        @Override
        public AttributeType[] getEffectAttrs() {
            return AttributeTypeConst.PHYSICAL_ATTACK_LIST;
        }
    },

    ;

    // FIXME
    private final static Map<String, AttributeType> NAME_TO_TYPE = new HashMap<>(values().length);
    private final static Map<Integer, AttributeType> ID_TO_TYPE = new HashMap<>(values().length);
    private final static List<IAttributeComputer> COMPUTERS = new ArrayList<>(values().length);
    private final static AttributeType[] CACHE_ATTRIBUTES = AttributeType.values();

    static {
        AttributeType[] values = AttributeType.values();
        for (AttributeType type : values) {
            NAME_TO_TYPE.put(type.name(), type);
            ID_TO_TYPE.putIfAbsent(type.getTypeId(), type);
            if (type.attributeComputer != null) {
                COMPUTERS.add(type.attributeComputer);
            }
        }
    }

    private int typeId;
    private String typeName;
    private int attrType;
    private IAttributeComputer attributeComputer;

    AttributeType(int typeId, String typeName, int attrType, Class<? extends IAttributeComputer> computerClazz) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.attrType = attrType;
        this.attributeComputer = createComputer(computerClazz);
    }

    AttributeType(int typeId, String typeName, int attrType) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.attrType = attrType;
    }

    public static AttributeType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    // ============================== static ==============================

    public static AttributeType getByName(String name) {
        return NAME_TO_TYPE.get(name);
    }

    public static List<IAttributeComputer> getComputers() {
        return COMPUTERS;
    }

    /**
     * 获取对此属性影响的属性
     *
     * @return
     */
    public AttributeType[] getEffectAttrs() {
        return new AttributeType[] {};
    }

    /**
     * 获取同时需要累计计算的属性 物理攻击下限=[物理攻击力+物理攻击力下限]
     *
     * @return
     */
    public AttributeType[] getCalculateAttrs() {
        return new AttributeType[] {this};
    }

    /**
     * 获取对此属性有加成的其他百分比属性 物理攻击下限: [攻击系数提升]
     *
     * @return
     */
    public AttributeType[] getCalculateRateAttributes() {
        return new AttributeType[] {};
    }

    public void alter(Attribute attribute, long value) {
        long resultValue = this.alter(attribute.getValue(), value);
        attribute.setValue(resultValue);
    }

    public long alter(long attributeValue, long value) {
        return attributeValue + value;
    }

    // ============================ get and set

    private IAttributeComputer createComputer(Class<? extends IAttributeComputer> computerClazz) {
        IAttributeComputer iAttributeComputer = null;
        try {
            Constructor<?>[] constructors = computerClazz.getConstructors();
            for (Constructor<?> constructor : constructors) {
                if (constructor.getParameterCount() >= 1) {
                    Constructor<? extends IAttributeComputer> computerClazzConstructor =
                        computerClazz.getConstructor(AttributeType.class);
                    return computerClazzConstructor.newInstance(this);
                } else {
                    iAttributeComputer = computerClazz.newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iAttributeComputer;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getAttrType() {
        return attrType;
    }

    public void setAttrType(int attrType) {
        this.attrType = attrType;
    }

    public IAttributeComputer getAttributeComputer() {
        return attributeComputer;
    }

    public void setAttributeComputer(IAttributeComputer attributeComputer) {
        this.attributeComputer = attributeComputer;
    }

}
