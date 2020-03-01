package callumboyd.model;


import lombok.Getter;

public enum Status {
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    INCOMPLETE("INCOMPLETE");

    @Getter
    private String value;

    Status(String value) {
        this.value = value;
    }

}
