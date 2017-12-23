package de.linzn.xeonInventory.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class HashDB {
    public static HashSet<UUID> loginLock = new HashSet<>();
    public static HashSet<UUID> vanish = new HashSet<>();
    public static HashSet<UUID> godLock = new HashSet<>();
    public static Map<UUID, Integer> taskID = new HashMap<>();
    public static Map<UUID, Integer> historyID = new HashMap<>();

}
