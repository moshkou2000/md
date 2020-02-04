package com.moshkou.md.configs;

public class Enumerates {

    public enum Connectivity {
        WIFI,
        MOBILE,
        NO_CONNECTIVITY
    }

    public enum Select {
        NULL,
        SELECT,
        UNSELECT
    }

    public enum LayoutStatus {
        NULL,
        INFO,
        DETAILS,
        SEARCH,
        ADD
    }

    public enum ConfirmationState {
        NULL,
        OK,
        CANCEL
    }

    public enum MediaPicker {
        NULL,
        MEDIA_ACTIVITY,
        STATUS_FRAGMENT
    }

    public enum Message {
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
