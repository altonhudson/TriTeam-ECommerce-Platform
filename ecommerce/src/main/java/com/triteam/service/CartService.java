package com.triteam.service;

import com.triteam.entity.Order;
import com.triteam.entity.OrderItem;
import com.triteam.entity.Product;
import com.triteam.repository.OrderItemRepository;
import com.triteam.repository.OrderRepository;
import com.triteam.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CartService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public CartService(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
            ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProductToCart(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found."));

        Order activeOrder = orderRepository.findByStatus("CART")
                .orElseGet(() -> {
                    Order newOrder = new Order();
                    newOrder.setStatus("CART");
                    newOrder.setOrderNumber("ORD-" + System.currentTimeMillis());
                    newOrder.setOrderDate(LocalDateTime.now());
                    newOrder.setTotalAmount(0.0);
                    return orderRepository.save(newOrder);
                });

        OrderItem orderItem = orderItemRepository.findByOrderAndProduct(activeOrder, product)
                .orElseGet(() -> {
                    OrderItem newItem = new OrderItem();
                    newItem.setOrder(activeOrder);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    newItem.setUnitPrice(product.getUnitPrice());
                    return newItem;
                });

        orderItem.setQuantity(orderItem.getQuantity() + 1);
        orderItem.setSubtotal(orderItem.getQuantity() * orderItem.getUnitPrice());
        orderItemRepository.save(orderItem);
    }

    @Transactional
    public void confirmOrder(String fullName, String phone, String address, String city, String postalCode,
            String paymentMethod, double finalTotal) {

        // Retrieve the current unsubmitted cart
        Order activeOrder = orderRepository.findByStatus("CART")
                .orElseThrow(() -> new RuntimeException("No active cart found to confirm."));

        // Change status to complete the checkout (the next time a user adds an item,
        // addProductToCart will generate a brand new "CART" order)
        activeOrder.setStatus("PROCESSING");
        activeOrder.setOrderDate(LocalDateTime.now());
        activeOrder.setTotalAmount(finalTotal);
        activeOrder.setFullName(fullName);
        activeOrder.setPhone(phone);
        activeOrder.setShippingAddress(address);
        activeOrder.setCity(city);
        activeOrder.setPostalCode(postalCode);
        activeOrder.setPaymentMethod(paymentMethod);
        orderRepository.save(activeOrder);
    }
}
