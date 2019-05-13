import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TotalWindow extends JFrame {

    Vending mainWindow;
    JLabel label;
    JButton button;

    public TotalWindow(String title, Vending mainWindow) {
        super(title);
        this.mainWindow = mainWindow;
        setLayout(new FlowLayout());
        setVisible(false);
        setSize(128, 128);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        label = new JLabel("Total sales = " + mainWindow.total);
        button = new JButton("Reset stock");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetStock();
            }
        });

        add(label);
        add(button);
    }

    private void resetStock() {
        for (int i = 0; i < mainWindow.shopItems.size(); i++) {
            ShopItem shopItem = mainWindow.shopItems.get(i);
            shopItem.resetStock();
            shopItem.updateLabel();
        }
    }
}