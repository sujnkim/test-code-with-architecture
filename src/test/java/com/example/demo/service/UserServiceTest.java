package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_가져온다() {
        // given
        String email = "abc@test.com";

        // when
        UserEntity result = userService.getByEmail(email);

        // then
        assertThat(result.getNickname()).isEqualTo("abc");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저를_가져올_수_없다() {
        // given
        String email = "xyz@test.com";

        // when
        // then
        assertThatThrownBy(() -> {
            UserEntity result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void getById는_ACTIVE_상태인_유저를_가져온다() {
        // given
        // when
        UserEntity result = userService.getById(1);

        // then
        assertThat(result.getNickname()).isEqualTo("abc");
    }

    @Test
    void getById는_PENDING_상태인_유저를_가져올_수_없다() {
        // given
        String email = "xyz@test.com";

        // when
        // then
        assertThatThrownBy(() -> {
            UserEntity result = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

}
