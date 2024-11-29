package com.doittogether.platform;

import com.doittogether.platform.domain.entity.ProfileImage;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.ProfileImageRepository;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TemporaryLoginSaveTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    private ProfileImage profileImage;

    private User user;

    @BeforeEach
    public void 기본이미지_저장() {
        profileImage = ProfileImage.from(
                "https://www.google.com/imgres?q=기본이미지&imgurl=https%3A%2F%2Fi.pinimg.com%2F474x%2F2f%2F55%2F97%2F2f559707c3b04a1964b37856f00ad608.jpg&imgrefurl=https%3A%2F%2Fkr.pinterest.com%2Fneane1204%2F%25EC%25B9%25B4%25ED%2586%25A1-%25EA%25B8%25B0%25EB%25B3%25B8%25ED%2594%2584%25EC%2582%25AC%2F&docid=-_QnKMphr9-4YM&tbnid=noP947Cj2lUIlM&vet=12ahUKEwjI_72rsvaJAxVQkK8BHYvhBGMQM3oECBoQAA..i&w=398&h=398&hcb=2&ved=2ahUKEwjI_72rsvaJAxVQkK8BHYvhBGMQM3oECBoQAA");
        profileImageRepository.save(profileImage);
    }

    @Test
    @Order(value = 1)
    public void 홍길동_계정_저장() {
        userRepository.save(
                User.of("doto", "doto@gmail.com", profileImage)
        );
    }

    @Test
    @Order(value = 2)
    void 홍길동_계정_조회() {
        User actual = userRepository.findByEmail("doto@gmail.com").orElseThrow(() -> new RuntimeException("비상"));
        Assertions.assertThat(actual.retrieveEmail()).isEqualTo("doto@gmail.com");
    }
}
