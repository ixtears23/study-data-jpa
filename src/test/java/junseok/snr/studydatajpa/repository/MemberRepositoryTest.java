package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.dto.MemberDto;
import junseok.snr.studydatajpa.entity.Member;
import junseok.snr.studydatajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    void testFindByNames() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<String> memberNames = Stream.of("AAA", "BBB")
                .collect(Collectors.toList());

        final List<Member> members = memberRepository.findByNames(memberNames);

        assertThat(members).hasSize(2);
        assertThat(members.get(0).getUserName()).isEqualTo("AAA");
        assertThat(members.get(1).getUserName()).isEqualTo("BBB");
    }

    @Test
    void testReturnType() {
        final Member member1 = new Member("AAA", 10);
        final Member member2 = new Member("BBB", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        final List<Member> members = memberRepository.findListByUserName("AAA");
        final Member member = memberRepository.findMemberByUserName("AAA");
        final Optional<Member> memberOptional = memberRepository.findOptionalMemberByUserName("AAA");

        assertThat(members).hasSize(1);
        assertThat(members.get(0)).isEqualTo(member1);
        assertThat(member).isEqualTo(member1);
        assertThat(memberOptional.get()).isEqualTo(member1);

    }

    @Test
    void paging() {

        final List<String> userNames = Arrays.asList("member1", "member2", "member3", "member4", "member5", "member6", "member7", "member8", "member9", "member10");

        memberRepository.save(new Member(userNames.get(0), 10));
        memberRepository.save(new Member(userNames.get(1), 10));
        memberRepository.save(new Member(userNames.get(2), 10));
        memberRepository.save(new Member(userNames.get(3), 10));
        memberRepository.save(new Member(userNames.get(4), 10));
        memberRepository.save(new Member(userNames.get(5), 10));
        memberRepository.save(new Member(userNames.get(6), 10));
        memberRepository.save(new Member(userNames.get(7), 10));
        memberRepository.save(new Member(userNames.get(8), 10));
        memberRepository.save(new Member(userNames.get(9), 10));

        final int age = 10;
        final Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "userName"));

        final Page<Member> page = memberRepository.findByAge(age, pageable);

        final Page<MemberDto> memberDtos = page.map(member -> new MemberDto(member.getId(), member.getUserName(), null));


        final List<Member> members = page.getContent();
        final long totalElements = page.getTotalElements();

        assertThat(members).hasSize(3);
        assertThat(totalElements).isEqualTo(10);
        assertThat(page.getNumber()).isZero();
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.isFirst()).isTrue();
    }

}
