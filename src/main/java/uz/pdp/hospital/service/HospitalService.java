package uz.pdp.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.hospital.entity.City;
import uz.pdp.hospital.entity.Hospital;
import uz.pdp.hospital.exception.RecordNotFoundException;
import uz.pdp.hospital.model.common.ApiResponse;
import uz.pdp.hospital.model.request.HospitalDto;
import uz.pdp.hospital.model.response.HospitalResponseListForAdmin;
import uz.pdp.hospital.repository.CityRepository;
import uz.pdp.hospital.repository.HospitalRepository;

import java.time.LocalDate;
import java.util.Optional;

import static uz.pdp.hospital.enums.Constants.*;


@RequiredArgsConstructor
@Service
public class HospitalService implements BaseService<HospitalDto, Integer> {

    private final HospitalRepository hospitalRepository;
    private final CityRepository cityRepository;

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(HospitalDto dto) {
        Optional<Hospital> byName = hospitalRepository.findByName(dto.getName());
        if (byName.isPresent()) {
            throw new RecordNotFoundException(MARKET_NAME_ALREADY_EXIST);
        }
        City city = cityRepository.findById(dto.getCityId()).orElseThrow(() -> new RecordNotFoundException(CITY_NOT_FOUND));
        Hospital marketNew = Hospital.from(dto,city);
        hospitalRepository.save(marketNew);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getById(Integer integer) {
        Hospital market = hospitalRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(market, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse update(HospitalDto dto) {
        Hospital market = hospitalRepository.findById(dto.getId()).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setName(market.getName());
        market.setLongitude(dto.getLongitude());
        market.setLatitude(dto.getLatitude());
        hospitalRepository.save(market);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse delete(Integer integer) {
        Hospital market = hospitalRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setDelete(true);
        market.setActive(false);
        hospitalRepository.save(market);
        return new ApiResponse(DELETED, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getByUserId(Integer integer) {
        Hospital market = hospitalRepository.findByIdAndDeleteFalse(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        return new ApiResponse(market, true);
    }

    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hospital> all = hospitalRepository.findAllByDeleteFalse(pageable);
        return new ApiResponse(new HospitalResponseListForAdmin(
                all.getContent(), all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    public ApiResponse deActive(Integer integer) {
        Hospital market = hospitalRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setActive(false);
        hospitalRepository.save(market);
        return new ApiResponse(market, true);
    }

    public ApiResponse activate(Integer integer, LocalDate newActiveDay) {
        Hospital market = hospitalRepository.findById(integer).orElseThrow(() -> new RecordNotFoundException(MARKET_NOT_FOUND));
        market.setActive(true);
        market.setActiveDay(newActiveDay);
        hospitalRepository.save(market);
        return new ApiResponse(market, true);
    }
}
