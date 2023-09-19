package hello.core.Web;

import hello.core.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
public class TestController {

    @GetMapping("/")
    public String test(Model model, HttpServletRequest request) {
        request.setAttribute("year", 2023);

        HttpSession session = request.getSession(); // 요청으로부터 session 객체를 얻어온다.
        session.setAttribute("id", "asdf"); // session 객체에 id를 저장

        ServletContext application = session.getServletContext();
        application.setAttribute("email", "abcd@naver.com");

        model.addAttribute("testName", "Thymeleaf test name");
        model.addAttribute("list", Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee"));
        model.addAttribute("bno", 123);
        model.addAttribute("user", new User("aaa", 20));
        return "test";
    }
}
