package my.todo.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.user.dto.SignInDto;
import my.todo.domain.user.dto.SignUpDto;
import my.todo.domain.user.dto.UserDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoItemRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private UserDto user;
  
  @BeforeAll
  void beforAll() throws Exception {
    String username = "user312";
    user = createAndLogin(username);
  }

  @Test
  void testNewItem() throws Exception {
    // given
    String title = "title1";
    String description = "description1";

    TodoItemDto itemDto = new TodoItemDto();
    itemDto.setTitle(title);
    itemDto.setDescription(description);

    // when
    ObjectMapper mapper = new ObjectMapper();
    String item = mapper.writeValueAsString(itemDto);

    String url = "/api/todo/" + user.getId() + "/item";
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .post(url)
        .header("X-MY-TODO-AUTH", user.getToken())
        .contentType(MediaType.APPLICATION_JSON)
        .content(item);

    // then
    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.number").exists())
      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
      .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description));
  }

  @Test
  void testItem() throws Exception {
    // given
    String title = "title2";
    String description = "description2";
    TodoItemDto itemDto = createItem(title, description);

    // when
    String url = "/api/todo/" + user.getId() + "/item/" + itemDto.getNumber();
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .get(url)
        .header("X-MY-TODO-AUTH", user.getToken())
        .contentType(MediaType.APPLICATION_JSON);

    // then
    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(itemDto.getNumber()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(itemDto.getTitle()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(itemDto.getDescription()));
  }

  @Test
  void testItems() {

  }

  @Test
  void testRemove() throws Exception {
    // given
    String title = "title4";
    String description = "description4";
    TodoItemDto itemDto = createItem(title, description);

    // when
    String url = "/api/todo/" + user.getId() + "/item/" + itemDto.getNumber();
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .delete(url)
        .header("X-MY-TODO-AUTH", user.getToken())
        .contentType(MediaType.APPLICATION_JSON);

    // then
    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk());

    // String findUrl = "/api/todo/" + user.getId() + "/item/" + itemDto.getNumber();
    // mockHttpServletRequestBuilder =
    //   MockMvcRequestBuilders
    //     .get(findUrl)
    //     .header("X-MY-TODO-AUTH", user.getToken())
    //     .contentType(MediaType.APPLICATION_JSON);

    // mockMvc
    //   .perform(mockHttpServletRequestBuilder)
    //   .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void testUpdate() throws Exception {
    // given
    String title = "title5";
    String description = "description5";
    TodoItemDto itemDto = createItem(title, description);

    // when
    title = "modifyTitle5";
    description = "modifyMescription5";
    TodoItemDto modify = new TodoItemDto();
    modify.setNumber(itemDto.getNumber());
    modify.setTitle(title);
    modify.setDescription(description);

    ObjectMapper mapper = new ObjectMapper();
    String item = mapper.writeValueAsString(modify);

    String url = "/api/todo/" + user.getId() + "/item/" + itemDto.getNumber();
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .patch(url)
        .header("X-MY-TODO-AUTH", user.getToken())
        .contentType(MediaType.APPLICATION_JSON)
        .content(item);

    // then
    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(itemDto.getNumber()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
      .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description));

    TodoItemDto modifiedItem = getItem(itemDto.getNumber());
    Assertions.assertThat(modifiedItem.getNumber()).isEqualTo(itemDto.getNumber());
    Assertions.assertThat(modifiedItem.getTitle()).isEqualTo(title);
    Assertions.assertThat(modifiedItem.getDescription()).isEqualTo(description);
  }

  UserDto createAndLogin(String username) throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    String email = username + "@gmail.com";
    String password = username;

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setUsername(username);
    signUpDto.setEmail(email);
    signUpDto.setPassword(password);

    String signUp = mapper.writeValueAsString(signUpDto);
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .post("/api/sign/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(signUp);

    mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk());

    SignInDto signInDto = new SignInDto();
    signInDto.setUsername(username);
    signInDto.setPassword(password);

    String signIn = mapper.writeValueAsString(signUpDto);
    mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .post("/api/sign/signin")
        .contentType(MediaType.APPLICATION_JSON)
        .content(signIn);

    MvcResult res = mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
      .andReturn();

    return mapper.readValue(res.getResponse().getContentAsString(), UserDto.class);
  }

  TodoItemDto createItem(String title, String description) throws Exception {
    TodoItemDto itemDto = new TodoItemDto();
    itemDto.setTitle(title);
    itemDto.setDescription(description);

    ObjectMapper mapper = new ObjectMapper();
    String item = mapper.writeValueAsString(itemDto);

    String url = "/api/todo/" + user.getId() + "/item";
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .post(url)
        .header("X-MY-TODO-AUTH", user.getToken())
        .contentType(MediaType.APPLICATION_JSON)
        .content(item);

    MvcResult res = mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();
    
    return mapper.readValue(res.getResponse().getContentAsString(), TodoItemDto.class);
  }

  TodoItemDto getItem(Long number) throws Exception {
    String url = "/api/todo/" + user.getId() + "/item/" + number;
    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
      MockMvcRequestBuilders
        .get(url)
        .header("X-MY-TODO-AUTH", user.getToken())
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult res = mockMvc
      .perform(mockHttpServletRequestBuilder)
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn();

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(res.getResponse().getContentAsString(), TodoItemDto.class);
  }

}
