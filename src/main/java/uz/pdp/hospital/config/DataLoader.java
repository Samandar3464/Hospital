package uz.pdp.hospital.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.hospital.entity.Hospital;
import uz.pdp.hospital.entity.Role;
import uz.pdp.hospital.entity.User;
import uz.pdp.hospital.enums.Gender;
import uz.pdp.hospital.repository.CategoryRepository;
import uz.pdp.hospital.repository.HospitalRepository;
import uz.pdp.hospital.repository.RoleRepository;
import uz.pdp.hospital.repository.UserRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.hospital.enums.Constants.*;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final HospitalRepository hospitalRepository;
    private final CategoryRepository categoryRepository;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) {


        if (initMode.equals("always")) {

            Role supperAdmin = Role.builder().id(1).name(SUPER_ADMIN).build();
            Role admin = Role.builder().id(2).name(ADMIN).build();
            Role user = Role.builder().id(3).name(USER).build();
            Role seller = Role.builder().id(4).name(SELLER).build();

            roleRepository.saveAll(List.of(supperAdmin, admin, user, seller));

            User superAdmin = User.builder()
                    .fullName("Super Admin")
                    .phoneNumber("111111111")
                    .birthDate(LocalDate.parse("1998-05-13"))
                    .gender(Gender.ERKAK)
                    .registeredDate(LocalDateTime.now())
                    .verificationCode(0)
                    .password(passwordEncoder.encode("111111"))
                    .isBlocked(true)
                    .role(supperAdmin)
                    .build();
            userRepository.save(superAdmin);


            User user1 = User.builder()
                    .fullName("Seller")
                    .phoneNumber("111111113")
                    .birthDate(LocalDate.parse("1998-05-13"))
                    .gender(Gender.ERKAK)
                    .registeredDate(LocalDateTime.now())
                    .verificationCode(0)
                    .password(passwordEncoder.encode("111111"))
                    .isBlocked(true)
                    .role(seller)
                    .build();
            userRepository.save(user1);

            Hospital market = Hospital.builder()
                    .name("Test Branch")
                    .delete(false)
                    .active(true)
                    .build();
            Hospital saveMarket = hospitalRepository.save(market);

            User admin1 = User.builder()
                    .fullName(" Admin")
                    .phoneNumber("111111112")
                    .birthDate(LocalDate.parse("1998-05-13"))
                    .gender(Gender.ERKAK)
                    .registeredDate(LocalDateTime.now())
                    .verificationCode(0)
                    .password(passwordEncoder.encode("111111"))
                    .isBlocked(true)
                    .hospital(saveMarket)
                    .role(admin)
                    .build();
            User saveAdmin = userRepository.save(admin1);

        }
    }
}