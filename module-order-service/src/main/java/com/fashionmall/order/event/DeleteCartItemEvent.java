package com.fashionmall.order.event;

import java.util.List;

public class DeleteCartItemEvent {

    private final List<Long> itemDetailIds;
    private final Long userId;

    public DeleteCartItemEvent(List<Long> itemDetailIds, Long userId) {
        this.itemDetailIds = itemDetailIds;
        this.userId = userId;
    }

    public List<Long> getItemDetailIds() {
        return itemDetailIds;
    }

    public Long getUserId() {
        return userId;
    }
}