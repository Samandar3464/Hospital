package uz.pdp.hospital.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hospital.model.common.ApiResponse;
import uz.pdp.hospital.model.request.RegionRegisterRequestDto;
import uz.pdp.hospital.service.RegionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
public class RegionController {

     private final RegionService regionService;
     @PostMapping("/add")
     @PreAuthorize("hasRole('ADMIN')")
     public ApiResponse addRegion(@RequestBody RegionRegisterRequestDto regionRegisterRequestDto) {
          return regionService.addRegion(regionRegisterRequestDto);
     }

     @GetMapping("/getRegionList")
     public ApiResponse getRegionList() {
          return regionService.getRegionList();
     }

     @GetMapping("/regionById/{id}")
     public ApiResponse getRegionById(@PathVariable Integer id){
          return regionService.getRegionById(id);
     }

     @DeleteMapping("/delete/{id}")
     @PreAuthorize("hasAnyRole('ADMIN')")
     public ApiResponse deleteRegionById(@PathVariable Integer id) {
          return regionService.deleteRegionById(id);
     }
}
