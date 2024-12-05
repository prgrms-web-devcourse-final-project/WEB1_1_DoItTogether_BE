package com.doittogether.platform.domain.enumeration;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;

public enum HouseworkCategory {
    LIVING_ROOM("거실"),
    KITCHEN("주방"),
    BATHROOM("욕실"),
    BED_ROOM("침실"),
    OTHER("기타");

    private final String displayName;

    HouseworkCategory(String displayName) {
        this.displayName = displayName;
    }

    public static HouseworkCategory parse(final String displayName) {
        for (HouseworkCategory category : HouseworkCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new HouseworkException(ExceptionCode.CATEGORY_NOT_FOUND);
    }

    public String getDisplayName() {
        return displayName;
    }
}
