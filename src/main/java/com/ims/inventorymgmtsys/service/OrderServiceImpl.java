package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.Employee;
import com.ims.inventorymgmtsys.entity.Order;
import com.ims.inventorymgmtsys.entity.OrderDetail;
import com.ims.inventorymgmtsys.entity.Product;
import com.ims.inventorymgmtsys.exception.StockShortageException;
import com.ims.inventorymgmtsys.input.CartInput;
import com.ims.inventorymgmtsys.input.CartItemInput;
import com.ims.inventorymgmtsys.input.OrderInput;
import com.ims.inventorymgmtsys.repository.OrderDetailRepository;
import com.ims.inventorymgmtsys.repository.OrderRepository;
import com.ims.inventorymgmtsys.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;

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
        int billingAmount = caluculateTax(totalAmount);

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


    int calculateTotalAmount(List<CartItemInput> cartItems) {
        int totalAmount = 0;
        for (CartItemInput cartItem : cartItems) {
            totalAmount += (cartItem.getProductPrice() * cartItem.getQuantity());
        }
        return totalAmount;
    }

    int caluculateTax(int price) {
        return (int) (price * 1.1);
    }
}
