package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.enumeration.PaymentMethod;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class  OrderController {
    private final OrderService orderService;
    private final SessionController sessionController;

    public OrderController(OrderService orderService, SessionController sessionController) {
        this.orderService = orderService;
        this.sessionController = sessionController;
    }

    @GetMapping("/orderform")
    public String orderForm(Model model) {
        OrderInput orderInput = new OrderInput();
        orderInput.setPaymentMethod(PaymentMethod.BANK);
        model.addAttribute("orderInput", orderInput);
        return "order/orderForm";
    }


}
