package com.codenbugs.ms_user.services.magazine;

import com.codenbugs.ms_user.dtos.report.CommentReportDto;
import com.codenbugs.ms_user.dtos.report.CommentReportRequestDto;
import com.codenbugs.ms_user.dtos.report.SuscriptionReportDto;
import com.codenbugs.ms_user.dtos.report.SuscriptionReportRequestDto;
import com.codenbugs.ms_user.dtos.suscription.*;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Comment;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.magazine.Suscription;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.magazine.CommentRepository;
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
import java.time.LocalDateTime;
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
    private final CommentRepository commentRepository;

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

    @Override
    public SuscriptionResponseDto updateIsLike(SuscriptionLikeRequest request) throws UserNotFoundException {

        Optional<Suscription> optionalSus = this.suscriptionRepository.findById(request.id());

        if (optionalSus.isEmpty()) {
            throw new UserNotFoundException("La suscription no existe");
        }

        Suscription suscription = optionalSus.get();
        suscription.setIsLike(request.isLike());

        Suscription savedSuscription = suscriptionRepository.save(suscription);

        return new SuscriptionResponseDto(savedSuscription);
    }

    @Override
    public CommentMagazineResponse saveComment(CommentRequest request) throws UserNotFoundException {

        Optional<Suscription> optionalSuscription = this.suscriptionRepository.findById(request.fkSuscription());
        if (optionalSuscription.isEmpty()) {
            throw new UserNotFoundException("No se encontro la suscripcion");
        }

        Optional<Magazine> optionalMagazine = this.magazineRepository.findById(Long.valueOf(request.fkMagazine()));
        if (optionalMagazine.isEmpty()) {
            throw new UserNotFoundException("La revista no existe");
        }

        Comment comment = new Comment();
        comment.setContent(request.content());
        comment.setSuscription(optionalSuscription.get());
        comment.setMagazine(optionalMagazine.get());
        comment.setDateCreated(LocalDateTime.now());

        Comment savedComent = this.commentRepository.save(comment);

        return new CommentMagazineResponse(savedComent);
    }

    @Override
    public List<CommentReportDto> getCommentReport(CommentReportRequestDto request) {
        if (request.magazineId() != null) {
            return commentRepository.findCommentsInDateRangeByMagazine(
                    request.start(), request.end(), request.magazineId()
            );
        } else {
            return commentRepository.findCommentsInDateRange(
                    request.start(), request.end()
            );
        }
    }

    @Override
    public List<SuscriptionReportDto> getReport(SuscriptionReportRequestDto request) {
        return suscriptionRepository.findSuscriptionsByFilters(
                request.startDate(),
                request.endDate(),
                request.authorId(),
                request.magazineId()
        );
    }
}
