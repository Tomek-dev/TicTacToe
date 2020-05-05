package com.app.tictactoe.controller;

import com.app.tictactoe.security.UserPrincipal;
import com.app.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private GameService gameService;

    @Autowired
    public ProfileController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserPrincipal user){
        model.addAttribute("count", gameService.countByPlayer(user.getPlayer()));
        model.addAttribute("wins", gameService.countWinsByPlayer(user.getPlayer()));
        model.addAttribute("defeats", gameService.countDefeatByPlayer(user.getPlayer()));
        model.addAttribute("draws", gameService.countDrawByPlayer(user.getPlayer()));
        model.addAttribute("games", gameService.findByPlayer(user.getPlayer()));
        return "profile";
    }
}
