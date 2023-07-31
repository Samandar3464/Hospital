package uz.pdp.hospital.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hospital.model.common.ApiResponse;
import uz.pdp.hospital.model.request.HospitalDto;
import uz.pdp.hospital.service.HospitalService;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/market")
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody @Valid HospitalDto dto) {
        return hospitalService.create(dto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return hospitalService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody @Validated HospitalDto dto) {
        return hospitalService.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return hospitalService.delete(id);
    }

    @GetMapping("/getByBusinessId/{id}")
    public ApiResponse getByBusinessId(@PathVariable Integer id) {
        return hospitalService.getByUserId(id);
    }


    @GetMapping("/getAll")
    public ApiResponse getAllBranches(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        return hospitalService.getAll(page, size);
    }

    @GetMapping("/activate")
    public ApiResponse activate(@RequestParam(name = "id") Integer id, @RequestParam(name = "day") LocalDate day) {
        return hospitalService.activate(id, day);
    }

    @GetMapping("/deactivate/{id}")
    public ApiResponse deactivate(@PathVariable Integer id) {
        return hospitalService.deActive(id);
    }


}
