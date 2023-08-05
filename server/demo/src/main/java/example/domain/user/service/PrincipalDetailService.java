package example.domain.user.service;

import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PrincipalDetailService  implements UserDetailsService {

    private  final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPicture())
                .roles(user.get().getRole().toString())
                .build();
    }
}