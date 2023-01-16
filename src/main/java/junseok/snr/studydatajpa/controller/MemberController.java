package junseok.snr.studydatajpa.controller;

import junseok.snr.studydatajpa.dto.MemberDto;
import junseok.snr.studydatajpa.entity.Member;
import junseok.snr.studydatajpa.entity.Team;
import junseok.snr.studydatajpa.repository.MemberRepository;
import junseok.snr.studydatajpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        final Member member = memberRepository.findById(id).get();
        return member.getUserName();
    }


    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUserName();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "userName") Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            final Team team = new Team("team" + i);
            teamRepository.save(team);
            memberRepository.save(new Member("user" + i, i, team));
        }
    }
}
