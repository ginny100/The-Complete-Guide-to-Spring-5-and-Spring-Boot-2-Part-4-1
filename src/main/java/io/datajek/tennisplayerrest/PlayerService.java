package io.datajek.tennisplayerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    /**
     * Method to update a player
     * @param id
     * @param p
     * @return the updated Player object
     */
    public Player updatePlayer(int id, Player p) {

        //get player object by Id
        Player player = repo.getReferenceById(id);

        //update player details in database
        player.setName(p.getName());
        player.setNationality(p.getNationality());
        player.setBirthDate(p.getBirthDate());
        player.setTitles(p.getTitles());

        //save updates
        return repo.save(player);
    }

    /**
     * Method to partial update a player
     * @param id
     * @param partialPlayer
     * @return
     */
    public Player patch(int id, Map<String, Object> partialPlayer) {

        //get player object by Id
        Optional<Player> player = repo.findById(id);

        //check if that found player is present in the database
        if (player.isPresent()) {
            //update fields using Map
            partialPlayer.forEach((key, value) -> {
                System.out.println("Key: " + key + " Value: " + value);
                Field field = ReflectionUtils.findField(Player.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, player.get(), value);
            });
        } else {
            throw new RuntimeException("Player with id {" + id + "} not found");
        }
        return repo.save(player.get());
    }

}