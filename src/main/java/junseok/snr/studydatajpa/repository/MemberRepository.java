package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);

    List<Member> findTop3HelloBy();

//    @Query(name = "Member.findByUserName")
    List<Member> findByUserName(@Param("userName") String userName);

}
