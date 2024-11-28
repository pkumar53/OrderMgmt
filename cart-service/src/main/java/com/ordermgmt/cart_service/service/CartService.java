package com.ordermgmt.cart_service.service;

import com.ordermgmt.cart_service.dto.CartDto;
import com.ordermgmt.cart_service.dto.Product;
import com.ordermgmt.cart_service.dto.User;
import com.ordermgmt.cart_service.dto.Address;
import com.ordermgmt.cart_service.model.Cart;
import com.ordermgmt.cart_service.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Cart addOrUpdateProductInCart(CartDto cartDto) {
        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(cartDto.getUser().getUserId(), cartDto.getProduct().getProductId());
        Cart cart = null;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(cartDto.getUser().getUserId());
            cart.setProductId(cartDto.getProduct().getProductId());
        }
        cart.setQuantity(cartDto.getQuantity());
        cart.setDiscount(cartDto.getProduct().getDiscount());
        cart.setPrice(cartDto.getProduct().getPricePerQty());
        cart.setTotalPrice(cartDto.getQuantity() *
                (cartDto.getProduct().getPricePerQty() -
                        (cartDto.getProduct().getPricePerQty() * cartDto.getProduct().getDiscount() / 100)));
        return cartRepository.save(cart);
    }

    public List<CartDto> getCartByUserId(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        User user = restTemplate.getForEntity("http://localhost:8082/users/" + userId, User.class).getBody();

        return carts.stream().map(cart -> {
            CartDto cartDto = mapCartToCartDto(cart);
            cartDto.setUser(user);
            return cartDto;
        }).toList();
    }

    public CartDto getCartByUserIdAndProductId(Long userId, Long productId) {
        Optional<Cart> cart = cartRepository.findByUserIdAndProductId(userId, productId);
        return cart.map(this::mapCartToCartDto).orElse(null);
    }

    public CartDto mapCartToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getCartId());
        cartDto.setAddDate(cart.getAddDate());
        cartDto.setQuantity(cart.getQuantity());
        cartDto.setPrice(cart.getPrice());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setDiscount(cart.getDiscount());
        cartDto.setCheckedOut(cart.getCheckedOut());
        cartDto.setCheckOutDate(cart.getCheckOutDate());
        Product product = restTemplate.getForObject("http://localhost:8083/products/" + cart.getProductId(), Product.class);
        cartDto.setProduct(product);
        return cartDto;
    }

    @Transactional
    public void removeProductFromCart(CartDto cartDto) {
        if (cartDto.getQuantity() <= 0) {
            cartRepository.deleteByUserIdAndProductId(cartDto.getUser().getUserId(), cartDto.getProduct().getProductId());
        }
    }

    public void addSelectedProductToCart(CartDto cartDto) {
        //get currently active user///for time being it is 1 only
        Optional<Cart> cartOptional = cartRepository.findByUserIdAndProductId(cartDto.getUser().getUserId(), cartDto.getProduct().getProductId());
        Cart cart = null;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
            cart.setQuantity(cart.getQuantity() + 1);
        } else {
            cart = new Cart();
            cart.setUserId(cartDto.getUser().getUserId());
            cart.setProductId(cartDto.getProduct().getProductId());
            cart.setQuantity(1);
            cart.setDiscount(cartDto.getProduct().getDiscount());
            cart.setPrice(cartDto.getProduct().getPricePerQty());
            cart.setTotalPrice(cartDto.getProduct().getPricePerQty() -
                    (cartDto.getProduct().getPricePerQty() * cartDto.getProduct().getDiscount() / 100));
        }
        cartRepository.save(cart);
    }

    public int getCartCountByUserId(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream().reduce(0, (sum, cart) -> sum + cart.getQuantity(), Integer::sum);
    }

    public Address getShippingAddress(Long userId) {
        return restTemplate.getForEntity("http://localhost:8081/addresses/" + userId + "/shippingAddress", Address.class).getBody();
    }
}
