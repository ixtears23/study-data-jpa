package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.dto.MemberDto;
import junseok.snr.studydatajpa.entity.Member;
import junseok.snr.studydatajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    void testMember() {
        final Member member = new Member("memberA");
        final Member savedMember = memberRepository.save(member);
        final Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    void basicCRUD() {
        final Member member1 = new Member("member1");
        final Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        final Member findMember1 = memberRepository.findById(member1.getId()).get();
        final Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        final List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        final long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        final long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        final List<Member> result = memberRepository.findByUserNameAndAgeGreaterThan("AAA", 15);

        final Member member = result.get(0);

        assertThat(member.getUserName()).isEqualTo("AAA");
        assertThat(member.getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testFindHelloBy() {
        final List<Member> top3Hello = memberRepository.findTop3HelloBy();
    }

    @Test
    void testNamedQuery() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        final List<Member> members = memberRepository.findByUserName("AAA");
        final Member member = members.get(0);
        assertThat(member).isEqualTo(m1);
    }

    @Test
    void testFindUser() {
        final Member m1 = new Member("AAA", 10);
        final Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        final List<Member> members = memberRepository.findUser("AAA", 10);
        final Member member = members.get(0);
        assertThat(member).isEqualTo(m1);
    }

    @Test
    void testFindUserName() {

        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<String> userNameList = memberRepository.findUserNameList();

        assertThat(userNameList).hasSize(2);
    }

    @Test
    void testFindMemberDto() {

        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);
        final Team team = new Team("A팀");

        teamRepository.save(team);
        member1.setTeam(team);
        member2.setTeam(team);
        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<MemberDto> memberDto = memberRepository.findMemberDto();

        assertThat(memberDto).hasSize(2);
        final MemberDto memberDto1 = memberDto.get(0);
        final MemberDto memberDto2 = memberDto.get(1);
        assertThat(memberDto1.getUserName()).isEqualTo("AAA");
        assertThat(memberDto1.getTeamName()).isEqualTo("A팀");

        assertThat(memberDto2.getUserName()).isEqualTo("BBB");
        assertThat(memberDto2.getTeamName()).isEqualTo("A팀");

    }



}
