package io.github.crucible.grimoire.forge.core;

import com.google.common.annotations.Beta;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A set of functions to help deal with UUIDs.
 *
 * @author juanmuscaria
 */
public final class UuidUtils {
    //Seal class.
    private UuidUtils() {
    }

    /**
     * Generate a player offline UUID.
     *
     * @param playerName The player's name.
     * @return An offline player UUID.
     */
    @NotNull
    public static UUID offlineUUID(String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Process a string to try to get a uuid from it.
     *
     * @param uuid A string to process.
     * @return The UUID found in the string, null if no UUID is found.
     */
    @Nullable
    public static UUID processUuidString(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (Exception ignore) {
        }
        try {
            String processedUuid = uuid.substring(0, 8) +
                    "-" + uuid.substring(8, 12) +
                    "-" + uuid.substring(12, 16) +
                    "-" + uuid.substring(16, 20) +
                    "-" + uuid.substring(20, 32);
            return UUID.fromString(processedUuid);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Get a player's UUID from Mojang's api.
     * Caching is recommended.
     *
     * @param playerName The player's name.
     * @return The player's UUID, null if it was not possible to get the UUID.
     */
    @Beta
    @Nullable
    public UUID onlineUUID(String playerName) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(content.toString());
            if (!element.isJsonObject()) return null;
            String unprocessedUuid = element.getAsJsonObject().get("id").getAsString();
            return processUuidString(unprocessedUuid);
        } catch (Exception ignored) {
        }
        return null;
    }
}
