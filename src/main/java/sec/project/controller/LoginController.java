package sec.project.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private HttpSession session;
    
    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadForm() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(@RequestParam String username, @RequestParam String password) {
        Account account = accountRepository.findByUsername(username);
        
        if (account != null && account.getPassword().equals(password)) {
            session.setAttribute("user", account.getId());
            return "redirect:/list";
        } else {
            return "login";
        }
    }

}
