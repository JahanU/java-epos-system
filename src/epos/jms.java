import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonActionListener implements ActionListener {

    private Vending window;

    public ButtonActionListener(Vending window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton pressedButton = (JButton) e.getSource();

        if (pressedButton == window.infoButton) {
            window.totalWindow.setVisible(true);
        } else {
            for (int i = 0; i < window.shopItems.size(); i++) {
                ShopItem shopItem = window.shopItems.get(i);
                if (pressedButton == shopItem.getButton()) {
                    updateStock(i);
                }
            }
        }
    }

    private void updateStock(int i) {
        ShopItem shopItem = window.shopItems.get(i);

        if (shopItem.getStock() > 0) {
            shopItem.decrementStock();
            shopItem.updateLabel();

            window.total += shopItem.getPrice();
            window.totalWindow.label.setText("Total sales = £" + String.format("%.2f", window.total));
        } else {
            JOptionPane.showMessageDialog(window, "Oops, there are none left!");
        }
    }
}