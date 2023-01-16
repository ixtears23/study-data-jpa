package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.entity.Member;

import java.util.List;

public interface MemberCustomRepository {
    List<Member> findMemberCustom();
}
