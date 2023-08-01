package uz.pdp.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class City {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

     private String name;

     @JsonIgnore
     @ManyToOne
     private Region region;

     public City(String name, Region region) {
          this.name = name;
          this.region = region;
     }
}
