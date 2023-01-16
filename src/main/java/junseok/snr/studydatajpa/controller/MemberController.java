package junseok.snr.studydatajpa.controller;

import junseok.snr.studydatajpa.entity.Member;
import junseok.snr.studydatajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        final Member member = memberRepository.findById(id).get();
        return member.getUserName();
    }


    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUserName();
    }


    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));
    }
}
