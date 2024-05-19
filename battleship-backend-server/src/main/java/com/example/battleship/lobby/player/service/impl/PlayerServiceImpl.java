package com.example.battleship.lobby.player.service.impl;

import com.example.battleship.lobby.player.entity.Player;
import com.example.battleship.lobby.player.enums.PlayerStatus;
import com.example.battleship.lobby.player.repository.PlayerRepository;
import com.example.battleship.lobby.player.service.PlayerService;
import com.example.battleship.user.entity.User;
import com.example.battleship.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * Service implementation for player-related operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository playerRepository;
  private final UserService userService;

  @Override
  @Transactional
  public Player createNewPlayer(User user) {
    log.info("Creating new Player with name: {}", user.getLogin());

    Player player = Player.builder()
            .name(user.getLogin())
            .userId(user.getId())
            .status(PlayerStatus.ONLINE)
            .build();
    player = save(player);

    return save(player);
  }

  @Transactional
  private Player save(Player player) {
    log.info("Saving player {} in database.", player.getName());

    return playerRepository.save(player);
  }

  @Transactional
  private void delete(Player player) {
    log.info("Deleting player: {}", player.getName());

    playerRepository.delete(player);
  }

  @Override
  public Player getPlayerFromSession(WebSocketSession session) {
    String userLogin = Objects.requireNonNull(session.getPrincipal()).getName();
    User user = userService.getUserByLogin(userLogin);

    log.info("Getting player: {} from session.", userLogin);

    return getOrCreatePlayer(user);
  }

  @Override
  public Player getOrCreatePlayer(User user) {
    log.info("Getting or creating player: {}.", user.getLogin());

    try {
      Player player = getPlayerByUserId(user.getId());

      return updatePlayerNameIfNecessary(user, player);
    } catch (RuntimeException e) {

      return createNewPlayer(user);
    }
  }

  private Player getPlayerByUserId(Long id) {
    log.info("Getting player entity by user id: {}", id);

    return playerRepository.findByUserId(id).orElseThrow(()
            -> new RuntimeException(String.format("Player with id %d not found in database.", id)));
  }

  private Player updatePlayerNameIfNecessary(User user, Player player) {
    log.info("Checking if player name is correct for player: {} and user: {}",
            player.getName(),
            user.getLogin()
    );

    if (!player.getName().equals(user.getLogin())) {
      player.setName(user.getLogin());

      player = save(player);
    }

    return player;
  }

  @Override
  public Player setPlayerSessionId(Player player, String sessionId) {
    log.info("Setting session id of player: {}", player.getName());
    player.setSessionId(sessionId);

    return save(player);
  }

  @Override
  public Player setPlayerStatus(Player player, PlayerStatus status) {
    log.info("Changing player: {}, status to: {}", player.getName(), status);
    player.setStatus(status);

    return save(player);
  }
}
