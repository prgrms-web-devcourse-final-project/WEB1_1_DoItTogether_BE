package com.doittogether.platform.infrastructure.persistence.housework;

import com.doittogether.platform.domain.entity.Assignee;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.entity.HouseworkCategory;
import com.doittogether.platform.domain.entity.ProfileImage;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.ProfileImageRepository;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

// auditing 때문에 DataJPATest 사용 불가
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@DisplayName("집안일 조회 테스트")
@ActiveProfiles(value = "test")
@Transactional
class HouseworkRepositoryTest {

    @Autowired
    HouseworkRepository houseworkRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssigneeRepository assigneeRepository;

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Autowired
    ChannelRepository channelRepository;

    private Channel channel;
    private User accessUser;

    private List<Housework> expectedAllUncompleteHouseworks = new ArrayList<>();
    private List<Housework> expectedMixedStatusHouseworks = new ArrayList<>();


    @BeforeEach
    void 집안일_데이터_저장() {
        channel = Channel.of("우리가좍");
        channelRepository.save(channel);

        ProfileImage profileImage1 = ProfileImage.from("https://~");
        ProfileImage profileImage2 = ProfileImage.from("http");
        profileImageRepository.save(profileImage1);

        accessUser = User.of("민재", "poo@gmai.com", profileImage1);
        User user2 = User.of("서준", "ajskdj@gmai.com", profileImage2);
        userRepository.save(accessUser);
        userRepository.save(user2);

        Assignee assignee1 = Assignee.assignAssignee(accessUser);
        Assignee assignee2 = Assignee.assignAssignee(user2);
        assigneeRepository.save(assignee1);
        assigneeRepository.save(assignee2);

        Housework housework1_1_unComplete = Housework.of(LocalDateTime.now(), "청소기 돌리기", HouseworkCategory.LIVING_ROOM,
                assignee1,
                channel);
        Housework housework2_1 = Housework.of(LocalDateTime.now(), "뺄래 접기", HouseworkCategory.LIVING_ROOM,
                assignee1,
                channel);
        Housework housework3_1_unComplete = Housework.of(LocalDateTime.now(), "화장실 대청소", HouseworkCategory.BATHROOM,
                assignee1,
                channel);

        Housework housework1_2_unComplete = Housework.of(LocalDateTime.now(), "감자 깎기", HouseworkCategory.KITCHEN,
                assignee2,
                channel);
        Housework housework2_2_unComplete = Housework.of(LocalDateTime.now(), "콩나물 사오기", HouseworkCategory.OTHER,
                assignee2,
                channel);
        Housework housework3_2_unComplete = Housework.of(LocalDateTime.now(), "집박스 구독 해지", HouseworkCategory.OTHER,
                assignee2,
                channel);

        housework2_1.updateStatus();

        System.out.println("해삐해삐" + housework2_1.retrieveStatus());
        expectedMixedStatusHouseworks.add(housework1_1_unComplete);
        expectedMixedStatusHouseworks.add(housework3_1_unComplete);
        expectedMixedStatusHouseworks.add(housework3_2_unComplete);
        expectedMixedStatusHouseworks.add(housework2_2_unComplete);

        houseworkRepository.save(housework1_1_unComplete);
        houseworkRepository.save(housework2_1);
        houseworkRepository.save(housework3_1_unComplete);
        houseworkRepository.save(housework1_2_unComplete);
        houseworkRepository.save(housework2_2_unComplete);
        houseworkRepository.save(housework3_2_unComplete);
    }

    @Test
    void 채널아이디로_전체_조회한다_완료_집안일보단_미완료_집안일을_ASC정렬하며_그중에서도_접속한_사용자의_집안일을_먼저_보여준다() {
        Pageable pageable = PageRequest.of(0, 4);
        final Slice<Housework> slice = houseworkRepository.findAllByChannelId(0L, channel.retrieveChannelId(),
                accessUser.retrieveUserId(), pageable);
        final Slice<Housework> expected = new SliceImpl<>(expectedMixedStatusHouseworks, pageable, true);
        Assertions.assertThat(slice).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}