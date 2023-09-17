package hello.core.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String test(Model model) {
        model.addAttribute("testName1", "thymeleaf Test Name");
        model.addAttribute("testName2", "JSP Test Name");

        return "test";
    }
}
