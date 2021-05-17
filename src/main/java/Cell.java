import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell {
    private boolean opened;

    public boolean isOpened() {
        return opened;
    }

    public enum Mark {square, flag}

    private Mark mark;

    public enum Value {zero, one, two, three, four, five, six, seven, eight, bomb}

    private Value value;


    public void setValue(Value a) {
        this.value = a;
    }

    public void setMark(Mark a) {
        this.mark = a;
    }

    public Mark getMark() {
        return this.mark;
    }

    public Value getValue() {
        return this.value;
    }



    public Boolean isBomb() {
        return this.value == Value.bomb;
    }

    public void open() {
        this.opened = true;
    }
}
