package com.nightshadepvp.discord.object;

import java.util.HashSet;

/**
 * Created by Blok on 8/17/2018.
 */
public class Room
{
    public static HashSet<Room> rooms;
    private long ownerID;
    private long roomID;
    private int userLimit;
    private String name;
    private boolean locked;

    public Room(final long ownerID) {
        this.ownerID = ownerID;
    }

    public long getOwnerID() {
        return this.ownerID;
    }

    public void setOwnerID(final long ownerID) {
        this.ownerID = ownerID;
    }

    public long getRoomID() {
        return this.roomID;
    }

    public void setRoomID(final long roomID) {
        this.roomID = roomID;
    }

    public int getUserLimit() {
        return this.userLimit;
    }

    public void setUserLimit(final int userLimit) {
        this.userLimit = userLimit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    static {
        Room.rooms = new HashSet<Room>();
    }
}