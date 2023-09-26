package example.domain.user.repository;

import example.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	// email을 통해 이미 가입된 사용자인지 확인

	List<User> findTop10ByOrderByLovelyCountDesc();
}
