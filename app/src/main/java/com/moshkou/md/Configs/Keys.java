package com.moshkou.md.Configs;

public class Keys {
    public static final String INDEX        = "INDEX";
    public static final String USER         = "USER";
    public static final String FLAG         = "FLAG";
    public static final String ALERT        = "ALERT";
    public static final String TOKEN        = "TOKEN";
    public static final String TEMPLATE     = "TEMPLATE";
    public static final String URL          = "URL";
    public static final String URI          = "URI";
    public static final String VIDEO        = "VIDEO";
    public static final String NAME         = "NAME";
    public static final String ID           = "ID";
    public static final String IMAGE        = "IMAGE";
    public static final String TAG          = "TAG";

    public enum ObserverType {
        CAMPAIGN,
        MEDIA,
        PLAYER,
        PLAYERS,
        PLAYLIST,
        TEMPLATES
    }

    public enum SelectState {
        NULL,
        SELECT,
        UNSELECT
    }

    public enum MessageState {
        NULL,
        INFO,
        WARNING,
        ERROR
    }

    public enum ServiceRequest {
        CREATE,
        READ,
        DELETE,
        UPDATE,
        UPLOAD
    }

    public enum ServiceResponse {
        AUTHENTICATION_FAILED,
        SERVICE_NOT_AVAILABLE,
        DATA_TOO_LARGE,
        NO_DATA,
        UNSUCCESSFUL
    }
}
