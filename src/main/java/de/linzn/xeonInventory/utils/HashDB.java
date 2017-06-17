package de.linzn.xeonInventory.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class HashDB {
	public static HashSet<UUID> loginLock = new HashSet<UUID>();
	public static HashSet<UUID> vanish = new HashSet<UUID>();
	public static HashSet<UUID> godLock = new HashSet<UUID>();
	public static Map<UUID, Integer> taskID = new HashMap<UUID, Integer>();
	public static Map<UUID, Integer> historyID = new HashMap<UUID, Integer>();

}
