package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class RegisterController {

    @Autowired
    private AccountRepository signupRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String loadForm() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitForm(@RequestParam String username, @RequestParam String password) {
        signupRepository.save(new Account(username, password));
        return "redirect:/login";
    }

}
