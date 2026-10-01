package ru.mrbedrockpy.bedlib.sidebar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import ru.mrbedrockpy.bedlib.text.Text;

import java.util.*;
import java.util.function.Supplier;

public class SideBar {

    private final ScoreboardManager manager;
    private final Map<UUID, PlayerBoard> boards = new HashMap<>();

    private final Text title;
    private final List<SidebarLine> structure = new ArrayList<>();

    private final Map<String, Supplier<String>> localPlaceholders = new HashMap<>();

    private SideBar(Text title) {
        this.manager = Bukkit.getScoreboardManager();
        this.title = title;
    }

    public static SideBar create(String title) {
        return create(Text.fromText(title));
    }
    public static SideBar create(Text title) {
        return new SideBar(title);
    }

    public void structure(SidebarLine... lines) {
        structure(Arrays.asList(lines));
    }

    public void structure(List<SidebarLine> lines) {
        if (lines.size() > 15)
            throw new IllegalStateException("Max 15 lines");
        structure.clear();
        structure.addAll(lines);
    }

    public void localPlaceholder(String name, Supplier<String> placeholder) {
        localPlaceholders.put(name, placeholder);
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
            Text textComponent;
            if (line.isEmpty()) textComponent = Text.fromText(ChatColor.values()[i].toString());
            else {
                textComponent = line.getText().applyPlaceholders(player);
                for (Map.Entry<String, Supplier<String>> entry : localPlaceholders.entrySet()) {
                    textComponent = textComponent.replace(entry.getKey(), entry.getValue().get());
                }
            }
            String text = textComponent.toVanilla() + ChatColor.values()[i];
            team.setPrefix(text);
            team.setSuffix("");
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