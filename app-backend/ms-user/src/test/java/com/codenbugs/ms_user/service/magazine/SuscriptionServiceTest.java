package com.codenbugs.ms_user.service.magazine;

import com.codenbugs.ms_user.dtos.suscription.*;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.magazine.Comment;
import com.codenbugs.ms_user.models.magazine.Magazine;
import com.codenbugs.ms_user.models.magazine.Suscription;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.magazine.CommentRepository;
import com.codenbugs.ms_user.repositories.magazine.MagazineRepository;
import com.codenbugs.ms_user.repositories.magazine.SuscriptionRepository;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.services.magazine.SuscriptionService;
import com.codenbugs.ms_user.services.magazine.SuscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SuscriptionServiceTest {

    private SuscriptionService suscriptionService;

    @Mock
    private SuscriptionRepository suscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MagazineRepository magazineRepository;

    @Mock
    private CommentRepository commentRepository;

    User user;
    Magazine magazine;
    Suscription suscription;
    SuscriptionRequestDto suscriptionRequestDto;
    private final Integer ID_SUSCRIPTION = 1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        suscriptionService = new SuscriptionServiceImpl(suscriptionRepository, userRepository, magazineRepository, commentRepository);

        user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setEmail("email");

        magazine = new Magazine();
        magazine.setId(1);
        magazine.setUser(user);

        suscription = new Suscription();
        suscription.setId(1);
        suscription.setUser(user);
        suscription.setMagazine(magazine);
        suscription.setIsLike(false);
        suscription.setPay(BigDecimal.valueOf(100));

        suscriptionRequestDto = new SuscriptionRequestDto(1,1,BigDecimal.valueOf(100));
    }

    @Test
    void saveSuscriptionSuccess() throws UserNotFoundException {

        // act
        when(this.userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(this.magazineRepository.findById(Long.valueOf(magazine.getId()))).thenReturn(Optional.of(magazine));

        when(this.suscriptionRepository.save(any(Suscription.class))).thenReturn(suscription);

        SuscriptionResponseDto expect = new SuscriptionResponseDto(suscription);

        SuscriptionResponseDto actual = this.suscriptionService.saveSuscription(suscriptionRequestDto);

        assertEquals(expect, actual);

    }

    @Test
    void saveSuscriptionFailureUserNotFound() throws UserNotFoundException {


        when(this.userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> suscriptionService.saveSuscription(suscriptionRequestDto));
    }

    @Test
    void saveSuscriptionFailureMagazineNotFound() throws UserNotFoundException {


        when(this.userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(this.magazineRepository.findById(Long.valueOf(magazine.getId()))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> suscriptionService.saveSuscription(suscriptionRequestDto));
    }

    @Test
    void getSuscriptionsByUserId(){

        List<Suscription> suscriptions = List.of(suscription);

        List<SuscriptionResponseDto> expect = suscriptions.stream().map(SuscriptionResponseDto::new).collect(Collectors.toList());

        when(this.suscriptionRepository.findByUserId(user.getId())).thenReturn(suscriptions);

        List<SuscriptionResponseDto> actual = this.suscriptionService.getSuscriptionsByUserId(user.getId());

        assertEquals(expect, actual);

    }

    @Test
    void getSuscriptionsWithMagazineByUserId(){

        List<Suscription> suscriptions = List.of(suscription);

        List<AllSuscriptionResponseDto> expect = suscriptions.stream().map(AllSuscriptionResponseDto::new).collect(Collectors.toList());

        when(this.suscriptionRepository.findByUserId(1)).thenReturn(suscriptions);

        List<AllSuscriptionResponseDto> actual = this.suscriptionService.getSuscriptionsWithMagazineByUserId(1);

        assertEquals(expect, actual);

    }

    @Test
    void getSuscriptionByIdSuccess() throws UserNotFoundException {

        when(this.suscriptionRepository.findById(ID_SUSCRIPTION)).thenReturn(Optional.of(suscription));

        AllSuscriptionResponseDto expect = new AllSuscriptionResponseDto(suscription);

        AllSuscriptionResponseDto actual = this.suscriptionService.getSuscriptionById(ID_SUSCRIPTION);

    }

    @Test
    void getSuscriptionByIdFailure() throws UserNotFoundException {

        when(this.suscriptionRepository.findById(ID_SUSCRIPTION)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> suscriptionService.getSuscriptionById(ID_SUSCRIPTION));

    }

    @Test
    void updateIsLikeSuccess() throws UserNotFoundException {

        SuscriptionLikeRequest request = new SuscriptionLikeRequest(1, true);

        when(this.suscriptionRepository.findById(request.id())).thenReturn(Optional.of(suscription));

        suscription.setIsLike(request.isLike());

        when(this.suscriptionRepository.save(any(Suscription.class))).thenReturn(suscription);

        SuscriptionResponseDto expect = new SuscriptionResponseDto(suscription);

        SuscriptionResponseDto actual = this.suscriptionService.updateIsLike(request);

        assertEquals(expect.isLike(), actual.isLike());
    }

    @Test
    void updateIsLikeFailure() throws UserNotFoundException {

        SuscriptionLikeRequest request = new SuscriptionLikeRequest(1, true);

        when(this.suscriptionRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> suscriptionService.updateIsLike(request));

    }

    @Test
    void saveCommentSuccess() throws UserNotFoundException {

        CommentRequest request = new CommentRequest(1,1,"content");

        when(this.suscriptionRepository.findById(request.fkSuscription())).thenReturn(Optional.of(suscription));

        when(this.magazineRepository.findById(Long.valueOf(magazine.getId()))).thenReturn(Optional.of(magazine));

        Comment comment = new Comment();
        comment.setContent("content");
        comment.setSuscription(suscription);
        comment.setMagazine(magazine);
        comment.setDateCreated(LocalDateTime.now());

        when(this.commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentMagazineResponse expect = new CommentMagazineResponse(comment);

        CommentMagazineResponse actual = this.suscriptionService.saveComment(request);

        assertEquals(expect, actual);
    }

    @Test
    void saveCommentFailureSuscriptionNofFound() throws UserNotFoundException {

        CommentRequest request = new CommentRequest(1,1,"content");

        when(this.suscriptionRepository.findById(request.fkSuscription())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> suscriptionService.saveComment(request));
    }

    @Test
    void saveCommentFailureMagazinenNofFound() throws UserNotFoundException {

        CommentRequest request = new CommentRequest(1,1,"content");

        when(this.suscriptionRepository.findById(request.fkSuscription())).thenReturn(Optional.of(suscription));

        when(this.magazineRepository.findById(Long.valueOf(magazine.getId()))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> suscriptionService.saveComment(request));
    }
}
