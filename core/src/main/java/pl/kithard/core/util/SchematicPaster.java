package pl.kithard.core.util;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public final class SchematicPaster {

    private SchematicPaster() {}

    public static void pasteSchematic(File schematicFile, Location location, boolean noAir) {
        try {

            EditSession editSession = new EditSession(new BukkitWorld(location.getWorld()), 999999999);
            editSession.enableQueue();

            SchematicFormat schematic = SchematicFormat.getFormat(schematicFile);
            CuboidClipboard clipboard = schematic.load(schematicFile);

            clipboard.paste(editSession, BukkitUtil.toVector(location), noAir);
            editSession.flushQueue();
        } catch (DataException | IOException | MaxChangedBlocksException e) {
            throw new RuntimeException(e);
        }
    }

}
