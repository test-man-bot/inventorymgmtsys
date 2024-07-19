package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.enumeration.PaymentMethod;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("orderconfirm")
    public String orderConfirm(@Validated  OrderInput orderInput, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "order/orderForm";
        }
        sessionController.setOrderInput(orderInput);
        model.addAttribute("orderInput", sessionController.getOrderInput());
        return "order/orderConfirmation";
    }

    @PostMapping(value="placeorder", params = "correct")
    public String correctOrder(@Validated OrderInput orderInput, Model model) {
        model.addAttribute("orderInput", sessionController.getOrderInput());
        return "order/orderForm";
    }

    @PostMapping(value="placeorder", params = "placeorder")
    public String placeOrder(RedirectAttributes redirectAttributes) {
        Order order = orderService.placeOrder(sessionController.getOrderInput(), sessionController.getCartInput(), sessionController.getEmployee() );
        redirectAttributes.addFlashAttribute("order", order);
        sessionController.clearData();
        return "redirect:/order/ordercompletion";
    }


}
