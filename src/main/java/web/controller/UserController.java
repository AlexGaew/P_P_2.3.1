package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping( "/")
    public String printWelcome() {
        return "user/start";
    }

    @GetMapping( "/info")
    public String printInfoAllUser(Model model) {
        model.addAttribute("userInfo", userService.getAll());
        return "user/userInfo";
    }

    @GetMapping( "/user/{id}")
    public String printInfoUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("userInfo", userService.getUserById(id));
        return "user/userInfoById";
    }

    @GetMapping("/user/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "user/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/info";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/userEdit";
    }

    @PostMapping("/user/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.save(user, id);
        return "redirect:/info";
    }
    @PostMapping("/info")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/info";
    }

}


