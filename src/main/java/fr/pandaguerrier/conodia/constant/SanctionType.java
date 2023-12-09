package fr.pandaguerrier.conodia.constant;

public enum SanctionType {
    BAN("BAN"),
    TEMP_BAN("TEMP BAN"),
    BAN_IP("BAN IP"),
    TEMP_BAN_IP("TEMPBAN IP"),
    MUTE("MUTE"),
    TEMP_MUTE("TEMP MUTE"),
    KICK("KICK"),
    UNBAN("UNBAN"),
    UNBAN_IP("UNBAN IP"),
    UNMUTE("UNMUTE");

    private String value;

    SanctionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
