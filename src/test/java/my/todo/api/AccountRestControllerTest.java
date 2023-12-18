package my.todo.api;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.todo.domain.user.dto.SignInDto;
import my.todo.domain.user.dto.SignUpDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  void testLogin() throws Exception {
    // given
    String username = "user1";
    String email = "user1@gmail.com";
    String password = username;

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setUsername(username);
    signUpDto.setEmail(email);
    signUpDto.setPassword(password);

    ObjectMapper mapper = new ObjectMapper();
    String signUp = mapper.writeValueAsString(signUpDto);

    // when
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .post("/api/sign/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(signUp);

    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email))
      .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_USER"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.token").isEmpty());
  }

  @Test
  @Order(2)
  void testNewUser() throws Exception {
    // given
    String username = "user1";
    String email = "user1@gmail.com";
    String password = username;

    SignInDto signInDto = new SignInDto();
    signInDto.setUsername(username);
    signInDto.setPassword(password);

    ObjectMapper mapper = new ObjectMapper();
    String signIn = mapper.writeValueAsString(signInDto);

    // when
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .post("/api/sign/signin")
        .contentType(MediaType.APPLICATION_JSON)
        .content(signIn);

    // then
    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email))
      .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_USER"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
  }
}
