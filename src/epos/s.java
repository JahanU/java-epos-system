import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Vending extends JFrame {

    public ArrayList<ShopItem> shopItems;
    public TotalWindow totalWindow;
    public JButton infoButton;
    public double total = 0;

    public static void main(String[] args) {
        Vending window = new Vending("Vending Machine");
        window.setLayout(null);
        window.setSize(896, 540);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public Vending(String title) {
        super(title);

        shopItems = new ArrayList<ShopItem>();

        addNewShopItem("Chocolate Jigglypuffs", 1.30, 4);
        addNewShopItem("Chocolate Bombs", 1.00, 4);
        addNewShopItem("Chocolate Twists", 0.80, 4);
        addNewShopItem("Caramel Jigglypuffs", 1.30, 4);
        addNewShopItem("Caramel Bombs", 1.00, 4);
        addNewShopItem("Caramel Twists", 0.80, 4);
        addNewShopItem("French Vanilla Jigglypuffs", 1.30, 4);
        addNewShopItem("French Vanilla Bombs", 1.00, 4);
        addNewShopItem("French Vanilla Twists", 0.80, 4);

        initialiseShop();
    }

    private void addNewShopItem(String name, double price, int stock) {
        shopItems.add(new ShopItem(this, name, price, stock, shopItems.size()));
    }

    private void initialiseShop() {
        infoButton = new JButton("Vender Info");
        infoButton.addActionListener(new ButtonActionListener(this));
        infoButton.setBounds(32, ((64 + 32) * (shopItems.size() / 3)) + 64, 256, 64);
        add(infoButton);

        totalWindow = new TotalWindow("Vendor info", this);
    }
}