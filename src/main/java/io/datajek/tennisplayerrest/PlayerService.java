package io.datajek.tennisplayerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository repo;

    /**
     * Method to return all players
     * @return a list of players
     */
    public List<Player> getAllPlayers() {
        return repo.findAll();
    }

    /**
     * Method to find player by id
     *
     * @param id
     * @return a Player
     */
    public Player getPlayer(int id) {
        Optional<Player> tempPlayer = repo.findById(id);

        if(tempPlayer.isEmpty())
            throw new RuntimeException("Player with id {"+ id +"} not found");

        return tempPlayer.get();
    }

    /**
     * Method to add player
     * @param p
     * @return the new player that has been added
     */
    public Player addPlayer(Player p) {
        return repo.save(p);
    }

    //method to update a player

    //method to partial update a player

}