package com.fashionmall.order.event;

import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockRestoreEventListener {
    private final ModuleApiUtil moduleApiUtil;

    @Async
    @TransactionalEventListener(
            classes = StockRestoreEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void stockRestore(StockRestoreEvent event) {
        try {
            moduleApiUtil.restoreItemStockApi(event.getOrderItems());
        } catch (Exception e) {
            log.error("stockRestore error", e);
        }
    }
}