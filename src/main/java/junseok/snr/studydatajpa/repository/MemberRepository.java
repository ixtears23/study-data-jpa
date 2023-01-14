package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
