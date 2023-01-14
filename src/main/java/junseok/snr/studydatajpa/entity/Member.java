package junseok.snr.studydatajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    protected Member() {
    }

    public Member(String userName) {
        this.userName = userName;
    }

    @Id @GeneratedValue
    private Long id;
    private String userName;

}
