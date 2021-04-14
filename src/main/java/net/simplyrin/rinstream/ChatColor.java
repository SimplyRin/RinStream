package net.simplyrin.rinstream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * I used it as a reference:
 * https://github.com/SpigotMC/BungeeCord/blob/master/chat/src/main/java/net/md_5/bungee/api/ChatColor.java
 */
@Getter
@AllArgsConstructor
public enum ChatColor {

	BLACK(ConsoleColor.BLACK, "§0"),
	DARK_BLUE (ConsoleColor.BLUE, "§1"),
	DARK_GREEN(ConsoleColor.GREEN, "§2"),
	DARK_AQUA(ConsoleColor.CYAN, "§3"),
	DARK_RED(ConsoleColor.RED, "§4"),
	DARK_PURPLE(ConsoleColor.MAGENTA, "§5"),
	GOLD(ConsoleColor.YELLOW, "§6"),
	DARK_GRAY(ConsoleColor.BLACK_BOLD, "§8"),
	BLUE(ConsoleColor.BLUE_BRIGHT, "§9"),
	GREEN(ConsoleColor.GREEN_BRIGHT, "§a"),
	AQUA(ConsoleColor.CYAN_BRIGHT, "§b"),
	RED(ConsoleColor.RED_BRIGHT, "§c"),
	LIGHT_PURPLE(ConsoleColor.MAGENTA_BRIGHT, "§d"),
	YELLOW(ConsoleColor.YELLOW_BRIGHT, "§e"),
	WHITE(ConsoleColor.WHITE, "§f"),
	// UNDERLINE(ConsoleColor.BLACK_BACKGROUND_BRIGHT, ""),
	RESET(ConsoleColor.RESET, "§r");

	private ConsoleColor consoleColor;
	private String colorCode;

	@Override
	public String toString() {
		return this.consoleColor.toString();
	}

	public static String translate(String value) {
		for (ChatColor chatColor : ChatColor.values()) {
			value = value.replace(chatColor.getColorCode(), chatColor.getConsoleColor().toString());
		}
		return value;
	}

}
