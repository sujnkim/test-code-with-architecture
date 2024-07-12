package com.example.demo.post.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1679530673958L)
                .build();
        User user = User.builder()
                .id(1L)
                .email("abc@naver.com")
                .nickname("abc")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);

        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("hello world!")
                .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postCreateController
                .createPost(postCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("hello world!");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("abc");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1679530673958L);
    }
}
