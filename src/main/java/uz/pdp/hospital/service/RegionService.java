package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.hospital.entity.Region;
import uz.pdp.hospital.exception.RecordAlreadyExistException;
import uz.pdp.hospital.exception.RecordNotFoundException;
import uz.pdp.hospital.model.common.ApiResponse;
import uz.pdp.hospital.model.request.RegionRegisterRequestDto;
import uz.pdp.hospital.repository.RegionRepository;

import java.util.List;
import java.util.Optional;

import static uz.pdp.hospital.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addRegion(RegionRegisterRequestDto regionRegisterRequestDto) {
        Optional<Region> byName = regionRepository.findByName(regionRegisterRequestDto.getName());
        if (byName.isPresent()) {
            throw new RecordAlreadyExistException(REGION_ALREADY_EXIST);
        }
        Region region = Region.builder().name(regionRegisterRequestDto.getName()).build();
        regionRepository.save(region);
        return new ApiResponse(SUCCESSFULLY , true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionList(){
        List<Region> all = regionRepository.findAll();
        return new ApiResponse(all,true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getRegionById(Integer id){
        Region region = regionRepository.findById(id).orElseThrow(()->new RecordNotFoundException(REGION_NOT_FOUND));
        return new ApiResponse(region,true);
    }

    public ApiResponse deleteRegionById(Integer id) {
        regionRepository.deleteById(id);
        return new ApiResponse(DELETED,true);
    }
}
