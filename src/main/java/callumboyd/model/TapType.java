package callumboyd.model;

import lombok.Getter;


public enum TapType {
    ON("ON"),
    OFF("OFF");

    @Getter
    private String value;

    TapType(String value) {
        this.value = value;
    }
}
