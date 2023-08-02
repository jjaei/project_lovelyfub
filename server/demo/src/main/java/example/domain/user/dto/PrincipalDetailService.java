package example.domain.user.dto;

import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalDetailService  implements UserDetailsService {

    private  final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).get();
        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        UserDetails result= org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPicture())
                .roles(user.getRole().toString())
                .build();

        return result;
    }

}