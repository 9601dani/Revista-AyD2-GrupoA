package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.suscription.AllSuscriptionResponseDto;
import com.codenbugs.ms_user.dtos.suscription.SuscriptionRequestDto;
import com.codenbugs.ms_user.dtos.suscription.SuscriptionResponseDto;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.magazine.Suscription;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.magazine.MagazineRepository;
import com.codenbugs.ms_user.repositories.magazine.SuscriptionRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class SuscriptionServiceImpl implements SuscriptionService {

    private final SuscriptionRepository suscriptionRepository;
    private final UserRepository userRepository;
    private final MagazineRepository magazineRepository;

    @Override
    public SuscriptionResponseDto saveSuscription(SuscriptionRequestDto suscriptionRequestDto) throws UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findById(suscriptionRequestDto.fkUser());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("El usuario no existe");
        }

        Optional<Magazine> magazineOptional = this.magazineRepository.findById(Long.valueOf(suscriptionRequestDto.fkMagazine()));

        if (magazineOptional.isEmpty()) {
            throw new UserNotFoundException("La revista no existe");
        }

        Suscription suscription = new Suscription();
        suscription.setUser(userOptional.get());
        suscription.setMagazine(magazineOptional.get());
        suscription.setPay(suscriptionRequestDto.pay());
        suscription.setDateCreated(LocalDate.now());
        suscription.setDateEnded(LocalDate.now().plusMonths(1));
        suscription.setIsLike(false);
        Suscription savedSuscription = suscriptionRepository.save(suscription);

        return new SuscriptionResponseDto(savedSuscription);

    }

    @Override
    public List<SuscriptionResponseDto> getSuscriptionsByUserId(Integer userId) {

        List<Suscription> suscriptions = this.suscriptionRepository.findByUserId(userId);
        return suscriptions.stream().map(SuscriptionResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AllSuscriptionResponseDto> getSuscriptionsWithMagazineByUserId(Integer userId) {

        List<Suscription> suscriptions = this.suscriptionRepository.findByUserId(userId);
        return suscriptions.stream().map(AllSuscriptionResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public AllSuscriptionResponseDto getSuscriptionById(Integer id) throws UserNotFoundException {
        Optional<Suscription> suscription = this.suscriptionRepository.findById(id);

        if (suscription.isEmpty()) {
            throw new UserNotFoundException("No se encontro la suscripcion");
        }

        return new AllSuscriptionResponseDto(suscription.get());
    }
}
