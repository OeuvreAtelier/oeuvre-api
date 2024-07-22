package com.muffincrunchy.oeuvreapi.model.constant;

import java.util.HashMap;
import java.util.Map;

public enum ItemType {
    DIGITAL("Digital"),
    PHYSICAL("Physical");

    public final String label;
    private ItemType(String label) {
        this.label = label;
    }

    private static final Map<String, ItemType> map;
    static {
        map = new HashMap<String, ItemType>();
        for (ItemType itemType : ItemType.values()) {
            map.put(itemType.label, itemType);
        }
    }

    public static ItemType getByLabel(String label) {
        return map.get(label);
    }
}
