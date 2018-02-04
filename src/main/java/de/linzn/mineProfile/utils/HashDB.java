/*
 * Copyright (C) 2018. MineGaming - All Rights Reserved
 *  You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 *  You should have received a copy of the LGPLv3 license with
 *  this file. If not, please write to: niklas.linz@enigmar.de
 */

package de.linzn.mineProfile.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class HashDB {
    public static HashSet<UUID> loginLock = new HashSet<>();
    public static HashSet<UUID> cookieFix = new HashSet<>();
    public static HashSet<UUID> vanish = new HashSet<>();
    public static HashSet<UUID> godLock = new HashSet<>();
    public static Map<UUID, Integer> taskID = new HashMap<>();
    public static Map<UUID, Integer> historyID = new HashMap<>();

}
