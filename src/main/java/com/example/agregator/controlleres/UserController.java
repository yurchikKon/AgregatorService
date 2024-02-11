package com.example.agregator.controlleres;

import com.example.agregator.entities.History;
import com.example.agregator.entities.ProductStorage;
import com.example.agregator.entities.User;
import com.example.agregator.repositories.HistoryRepository;
import com.example.agregator.repositories.UserRepository;
import com.example.agregator.services.ProductStorageService;
import com.example.agregator.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HistoryRepository historyRepository;
    private final ProductStorageService productStorageService;

    @GetMapping("/account")
    public String account(Model model, @CurrentSecurityContext(expression="authentication?.name") String username,
                          @RequestParam(value = "historyName", required = false) String historyName)
    {
        Iterable<History> uHistory = historyRepository.findHistoryByULogin(username);
        model.addAttribute("uHistory", uHistory);
        model.addAttribute("uname", username);
        if(historyName !=null)
        {
            List<ProductStorage> uP = productStorageService.searchHistory(historyName,username);
            model.addAttribute("history",uP);
            return "SearchResult";
        }

        return "account";
    }



    @GetMapping("/registration")
    public String registration(@ModelAttribute User usr){
        return "registration";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @PostMapping("/registration")
    public String registerUser(@RequestParam String login,
                               @RequestParam String password,
                               Model model){
        if(!userService.createUser(login, password)){
            model.addAttribute("error", "Ошибка при создании пользователя!");
            return "registration";
        }
        return "redirect:/account";
    }
}
