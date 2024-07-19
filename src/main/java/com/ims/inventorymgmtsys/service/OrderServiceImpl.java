package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.entity.OrderDetail;
import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.exception.StockShortageException;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.repository.EmployeeRepository;
import com.ims.inventorymgmtsys.repository.OrderDetailRepository;
import com.ims.inventorymgmtsys.repository.OrderRepository;
import com.ims.inventorymgmtsys.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final EmployeeRepository employeeRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderDetailRepository orderDetailRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.employeeRepository = employeeRepository;

    }

    @Override
    public Order placeOrder(OrderInput orderInput, CartInput cartInput, Employee employee) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setCustomerName(orderInput.getName());
        order.setEmployeeName(employee.getEmployeeName());
        order.setPaymentMethod(orderInput.getPaymentMethod());

        int totalAmount = calculateTotalAmount(cartInput.getCartItemInputs());
        int billingAmount = calculateTax(totalAmount);

        orderRepository.insert(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemInput cartItem : cartInput.getCartItemInputs()) {
            Product product = productRepository.selectById(cartItem.getProductId());
            int afterstock = product.getStock() - cartItem.getQuantity();

            if ( afterstock < 0) {
                throw new StockShortageException("在庫が足りません");
            }

            product.setStock(afterstock);
            productRepository.update(product);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setProductId(product.getId());
            orderDetail.setQuantity(cartInput.getTotalAmount());

            orderDetailRepository.insert(orderDetail);
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        return order;
    }


    @Override
    public int calculateTotalAmount(List<CartItemInput> cartItemInputs) {
        int totalAmount = 0;
        for (CartItemInput cartItem : cartItemInputs) {
            totalAmount += (cartItem.getProductPrice() * cartItem.getQuantity());
        }
        return totalAmount;
    }

    @Override
    public int calculateTax(int price) {
        return (int) (price * 1.1);
    }
}
