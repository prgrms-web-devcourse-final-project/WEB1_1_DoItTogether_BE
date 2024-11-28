package com.doittogether.platform.business.housework;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.doittogether.platform.business.channel.ChannelValidator;
import com.doittogether.platform.domain.entity.Assignee;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.entity.HouseworkCategory;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.ProfileImageRepository;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.housework.AssigneeRepository;
import com.doittogether.platform.infrastructure.persistence.housework.HouseworkRepository;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HouseworkServiceImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private HouseworkRepository houseworkRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private HouseworkValidator houseworkValidator;

    @Mock
    private ChannelValidator channelValidator;

    @Mock
    private ProfileImageRepository profileImageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HouseworkServiceImpl houseworkService;

    Long houseworkId;
    Long channelId;
    Long userId;

    @BeforeEach
    void setUp() {
        houseworkId = 1L;
        channelId = 1L;
    }


    @Test
    void 집안일_추가_성공() {
        userId = 1L;
        HouseworkRequest request = mock(HouseworkRequest.class);
        Channel channel = mock(Channel.class);
        Assignee assignee = mock(Assignee.class);
        User user = mock(User.class);

        when(request.userId()).thenReturn(userId);
        when(request.startDateTime()).thenReturn(LocalDateTime.now());
        when(request.task()).thenReturn("Task Name");
        when(request.category()).thenReturn(HouseworkCategory.LIVING_ROOM);

        when(entityManager.getReference(Channel.class, channelId)).thenReturn(channel);
        when(entityManager.getReference(Assignee.class, userId)).thenReturn(assignee);
        when(assignee.retrieveUser()).thenReturn(user);

        houseworkService.addHousework(channelId, request);

        verify(channelValidator).validateExistChannel(channelId); // 유효성 검증 호출 확인
        verify(houseworkRepository).save(any(Housework.class)); // 저장 호출 확인
    }


    @Test
    void 집안일_수정_성공() {
        Long houseworkId = 1L;
        Long channelId = 1L;
        Long userId = 1L;

        User loginUser = mock(User.class);
        HouseworkRequest request = mock(HouseworkRequest.class);
        Housework housework = mock(Housework.class);
        Assignee assignee = mock(Assignee.class);

        when(request.userId()).thenReturn(userId);

        when(entityManager.getReference(Housework.class, houseworkId)).thenReturn(housework);
        when(assigneeRepository.findByUserUserId(userId)).thenReturn(assignee);
        when(housework.update(request, assignee)).thenReturn(housework);

        houseworkService.updateHousework(loginUser, houseworkId, channelId, request);

        verify(channelValidator).validateExistChannel(channelId);
        verify(houseworkValidator).validateExistHousework(houseworkId);
        verify(houseworkValidator).validateEditableUser(housework, loginUser);
        verify(houseworkRepository).save(housework);
    }


    @Test
    void 집안일삭제_성공() {
        Long houseworkId = 1L;
        Long channelId = 1L;
        User loginUser = mock(User.class);
        Housework housework = mock(Housework.class);

        when(houseworkRepository.findById(houseworkId)).thenReturn(Optional.of(housework));

        houseworkService.deleteHousework(loginUser, houseworkId, channelId);

        verify(channelValidator).validateExistChannel(channelId); // 채널 유효성 검증 호출 확인
        verify(houseworkValidator).validateExistHousework(houseworkId); // 존재 확인 호출 확인
        verify(houseworkValidator).validateEditableUser(housework, loginUser); // 권한 검증 호출 확인
        verify(houseworkRepository).delete(housework); // 삭제 호출 확인
    }

//    @Test
//    void 집안일삭제_존재하지않는_채널_예외발생() {
//        doThrow(new HouseworkException(ExceptionCode.CHANNEL_NOT_FOUND))
//                .when(channelValidator)
//                .validateExistChannel(channelId);
//        Exception exception = assertThrows(HouseworkException.class, () -> {
//            houseworkService.deleteHousework(loginUser, houseworkId, channelId);
//        });
//        assertThat(exception.getMessage()).isEqualTo(ExceptionCode.CHANNEL_NOT_FOUND.getMessage());
//        verify(houseworkRepository, times(0)).save(housework);
//    }
//
//    @Test
//    void 집안일삭제_집안일없음_예외발생() {
//        assertThrows(HouseworkException.class,
//                () -> houseworkService.deleteHousework(loginUser, houseworkId, channelId));
//        verify(houseworkRepository, times(0)).delete(housework);
//    }
//
//    @Test
//    void 집안일삭제_집안일_객체_NULL_예외발생() {
//        when(houseworkRepository.save(housework)).thenThrow(IllegalArgumentException.class);
//        Exception exception = assertThrows(HouseworkException.class, () -> {
//            houseworkService.deleteHousework(loginUser, houseworkId, channelId);
//        });
//        assertThat(exception.getMessage()).isEqualTo(ExceptionCode.HOUSEWORK_NOT_NULL.getMessage());
//        verify(houseworkRepository, times(0)).save(housework);
//    }
//
//    @Test
//    void 집안일삭제_집안일_객체_UNEXPECTED_예외발생() {
//        when(houseworkRepository.save(housework)).thenThrow(Exception.class);
//        Exception exception = assertThrows(GlobalException.class, () -> {
//            houseworkService.deleteHousework(loginUser, houseworkId, channelId);
//        });
//        assertThat(exception.getMessage()).isEqualTo(ExceptionCode._INTERNAL_SERVER_ERROR.getMessage());
//        verify(houseworkRepository, times(0)).save(housework);
//    }
}
