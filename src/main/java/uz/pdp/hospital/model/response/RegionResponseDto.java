package uz.pdp.hospital.model.response;

import lombok.*;
import uz.pdp.hospital.entity.Region;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionResponseDto {

    private Integer id;

    private String name;

    public static RegionResponseDto from(Region region){
        return RegionResponseDto.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
