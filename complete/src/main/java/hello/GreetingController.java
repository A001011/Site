package hello;

import com.google.common.hash.Hashing;
import javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SessionAttributes("user")
@Controller
public class GreetingController {
   @ModelAttribute
    public User intSession() {
        return new User();
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/Register", method = RequestMethod.GET)
    public String register() {
        return "Register";
    }

    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public String registerpost(@RequestParam("email") String email,
                               @RequestParam("pass") String pass,
                               @RequestParam("pass_repeat") String pass_repeat,
                               @RequestParam("first_name") String first_name,
                               @RequestParam("last_name") String last_name) {
        if (pass.equals(pass_repeat)) {
            System.out.println(email + " " + pass + " " + pass_repeat + " " + first_name + " " + last_name);
            final String hash = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
            userService.create(new User(email, hash, first_name, last_name));
            return "redirect:/login";
        } else {
            throw new IllegalArgumentException("repeat@ vhe hamapatasxanum passin");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(HttpServletResponse response) {
        response.addCookie(new Cookie("session", "test"));
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginpost(@ModelAttribute("user") User sessionuser,
                            @RequestParam("email") String email,
                            @RequestParam("pass") String pass,
                            @CookieValue("session") String cookie) {
        if (userService.userExistByEmail(email)) {
            User user = userService.getByEmail(email);
            String p = user.getPass();
            pass = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
            if (p.equals(pass)) {
                sessionuser.setEmail(user.getEmail());
                sessionuser.setFirstName(user.getFirstName());
                sessionuser.setLastName(user.getLastName());
                sessionuser.setId(user.getId());
                return "redirect:/home";
            } else {
                return "login";
            }
            //System.out.println(userService.getByEmail(email));
            //return "redirect:/home";
        }
        return "login";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        List<User>userList = userService.getAllUsers();
        model.addAttribute("list", userList);
        return "admin";
    }
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, @ModelAttribute User user) {
        model.addAttribute("email", user.getEmail());
        return "home";
    }

}
