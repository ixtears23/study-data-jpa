package junseok.snr.studydatajpa.repository;

import junseok.snr.studydatajpa.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

    @Test
    void save() {
        final Item item = new Item("A");
        itemRepository.save(item);
    }

}