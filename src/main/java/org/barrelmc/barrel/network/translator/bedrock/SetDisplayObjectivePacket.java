package org.barrelmc.barrel.network.translator.bedrock;

import com.github.steveice10.mc.protocol.data.game.scoreboard.ObjectiveAction;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreType;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreboardPosition;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerDisplayScoreboardPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import net.kyori.adventure.text.Component;
import org.barrelmc.barrel.network.translator.interfaces.BedrockPacketTranslator;
import org.barrelmc.barrel.player.Player;
import org.barrelmc.barrel.utils.Utils;

public class SetDisplayObjectivePacket implements BedrockPacketTranslator {

    @Override
    public void translate(BedrockPacket pk, Player player) {
        com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket packet = (com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket) pk;

        String name = Utils.lengthCutter(packet.getDisplayName(), 32);

        if ("sidebar".equals(packet.getDisplaySlot())) {
            player.setScoreSortorder(packet.getSortOrder());

            player.getJavaSession().send(new ServerScoreboardObjectivePacket(packet.getObjectiveId(), ObjectiveAction.ADD, Component.text(name), ScoreType.INTEGER));
            player.getJavaSession().send(new ServerDisplayScoreboardPacket(ScoreboardPosition.SIDEBAR, packet.getObjectiveId()));
        }
    }
}
