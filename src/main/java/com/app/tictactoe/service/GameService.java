package com.app.tictactoe.service;

import com.app.tictactoe.dao.GameDao;
import com.app.tictactoe.dao.PreGameDao;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.model.PreGame;
import com.app.tictactoe.other.builder.GameBuilder;
import com.app.tictactoe.other.dto.GameDto;
import com.app.tictactoe.other.dto.ProfileGameDto;
import com.app.tictactoe.other.enums.Process;
import com.app.tictactoe.other.enums.Win;
import com.app.tictactoe.other.exceptions.GameNotFoundException;
import com.app.tictactoe.other.exceptions.PreGameNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final ModelMapper mapper = new ModelMapper();

    private GameDao gameDao;
    private PreGameDao preGameDao;

    @Autowired
    public GameService(GameDao gameDao, PreGameDao preGameDao) {
        this.gameDao = gameDao;
        this.preGameDao = preGameDao;
    }

    public GameDto findById(Long id){
        Optional<Game> gameOptional = gameDao.findById(id);
        Game game = gameOptional.orElseThrow(GameNotFoundException::new);
        return mapper.map(game, GameDto.class);
    }

    public void setWinner(Long id, Win winner){
        Optional<Game> gameOptional = gameDao.findById(id);
        Game game = gameOptional.orElseThrow(GameNotFoundException::new);
        game.setWinner(winner);
        game.setProcess(Process.ENDED);
        gameDao.save(game);
    }

    public Boolean existActualGame(Player player){
        return gameDao.existsByXAndProcessOrOAndProcess(player, Process.DURING, player, Process.DURING);
    }

    public GameDto findActualGame(Player player){
        Optional<Game> gameOptional = gameDao.findByXAndProcessOrOAndProcess(player, Process.DURING, player, Process.DURING);
        Game game = gameOptional.orElseThrow(GameNotFoundException::new);
        return mapper.map(game, GameDto.class);
    }

    public Game create(Player player){
        Optional<PreGame> preGameOptional = preGameDao.findTop1ByOrderByIdAsc();
        PreGame preGame = preGameOptional.orElseThrow(PreGameNotFoundException::new);
        Game game = GameBuilder.builder()
                .x(preGame.getPlayer())
                .o(player)
                .process(Process.DURING)
                .build();
        preGameDao.delete(preGame);
        return gameDao.save(game);
    }

    public List<ProfileGameDto> findByPlayer(Player player){
        List<Game> games = gameDao.findByXOrO(player, player);
        return games.stream()
                .map(game -> mapper.map(game, ProfileGameDto.class))
                .collect(Collectors.toList());
    }

    public Long countByPlayer(Player player){
        return gameDao.countByXOrO(player, player);
    }

    public Long countWinsByPlayer(Player player){
        List<Win> x = new LinkedList<>();
        List<Win> o = new LinkedList<>();
        x.add(Win.X);
        x.add(Win.O_LEFT);
        o.add(Win.O);
        o.add(Win.X_LEFT);
        return gameDao.countByXAndWinnerInOrOAndWinnerIn(player, x, player, o);
    }

    public Long countDefeatByPlayer(Player player){
        List<Win> x = new LinkedList<>();
        List<Win> o = new LinkedList<>();
        x.add(Win.X);
        x.add(Win.O_LEFT);
        o.add(Win.O);
        o.add(Win.X_LEFT);
        return gameDao.countByXAndWinnerInOrOAndWinnerIn(player, o, player, x);
    }

    public Long countDrawByPlayer(Player player){
        return gameDao.countByXAndWinnerOrOAndWinner(player, Win.DRAW, player, Win.DRAW);
    }
}
