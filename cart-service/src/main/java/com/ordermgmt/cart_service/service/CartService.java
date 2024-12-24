package com.ordermgmt.cart_service.service;

import com.ordermgmt.cart_service.dto.CartDto;
import com.ordermgmt.cart_service.dto.Product;
import com.ordermgmt.cart_service.dto.User;
import com.ordermgmt.cart_service.dto.Address;
import com.ordermgmt.cart_service.model.Cart;
import com.ordermgmt.cart_service.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    public CartDto addOrUpdateProductInCart(CartDto cartDto) {
        Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(cartDto.getUser().getUserId(), cartDto.getProduct().getProductId());
        Cart cart = null;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(cartDto.getUser().getUserId());
            cart.setProductId(cartDto.getProduct().getProductId());
//            cart.setAddDate();
        }
        cart.setQuantity(cartDto.getQuantity());
        cart.setDiscount(cartDto.getDiscount());
        cart.setPrice(cartDto.getPrice());
        cart.setDiscountPercentage(cartDto.getDiscountPercentage());
        cart.setDiscount(cartDto.getDiscount());
        cart.setDiscountedPrice(cartDto.getDiscountedPrice());
        CartDto cartDtoRes = mapCartToCartDto(cartRepository.save(cart));
        cartDtoRes.setUser(cartDto.getUser());
        return cartDtoRes;
    }

    public List<CartDto> getCartsByUserId(Long userId) {
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
        cartDto.setDiscount(cart.getDiscount());
        cartDto.setDiscountPercentage(cart.getDiscountPercentage());
        cartDto.setDiscountedPrice(cart.getDiscountedPrice());
        cartDto.setDiscount(cart.getDiscount());
        cartDto.setCheckedOut(cart.getCheckedOut());
        cartDto.setCheckOutDate(cart.getCheckOutDate());
        if (cartDto.getProduct() == null) {
            Product product = restTemplate.getForObject("http://localhost:8083/products/" + cart.getProductId(), Product.class);
            cartDto.setProduct(product);
        }
        return cartDto;
    }

    @Transactional
    public void removeProductFromCart(CartDto cartDto) {
        if (cartDto.getQuantity() <= 0) {
            cartRepository.deleteByUserIdAndProductId(cartDto.getUser().getUserId(), cartDto.getProduct().getProductId());
        }
    }

    /* Called from Medicine page Add button*/
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
        }
        cart.setDiscount(cartDto.getProduct().getDiscount() * cart.getQuantity());
        cart.setPrice(cartDto.getProduct().getPricePerQty() * cart.getQuantity());
        cart.setDiscountPercentage(cartDto.getProduct().getDiscountPercentage());
        cart.setDiscountedPrice(cartDto.getProduct().getDiscountedPrice() * cart.getQuantity());

        cartRepository.save(cart);
    }

    public int getCartCountByUserId(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream().reduce(0, (sum, cart) -> sum + cart.getQuantity(), Integer::sum);
    }

    public Address getShippingAddress(Long userId) {
        return restTemplate.getForEntity("http://localhost:8081/addresses/" + userId + "/shippingAddress", Address.class).getBody();
    }

    public void deleteAllCartsByUserId(Long userId) {
        cartRepository.deleteAllByUserId(userId);
    }
}
