package vladislavmaltsev.paymenttaskapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vladislavmaltsev.paymenttaskapi.dto.UserDTO;
import vladislavmaltsev.paymenttaskapi.entity.User;
import vladislavmaltsev.paymenttaskapi.repository.UserPaymentRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static vladislavmaltsev.paymenttaskapi.util.MappingDTOClass.mapDTOAndClass;

@Service
public class UserPaymentService implements UserDetailsService {

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentService(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Transactional
    public UserDTO getMoney(int id) {
        UserDTO userDTO =
                mapDTOAndClass(
                        userPaymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " does not exists")),
                        UserDTO.class).orElseThrow();

        //logic
        userDTO.setUsd(userDTO.getUsd().subtract(new BigDecimal("1.1")));

        return save(userDTO).orElseThrow();
    }

//    @Transactional
//    public User getUser(int id) {
//        return userPaymentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id + "not exists"));
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var u = userPaymentRepository.findByName(username);
        System.out.println(u.orElseThrow());
        return userPaymentRepository.findByName(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getName(),
                        user.getPass(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new NoSuchElementException(username + " does not exists"));
    }

    public Optional<UserDTO> save(UserDTO userParametersDTO) {
        return Optional.ofNullable(
                        mapDTOAndClass(
                                userPaymentRepository.save(
                                        Objects.requireNonNull(mapDTOAndClass(userParametersDTO,
                                                User.class).orElseThrow())
                                ),
                                UserDTO.class))
                .orElseThrow();
    }
}
