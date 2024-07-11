package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;
import javax.validation.Valid;


@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String printWelcome() {
        return "user/start";
    }

    @GetMapping("/info")
    public String printInfoAllUser(@RequestParam(name = "id", defaultValue = "0") long id, Model model) {
        model.addAttribute("userInfo", userService.getAll());
        model.addAttribute("id", id);
        return "user/userInfo";
    }

    @GetMapping("/user")
    public String printInfoUserById(@RequestParam(name = "id") long id, Model model) {
        model.addAttribute("userById", userService.getUserById(id));
        model.addAttribute("id", id);
        return "user/userInfoById";
    }

    @GetMapping("/user/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "user/newUser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/newUser";
        }
        userService.save(user);
        return "redirect:/info";
    }

    @GetMapping("/user/edit")
    public String editUser(@RequestParam(name = "id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));

        return "user/userEdit";
    }

    @PostMapping("/user/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/userEdit";
        }
        userService.update(user);
        return "redirect:/info";
    }

    @PostMapping("/info")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/info";
    }

}


