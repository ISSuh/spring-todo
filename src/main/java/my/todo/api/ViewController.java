package my.todo.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewController {
  
  @GetMapping("/home")
  public String homeView() {
    return "home";
  }

  @GetMapping("/login")
  public String loginView() {
    return "login";
  }

}
