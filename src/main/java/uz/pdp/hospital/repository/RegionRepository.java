package uz.pdp.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Region;

import java.util.Collection;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByName(String name);
    boolean existsByNameIn(Collection<String> name);
}
