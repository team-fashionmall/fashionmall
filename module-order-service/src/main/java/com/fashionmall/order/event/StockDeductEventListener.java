package com.fashionmall.order.event;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockDeductEventListener {
    private final OrdersRepository ordersRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Async
    @EventListener
    public void stockDeduct(StockDeductEvent event) {
        Long ordersId = event.getOrdersId();

        try {
            List<OrderItemDto> orderItemsByOrderId = ordersRepository.findOrderItemsByOrderId(ordersId);
            moduleApiUtil.deductItemQuantityApi(orderItemsByOrderId);
        } catch (Exception e) {
            log.error("stockDeduct error", e);
        }
    }
}
