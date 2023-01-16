package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
