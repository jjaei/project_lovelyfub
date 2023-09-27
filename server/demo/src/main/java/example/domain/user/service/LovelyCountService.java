package example.domain.user.service;

import example.domain.user.entity.User;
import example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LovelyCountService {

    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron ="0 0 1 1 * ?") // 매월 1일 00:00 초기화
    public void resetLovelyCount() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setLovelyCount(0l);
        }
        userRepository.saveAll(users);
    }
}
