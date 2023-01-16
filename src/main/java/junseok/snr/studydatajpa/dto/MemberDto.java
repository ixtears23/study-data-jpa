package junseok.snr.studydatajpa.dto;

import junseok.snr.studydatajpa.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemberDto {

    private Long id;
    private String userName;
    private String teamName;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUserName();
        this.teamName = member.getTeam().getName();
    }
}
