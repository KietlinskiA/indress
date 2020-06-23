package pl.kietlinski.indress.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kietlinski.indress.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findById(long id);

    @Query(value = "SELECT * FROM items WHERE item_name LIKE %:itemName% " +
            "OR item_description LIKE %:itemName% ", nativeQuery = true)
    List<Item> findByName(@Param("itemName") String itemName);


}
