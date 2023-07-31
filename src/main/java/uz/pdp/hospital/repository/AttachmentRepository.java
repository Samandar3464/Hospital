package uz.pdp.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.hospital.entity.Attachment;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findByNewName(String newName);
}
