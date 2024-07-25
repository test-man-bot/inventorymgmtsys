package com.ims.inventorymgmtsys.controller;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.enumeration.PaymentMethod;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.service.EmployeeService;
import com.ims.inventorymgmtsys.service.OrderService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class  OrderController {
    private final OrderService orderService;
    private final SessionController sessionController;
    private final EmployeeService employeeService;


    public OrderController(OrderService orderService, SessionController sessionController, EmployeeService employeeService) {
        this.orderService = orderService;
        this.sessionController = sessionController;
        this.employeeService = employeeService;
    }

    @GetMapping("/orderform")
    public String orderForm(Model model) {
        OrderInput orderInput = new OrderInput();
        orderInput.setPaymentMethod(PaymentMethod.BANK);

        List<Employee> employees = employeeService.selectAll();

        // Employeeリストの内容をログに出力
        for (Employee employee : employees) {
            System.out.println("Employee ID: " + employee.getEmployeeId() + ", Employee Name: " + employee.getEmployeeName());
        }

        model.addAttribute("employees", employees);
        model.addAttribute("orderInput", orderInput);
        return "order/orderForm";
    }

    @PostMapping("orderconfirm")
    public String orderConfirm(@Validated  @ModelAttribute("orderInput") OrderInput orderInput, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "order/orderForm";
        }

        String employeeId = orderInput.getEmployeeId();
//        if (employeeId == null || employeeId.trim().isEmpty()) {
//            bindingResult.rejectValue("employeeId", "error.employeeId", "Invalid employee selected");
//            return "order/orderForm";
//        }

        Employee employee = employeeService.selectById(employeeId);
        orderInput.setEmployeeName(employee.getEmployeeName());
        orderInput.setEmployeeId(employee.getEmployeeId());
//        try {
//            employee = employeeService.selectById(employeeId);
//            orderInput.setEmployeeName(employee.getEmployeeName());
//            orderInput.setEmployeeId(employee.getEmployeeId());
//            System.out.println("Employee Name set to: " + orderInput.getEmployeeName() + orderInput.getEmployeeId()); // Debugging log
//        } catch (EmptyResultDataAccessException e) {
//            bindingResult.rejectValue("employeeId", "error.employeeId", "Invalid employee selected");
//            return "order/orderForm";
//        }

        if (employee == null) {
            bindingResult.rejectValue("employeeId", "error.employeeId", "Invalid employee selected");
            model.addAttribute("employees", employeeService.selectAll());
            return "order/orderForm";
        }

        sessionController.setOrderInput(orderInput);
        sessionController.setEmployee(employee);
//        sessionController.setOrderInput(orderInput);
//        Employee employee = employeeService.selectById(employeeId);
//        sessionController.setEmployee(employee);
        model.addAttribute("cartInput", sessionController.getCartInput());
        model.addAttribute("orderInput", sessionController.getOrderInput());
        model.addAttribute("employee", employee.getEmployeeName());
        return "order/orderConfirmation";
    }

    @PostMapping(value="placeorder", params = "correct")
    public String correctOrder(@Validated OrderInput orderInput, Model model) {
        List<Employee> employees = employeeService.selectAll();
        model.addAttribute("employees", employees);
        model.addAttribute("orderInput", sessionController.getOrderInput());
        return "order/orderForm";
    }

    @PostMapping(value = "placeorder", params = "correctproduct")
    public String correctProduct(@Validated CartInput cartInput, Model model) {
        model.addAttribute("cartInput", sessionController.getCartInput());
        return "cart/cartItem";
    }

    @PostMapping(value="placeorder", params = "placeorderconfirm")
    public String placeOrder(RedirectAttributes redirectAttributes) {
        Employee employee = sessionController.getEmployee();

        Order order = orderService.placeOrder(sessionController.getOrderInput(), sessionController.getCartInput(), employee );
        redirectAttributes.addFlashAttribute("order", order);
        sessionController.clearData();
        return "redirect:/order/orderCompletion";
    }

    @GetMapping("/orderCompletion")
    public String orderCompletion(Model model) {
//        model.addAttribute("")
        return "order/orderCompletion";
    }


}
