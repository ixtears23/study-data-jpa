package junseok.snr.studydatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemberDto {

    private Long id;
    private String userName;
    private String teamName;
}
