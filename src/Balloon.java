import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Класс Balloon отвечает за движение и отрисовку шаров.
 */
public class Balloon {
    private int x, y; // Позиции шарика на панели
    private int dx, dy; // Скорость движения шарика по осям X и Y
    private final int size = 50; // Размер шарика
    private final Color color; // Цвет шарика
    private Random rand; // Использование рандома для движения шариков

    public Balloon(int x, int y, int dx, int dy, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        this.rand = new Random();

        int movementType = rand.nextInt(3);

        //Разная скорость для шаров, рандомное направление красных и желтых шаров
        if (color.equals(Color.RED)|| color.equals(Color.YELLOW)){
            if (color.equals(Color.RED)) {
                this.dy *= 1.4;
            } else if (color.equals(Color.YELLOW)) {
                this.dy *= 1.2;
            }
            if (movementType == 1){
                this.dx = 2;
            } else {
                this.dx = -2;
            }
        }
    }

    /**
     * Отрисовка шара размерами size.
     * @param g
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    /**
     * Движение шара, где красный движется по синусоиде, желтый по диагонали, зеленый ровно вверх.
     * Если шар сталкивается с боками, то он отталкивается.
     */
    public void move() {

        if(color.equals(Color.RED)){
            y +=dy;
            x = (int) (x + 40 * Math.sin(dx * 0.1));
        } else if (color.equals(Color.YELLOW)) {
            y += dy;
            x += dx;
        } else {
            y += dy;
        }

        if (x < 0) {
            dx = -dx;
            x = 0;
        } else if( x > GamePanel.WIDTH - size){
            x = GamePanel.WIDTH - size;
            dx = -dx;
        }
    }

    /**
     * Проверка на расположение мышки внутри шара.
     * @param mouseX параметр по осиХ.
     * @param mouseY параметр по осиY.
     * @return верность или ложность попадания мышки по шару.
     */
    public boolean isHit(int mouseX, int mouseY) {
        return mouseX >= x-3 && mouseX <= x + size+3 && mouseY >= y-3 && mouseY <= y + size+3;
    }

    /**
     *
     * @return возвращает координату y.
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return возвращает цвет.
     */
    public Color getColor() {
        return color;
    }
}

