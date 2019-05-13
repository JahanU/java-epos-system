import javax.swing.*;

class ShopItem {

    private final Vending window;

    private final String name;
    private final double price;
    private int stock;
    private final int position;

    private JButton button;
    private JLabel label;

    public ShopItem(Vending window, String name, double price, int stock, int position) {
        this.window = window;

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.position = position;

        initialiseButton();
        initialiseLabel();
    }

    public void initialiseButton() {
        button = new JButton(getInfoDisplayText());
        button.addActionListener(new ButtonActionListener(window));
        button.setBounds(((256 + 32) * (position % 3)) + 32, ((64 + 32) * (position / 3)) + 32, 256, 64);
        window.add(button);
    }

    public void initialiseLabel() {
        label = new JLabel(getStockDisplayText());
        label.setBounds(((256 + 32) * (position % 3)) + 32, ((64 + 32) * (position / 3)) + 96, 256, 16);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(label);
    }

    public String getInfoDisplayText() {
        return name + " (£" + String.format("%.2f", price) + ")";
    }

    public String getStockDisplayText() {
        return stock + " left";
    }

    public void updateLabel() {
        label.setText(getStockDisplayText());
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decrementStock() {
        stock--;
    }

    public void resetStock() {
        stock = 4;
    }

    public JButton getButton() {
        return button;
    }
}