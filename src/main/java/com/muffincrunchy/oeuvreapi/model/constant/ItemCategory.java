package com.muffincrunchy.oeuvreapi.model.constant;

import java.util.HashMap;
import java.util.Map;

public enum ItemCategory {
    AUDIO("Audio"),
    COSPLAY("Cosplay"),
    FASHION("Fashion"),
    FIGURES("Figures, Plushies, & Dolls"),
    GAMES("Games"),
    GOODS("Goods"),
    ILLUSTRATION("Illustration"),
    NOVEL_BOOKS("Novel & Books"),
    MUSIC("Music"),
    PHOTOGRAPH("Photograph"),
    SOFTWARE_HARDWARE("Software & Hardware"),
    VIDEO("Video");

    public final String label;
    private ItemCategory(String label) {
        this.label = label;
    }

    private static final Map<String, ItemCategory> map;
    static {
        map = new HashMap<String, ItemCategory>();
        for (ItemCategory itemCategory : ItemCategory.values()) {
            map.put(itemCategory.label, itemCategory);
        }
    }

    public static ItemCategory getByLabel(String label) {
        return map.get(label);
    }
}