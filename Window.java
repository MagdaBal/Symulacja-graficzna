import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() throws HeadlessException{
        setTitle("Garden");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(Garden.getInstance());
        pack();
        setLocationRelativeTo(null);
    }
}
