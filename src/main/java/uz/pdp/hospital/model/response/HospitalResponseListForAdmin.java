package uz.pdp.hospital.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.hospital.entity.Hospital;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalResponseListForAdmin {

    private List<Hospital> marketResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
