import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class LeaderWindow extends JFrame {
    private JButton backButton; //Кнопка "Назад"
    private String [] leadertext  = new String[9]; //Массив для строк
    public LeaderWindow() {
        setTitle("Лидеры");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        try {
            getText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < leadertext.length; i++) {
            JLabel label = new JLabel(leadertext[i]);
            label.setBounds(20, 20+30*i, 100, 20);
            panel.add(label);
        }

        backButton = new JButton("Назад");
        backButton.setBounds(100, 300, 100, 30);
        panel.add(backButton);

        /**
         * Обработка события нажатия кнопки "Назад". Данное окно закрывается и открывается главное окно приложения.
         */
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow startWindow = new StartWindow();
                startWindow.setVisible(true);
                dispose();
            }
        });
        add(panel);
    }

    /**
     * Чтение текста из файла
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void getText() throws FileNotFoundException, IOException{
        FileReader fille = new FileReader("src/leaderboardhide.txt");
        BufferedReader newText = new BufferedReader(fille);
        for (int i = 0;i<leadertext.length;i++)
            leadertext[i]=newText.readLine();
        fille.close();
        newText.close();
    }
}
