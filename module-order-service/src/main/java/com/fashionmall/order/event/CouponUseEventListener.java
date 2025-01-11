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
public class CouponUseEventListener {
    private final ModuleApiUtil moduleApiUtil;

    @Async
    @TransactionalEventListener(
            classes = CouponUseEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void couponUse(CouponUseEvent event) {
        try {
            moduleApiUtil.useCoupon(event.getCouponId(), event.getUserId());
        } catch (Exception e) {
            log.error("couponUse error", e);
        }
    }
}