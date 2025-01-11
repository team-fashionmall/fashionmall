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
public class CouponCancelEventListener {
    private final ModuleApiUtil moduleApiUtil;

    @Async
    @TransactionalEventListener(
            classes = CouponCancelEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void couponCancel(CouponCancelEvent event) {
        try {
            moduleApiUtil.cancelCoupon(event.getCouponId(), event.getUserId());
        } catch (Exception e) {
            log.error("couponCancel error", e);
        }
    }
}