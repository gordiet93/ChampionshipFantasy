package com.example.ChampionshipFantasy.model;

import com.example.ChampionshipFantasy.model.player.Player;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
public class Selection extends AuditModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Formula("select p.points from player_gameweek p where p.fantasy_team_gameweek_id = fantasyteam_week")
    private Integer points;

    @ManyToOne
    private FantasyTeamGameweek fantasyTeamGameweek;

    @ManyToOne
    private Player player;

    private boolean captained;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isCaptained() {
        return captained;
    }

    public void setCaptained(boolean captained) {
        this.captained = captained;
    }
}
