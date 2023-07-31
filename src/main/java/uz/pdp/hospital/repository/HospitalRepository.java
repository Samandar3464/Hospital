package uz.pdp.hospital.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    Optional<Hospital> findByName(String name);

    List<Hospital> findAllByActiveTrueAndDeleteFalse();
    Optional<Hospital> findByIdAndDeleteFalse(Integer id);

    Page<Hospital> findAllByDeleteFalse(Pageable pageable);
}
