import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell {
    private Integer value; // цифры 1-8, 9 - бомба.
    private Integer mark; //1 - не помечено, 2 - флаг, 3 - вопрос
    private boolean opened;

    public void setValue(Integer a){this.value = a;}
    public void setMark(Integer a){this.mark = a;}
    public Integer getMark(){return this.mark;}
    public Integer getValue(){return this.value;}
    public Boolean isBomb(){
        if(value == 9) return true; else return false;
    }
    public void open(){
        this.opened = true;
    }
}
