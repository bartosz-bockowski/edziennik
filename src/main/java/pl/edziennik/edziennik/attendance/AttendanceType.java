package pl.edziennik.edziennik.attendance;

public enum AttendanceType {
    PRESENT("green"),
    ABSENT("red"),
    LATE("yellow");
    final String cellColor;

    AttendanceType(String cellColor) {
        this.cellColor = cellColor;
    }

    public String getCellColor() {
        return this.cellColor;
    }
}
