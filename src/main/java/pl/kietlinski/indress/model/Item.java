package pl.kietlinski.indress.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Size(max = 43)
    @Column(name = "item_name")
    private String name;
    @Column(name = "item_brand")
    private String brand;
    @Column(name = "item_size")
    private String size;
    @Column(name = "item_condition")
    private String condition;
    @Column(name = "item_color")
    private String color;
    @Column(name = "item_addedData")
    private LocalDateTime addedData;
    @Column(name = "item_description")
    private String description;
    @Column(name = "item_price")
    private BigDecimal price;
    @Column(name = "item_img")
    private String img;
    @Column(name = "item_main_category")
    private String mainCategory;
    @Column(name = "item_clothes")
    private String clothes;


    public Item(@Size(max = 43) String name, String brand, String size, String condition, String color, LocalDateTime addedData, String description, BigDecimal price, String img, String mainCategory, String clothes) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.condition = condition;
        this.color = color;
        this.addedData = addedData;
        this.description = description;
        this.price = price;
        this.img = img;
        this.mainCategory = mainCategory;
        this.clothes = clothes;
    }
}
