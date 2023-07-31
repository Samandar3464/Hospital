package uz.pdp.hospital.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.hospital.model.request.HospitalDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    private boolean delete;

    private boolean active;

    private long longitude;

    private long latitude;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate activeDay;

    public static Hospital from(HospitalDto dto) {
        return Hospital.builder()
                .name(dto.getName())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .activeDay(dto.getActiveDay())
                .delete(false)
                .active(true)
                .build();
    }
}