import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Класс, отвечающий за отрисовку главного окна приложения.
 */
public class StartWindow extends JFrame {
    private JButton playButton; //Кнопка "Играть"
    private JButton exitButton; //Кнопка "Выход"
    private JButton leaderboardButton; //Кнопка "Лидеры"

    public StartWindow() {
        setTitle("Шарики");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(){
            /**
             * Отрисовка изображения на заднем фоне.
             * @param g the <code>Graphics</code> object to protect
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("pikachu.png");
                Image image = imageIcon.getImage();
                int x = (this.getWidth() - image.getWidth(null)) / 2;
                int y = (this.getHeight() - image.getHeight(null)) / 2;
                g.drawImage(image, x, y, this);
            }
        };
        panel.setLayout(null);

        playButton = new JButton("Играть");
        playButton.setBounds(10, 70, 100, 30);
        panel.add(playButton);

        exitButton = new JButton("Выйти");
        exitButton.setBounds(150, 120, 100, 30);
        panel.add(exitButton);

        leaderboardButton = new JButton("Лидеры");
        leaderboardButton.setBounds(150, 70, 100, 30);
        panel.add(leaderboardButton);

        add(panel);

        /**
         * Изменение цвета кнопки "Выход" при наведении на нее.
         */
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(Color.RED);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(UIManager.getColor("control"));
            }
        });

        /**
         * Изменение цвета кнопки "Играть" при наведении на нее.
         */
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playButton.setBackground(Color.GREEN);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playButton.setBackground(UIManager.getColor("control"));
            }
        });

        /**
         * Обработка события нажатия кнопки "Играть". запускается игра, открывается новое окно, данное окно закрывается.
         */
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Шарики");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                GamePanel gamePanel = new GamePanel(frame);
                frame.add(gamePanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                gamePanel.startGame();
                dispose();
            }
        });

        /**
         * Нажатие на кнопку, активирующее выход из приложения
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /**
         * Нажатие на кнопку, открывающее окно лидеров и закрывающее главное.
         */
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LeaderWindow leadersWindow = new LeaderWindow();
                leadersWindow.setVisible(true);

            }
        });
    }
}
