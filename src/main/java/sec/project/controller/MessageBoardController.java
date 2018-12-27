package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Message;
import sec.project.repository.AccountRepository;
import sec.project.repository.MessageRepository;

@Controller
public class MessageBoardController {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private HttpSession session;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        Long userId = (Long) session.getAttribute("user");
        Account account = null;
        
        if (userId != null) {
            account = accountRepository.findOne(userId);
        }
        
        if (account != null) {
            List<Message> messages = messageRepository.findAll();
            
            Collections.reverse(messages);
            
            model.addAttribute("messages", messages);
            model.addAttribute("account", account);
            
            return "list";
        } else {
            return "redirect:/login";
        }
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String newMessage(@RequestParam String title, @RequestParam String content) {
        Message newMessage = new Message();
        
        newMessage.setTitle(title);
        newMessage.setContent(content);
        
        Long userId = (Long) session.getAttribute("user");
        Account account = null;
        
        if (userId != null) {
            account = accountRepository.findOne(userId);
        }
        
        if (account != null) {
            newMessage.setAccount(account);
        
            messageRepository.save(newMessage);
        }
        
        return "redirect:/list";
    }
    
    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public String message(Model model, @PathVariable Long id) {
        Long userId = (Long) session.getAttribute("user");
        Account account = null;
        
        if (userId != null) {
            account = accountRepository.findOne(userId);
        }
        
        if (account != null && id != null) {
            Message message = messageRepository.findOne(id);
            
            model.addAttribute("message", message);
            
            if (message.getAccount().getId().equals(account.getId()) || account.getName().equals("admin")) {
                model.addAttribute("owned", true);
            } else {
                model.addAttribute("owned", false);
            }
            
            return "message";
        } else {
            return "redirect:/login";
        }
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable Long id) {
        Long userId = (Long) session.getAttribute("user");
        Account account = null;
        
        if (userId != null) {
            account = accountRepository.findOne(userId);
        }
        
        if (account != null && id != null) {
            Message message = messageRepository.findOne(id);
            
            if (message != null) {
                messageRepository.delete(id);
            }
            
            return "redirect:/list";
        } else {
            return "redirect:/login";
        }
    }
}
