package uz.pdp.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.FireBaseToken;

public interface TokenRepository extends JpaRepository<FireBaseToken, Integer> {
}
