import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс, отвечающий за создание и отрисовку окна конца игры.
 */
public class EndGameWindow extends JFrame {
    private JTextField nameField; //поле ввода имени
    private JButton exitButton; //кнопка "Выход"
    private JButton returnToMenuButton; //кнопка "В меню"

    public EndGameWindow(int score) {
        setTitle("Конец игры");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel endGameLabel = new JLabel("Конец игры");
        endGameLabel.setBounds(100, 10, 100, 20);
        panel.add(endGameLabel);

        JLabel scoreLabel = new JLabel("Ваши очки: " + score);
        scoreLabel.setBounds(20, 40, 200, 20);
        panel.add(scoreLabel);

        JLabel nameLabel = new JLabel("Введите имя");
        nameLabel.setBounds(20, 70, 100, 20);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 70, 150, 20);
        panel.add(nameField);

        exitButton = new JButton("Выход");
        exitButton.setBounds(50, 100, 80, 30);
        panel.add(exitButton);

        returnToMenuButton = new JButton("В меню");
        returnToMenuButton.setBounds(140, 100, 120, 30);
        panel.add(returnToMenuButton);

        /**
         * Сохранение результатов в файл, закрытие приложения.
         *
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData(score);
                System.exit(0);
            }
        });

        /**
         * Сохранение резульатов в файл, открытие главного окна и закрытие окна окончания игры.
         *
         */
        returnToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData(score);
                StartWindow startWindow = new StartWindow();
                startWindow.setVisible(true);
                dispose();
            }
        });
        add(panel);
    }

    /**
     * Сохранение имени игрока и его счет в файл в зашифрованном виде.
     * @param score счетчик накопленных баллов.
     */
    private void saveData(int score){
        String nickName;
        if(!nameField.getText().isEmpty()){
            nickName = nameField.getText();
        } else {
            nickName = "Игрок";
        }
        int encyptScore = (score+10)% 2;
        String encyptName = encryptedName(nickName);

        try (FileWriter writer = new FileWriter("leaderboard.txt", true)) {
            writer.write(encyptName + ", " + encyptScore + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("src/leaderboardhide.txt", true)) {
            writer.write(nickName + ", " + score + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Шифрование текста при помощи шифра цезаря со сдвигом на 2 символа.
     * @param nickName имя пользователя, берущиеся из поля ввода.
     * @return возвращает зашифрованное имя игрока.
     */
    private String encryptedName(String nickName){
        int key = 2;
        StringBuilder encryptedName = new StringBuilder();
        for (int i = 0; i < nickName.length(); i++) {
            char c = nickName.charAt(i);
            if (Character.isUpperCase(c)) {
                char encryptedChar = (char) ((c + key - 65) % 26 + 65);
                encryptedName.append(encryptedChar);
            } else if (Character.isLowerCase(c)) {
                char encryptedChar = (char) ((c + key - 97) % 26 + 97);
                encryptedName.append(encryptedChar);
            } else {
                encryptedName.append(c);
            }
        }
        return encryptedName.toString();
    }

}
