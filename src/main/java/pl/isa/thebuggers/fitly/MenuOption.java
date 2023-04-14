package pl.isa.thebuggers.fitly;

public enum MenuOption {

    DUMP(0), // do wygody
    USER(1),
    BMI(2),
    TRAINING(3),
    DIET(4),
    EXIT(5);

    private final int value;

    MenuOption(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}