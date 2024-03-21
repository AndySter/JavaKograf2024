import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    public static int WIDTH = 800; //Ширина окна
    public static int HEIGHT = 600; //Высота окна
    private Thread gameThread;
    private JFrame frame;
    private boolean running;
    private ArrayList<Balloon> balloons; //Лист шаров
    private Random rand; //Использование рандома
    private int score; //Количество очков
    private int missedBalloons; //Количество пропущенных шаров
    private Color lastPoppedColor = null; //Цвет для определения счета 3х лопнувшних шаров
    private int colorStreak = 0; //Счетчик 3х лопнувших шаров
    private int BONUS_POINTS = 5; //Бонус за 3х лопнувшие шары

    public GamePanel(JFrame frame) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        this.frame = frame;
        rand = new Random();
        balloons = new ArrayList<>();
        score = 0;
        missedBalloons = 0;


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Iterator<Balloon> iterator = balloons.iterator();
                while (iterator.hasNext()) {
                    Balloon balloon = iterator.next();
                    if (balloon.isHit(e.getX(), e.getY())) {
                        iterator.remove();
                        score += getColorScore(balloon.getColor());
                        break;
                    }
                }
            }
        });
        startGame();
    }

    /**
     * Запуск потока игры.
     */
    void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Обновление потока игры.
     */
    @Override
    public void run() {
        while (running) {
            updateGame();
            repaint();
            try {
                Thread.sleep(40);   //Обозначение времени обновления потока (кадров в секунду)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод рандомно добавляющий шарик на поле в нижней части окна. Обновление его скорости в зависимости от
     * набранных очков.
     * Обновление местоположения шара в окне.
     * Обработка проигрыша игры. Закрытие окна игры и открытие окна конца игры.
     */
    private void updateGame() {
        if (rand.nextInt(100) < 3) {
            int x = rand.nextInt(WIDTH - 60);
            //int dy = rand.nextInt(2)+1;
            int dy = 1;
            if (score >= 20){
                dy = 2;
            } else if (score >= 60){
                dy = 3;
            }
            Color color = getRandomColor();
            balloons.add(new Balloon(x, HEIGHT, 0, -dy, color));
        }

        Iterator<Balloon> iterator = balloons.iterator();
        while (iterator.hasNext()) {
            Balloon balloon = iterator.next();
            balloon.move();
            if (balloon.getY() < 0) {
                iterator.remove();
                missedBalloons++;
                if (missedBalloons >= 3) {
                    running = false;
                    frame.dispose();
                    EndGameWindow endgame = new EndGameWindow(score);
                    endgame.setVisible(true);
                }
            }
        }
    }

    /**
     * Рандомное присвое цвета шару.
     * @return возвращает цвет шара.
     */
    private Color getRandomColor(){
        int colorChoice = rand.nextInt(3);
        switch (colorChoice) {
            case 0:
                return Color.RED;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    /**
     * Отрисовка листа шаров и количества очков на экране.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Balloon balloon : balloons) {
            balloon.draw(g);
        }
        drawScore(g);
    }

    /**
     * Метод возвращающий количество очков за попадание по шару определенного цвета.
     * Так же определение уничтожения 3х шаров одного цвета подряд.
     * @param color
     * @return
     */
    private int getColorScore(Color color) {
        int score;
        if (color.equals(Color.RED)) {
            score = 3;
        } else if (color.equals(Color.YELLOW)) {
            score = 2;
        } else {
            score = 1;
        }

        if (lastPoppedColor != null && lastPoppedColor.equals(color)) {
            colorStreak++;
            if (colorStreak % 3 == 0) {
                score += BONUS_POINTS;
            }
        } else {
            colorStreak = 1;
        }
        lastPoppedColor = color;
        return score;
    }

    /**
     * Отрисовка очков во время игры в верхнем левом углу.
     * @param g
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        String scoreText = "Очки: " + score;
        g.drawString(scoreText, 10, 30);
    }
}
