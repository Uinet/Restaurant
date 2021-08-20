package com.github.uinet.project.services;

import com.github.uinet.project.domain.OrderStatus;
import com.github.uinet.project.domain.Orders;
import com.github.uinet.project.domain.User;
import com.github.uinet.project.exception.UserException;
import com.github.uinet.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrdersService ordersService;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->{
            log.error("Email not found");
            return new UsernameNotFoundException("Email: " + username + "not found");
        });
    }

    public Optional<User> registerNewUser(User user) throws UserException {
        try {
            return Optional.of(userRepository.save(user));
        }catch (Exception exception){
            log.error("User is exist");
            throw new UserException("User is exist " + exception.getMessage());
        }
    }

    @Transactional
    public void buyOrder(Long userId, Orders orders) throws UserException {
        User user = userRepository.getById(userId);
        if(user.getMoney() >= orders.getTotalPrice()){
            user.setMoney(user.getMoney() - orders.getTotalPrice());
            userRepository.save(user);
            ordersService.changeStatus(orders.getId(), OrderStatus.PAID);
        }
        else{
            log.error("Money not enough");
            throw new UserException("Not enough money");
        }
    }

    public Page<User> findPaginated(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public double topUpBalance(Long userId, double money){
        User user = userRepository.getById(userId);
        user.setMoney(user.getMoney() + money);
        userRepository.save(user);
        return user.getMoney();
    }
}
