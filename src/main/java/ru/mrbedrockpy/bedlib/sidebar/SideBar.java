package ru.mrbedrockpy.bedlib.sidebar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import ru.mrbedrockpy.bedlib.text.Text;

import java.util.*;

public class SideBar {

    private final ScoreboardManager manager;
    private final Map<UUID, PlayerBoard> boards = new HashMap<>();

    private final List<SidebarLine> structure = new ArrayList<>();
    private final Text title;

    private SideBar(Text title) {
        this.manager = Bukkit.getScoreboardManager();
        this.title = title;
    }

    public static SideBar create(String title) {
        return new SideBar(Text.fromText(title));
    }

    public void structure(SidebarLine... lines) {
        if (lines.length > 15)
            throw new IllegalStateException("Max 15 lines");
        structure.clear();
        structure.addAll(Arrays.asList(lines));
    }

    public void addPlayers(Collection<Player> players) {
        players.forEach(this::addPlayer);
    }

    public void removePlayers() {
        boards.values().forEach(b -> b.player.setScoreboard(manager.getMainScoreboard()));
        boards.clear();
    }

    public void removePlayer(Player player) {
        PlayerBoard board = boards.remove(player.getUniqueId());
        if (board != null) player.setScoreboard(manager.getMainScoreboard());
    }

    public void addPlayer(Player player) {
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective(
                "sidebar", Criteria.DUMMY,
                title.toAdventure(), RenderType.INTEGER);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        PlayerBoard playerBoard = new PlayerBoard(player, board, obj);
        int score = structure.size();
        for (int i = 0; i < structure.size(); i++) {
            String entry = ChatColor.values()[i].toString();
            Team team = board.registerNewTeam("line_" + i);
            team.addEntry(entry);
            obj.getScore(entry).setScore(score--);
            playerBoard.lines.put(i, team);
        }
        boards.put(player.getUniqueId(), playerBoard);
        player.setScoreboard(board);
        updatePlayer(player);
    }

    public void update() {
        boards.values().forEach(b -> updatePlayer(b.player));
    }

    private void updatePlayer(Player player) {
        PlayerBoard board = boards.get(player.getUniqueId());
        if (board == null) return;
        for (int i = 0; i < structure.size(); i++) {
            SidebarLine line = structure.get(i);
            Team team = board.lines.get(i);
            String text;
            if (line.isEmpty()) text = ChatColor.values()[i].toString();
            else text = line.getText()
                    .applyPlaceholders(player)
                    .toVanilla();
            text = text + ChatColor.values()[i];
            String prefix;
            String suffix;
            if (text.length() <= 16) {
                prefix = text;
                suffix = "";
            } else {
                prefix = text.substring(0, 16);
                String rest = text.substring(16);
                String lastColors = ChatColor.getLastColors(prefix);
                suffix = lastColors + rest;
                if (suffix.length() > 16) suffix = suffix.substring(0, 16);
            }
            team.setPrefix(prefix);
            team.setSuffix(suffix);
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class PlayerBoard {

        private final Player player;
        private final Scoreboard board;
        private final Objective objective;
        private final Map<Integer, Team> lines = new HashMap<>();

    }
}