package hello;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;

@Controller
public class GreetingController {

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
    public String Login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String registerpost(@RequestParam("email") String email,
                               @RequestParam("pass") String pass) {
        if (userService.userExistByEmail(email)) {
            User user = userService.getByEmail(email);
            String p = user.getPass();
            pass = Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
            if (p.equals(pass)) {

                return "redirect:/home";
            } else {
                return "login";
            }
            //System.out.println(userService.getByEmail(email));
            //return "redirect:/home";
        }
        return "login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }
}
