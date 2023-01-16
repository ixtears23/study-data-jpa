package junseok.snr.studydatajpa.entity;

import junseok.snr.studydatajpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void testEntity() {
        final Team teamA = new Team("teamA");
        final Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        final Member member1 = new Member("member1", 10, teamA);
        final Member member2 = new Member("member2", 20, teamA);
        final Member member3 = new Member("member3", 30, teamB);
        final Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        final List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }

    @Test
    void jpaEventBaseEntity() throws InterruptedException {
        final Member member = new Member("member1");
        memberRepository.save(member); // @PrePersist

        Thread.sleep(1000);

        member.setUserName("member2");

        em.flush(); // @PreUpdate
        em.clear();


        final Member findMember = memberRepository.findById(member.getId()).get();

        System.out.println("createdDate : " + findMember.getCreatedDate());
        System.out.println("updatedDate : " + findMember.getLastModifiedDate());
        System.out.println("updatedDate : " + findMember.getCreatedBy());
        System.out.println("updatedDate : " + findMember.getLastModifiedBy());

    }

}