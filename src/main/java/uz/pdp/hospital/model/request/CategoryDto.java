package uz.pdp.hospital.model.request;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {


    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Integer parentCategoryId;
}
