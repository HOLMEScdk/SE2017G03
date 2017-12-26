package ui;

import control.productsManager;
import model.Products;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmProductManager_Modify extends JDialog implements ActionListener {
    private Products product=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确认");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("商品名称");
    private JLabel labelDesc = new JLabel("描        述");
    private JLabel labelClass = new JLabel("类        型");
    private JLabel labelSize = new JLabel("尺        寸");
    private JLabel labelColor = new JLabel("颜        色");
    private JLabel labelRPrice = new JLabel("进  货  价");
    private JLabel labelSPrice = new JLabel("售        价");
    private JLabel labelAPrice = new JLabel("代  理  价");
    private JLabel labelTimeImport = new JLabel("上架时间");
    private JLabel labelTimeEnd = new JLabel("结束销售时间");
    private JLabel labelAmount = new JLabel("数        量");
    private JLabel labelSrc = new JLabel("图片路径");//TODO

    private JTextField edtName = new JTextField(20);
    private JTextField edtDesc = new JTextField(20);
    private JTextField edtClass = new JTextField(20);
    private JTextField edtSize = new JTextField(20);
    private JTextField edtColor = new JTextField(20);
    private JTextField edtRPrice = new JTextField(20);
    private JTextField edtSPrice = new JTextField(20);
    private JTextField edtAPrice = new JTextField(20);
    private JTextField edtTimeImport = new JTextField(20);
    private JTextField edtTimeEnd = new JTextField(18);
    private JTextField edtAmount = new JTextField(20);
    private JTextField edtSrc = new JTextField(20);



    private JComboBox cmbPub=null;
    public FrmProductManager_Modify(JDialog f, String s, boolean b, Products products) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        edtName.setText(products.getName());
        workPane.add(labelDesc);
        workPane.add(edtDesc);
        edtDesc.setText(products.getDescription());
        workPane.add(labelClass);
        workPane.add(edtClass);
        edtClass.setText(products.getClass_belong());
        workPane.add(labelSize);
        workPane.add(edtSize);
        edtSize.setText(String.valueOf(products.getSize()));
        workPane.add(labelColor);
        workPane.add(edtColor);
        edtColor.setText(String.valueOf(products.getColor()));
        workPane.add(labelRPrice);
        workPane.add(edtRPrice);
        edtRPrice.setText(Double.toString(products.getRaw_price()));
        workPane.add(labelSPrice);
        workPane.add(edtSPrice);
        edtSPrice.setText(Double.toString(products.getSale_price()));
        workPane.add(labelAPrice);
        workPane.add(edtAPrice);
        edtAPrice.setText(Double.toString(products.getAgent_price()));
        workPane.add(labelTimeEnd);
        workPane.add(edtTimeEnd);
        edtTimeEnd.setText(products.getTime_end_sale());
        workPane.add(labelAmount);
        workPane.add(edtAmount);
        edtAmount.setText(Integer.toString(products.getAmount()));
        workPane.add(labelSrc);
        workPane.add(edtSrc);
        edtSrc.setText(products.getSrc());

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 480);

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(e.getSource()==this.btnOk){
            String name=this.edtName.getText();
            String Desc = this.edtDesc.getText();
            String Class = this.edtClass.getText();
            String strSize = this.edtSize.getText();
            String[] Size = strSize.split("','|'，'|\t|\n|\r|\f");
            String strColor = this.edtColor.getText();
            String[] Color = strColor.split("','|'，'|\t|\n|\r|\f");
            double RPrice = 0,SPrice=0,APrice=0;
            try{
                RPrice = Double.parseDouble(this.edtRPrice.getText());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "请输入合法数据","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                SPrice = Double.parseDouble(this.edtSPrice.getText());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "请输入合法数据","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                APrice = Double.parseDouble(this.edtAPrice.getText());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "请输入合法数据","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String TimeEnd = this.edtTimeEnd.getText();
            int amount = 0;
            try{
                amount = Integer.parseInt(this.edtAmount.getText());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "请输入合法数据","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //TODO edtSrc("图片路径");没想好怎么写

            Products products=new Products();
            products.setName(name);
            products.setDescription(Desc);
            products.setClass_belong(Class);
            products.setSize(Size);
            products.setColor(Color);
            products.setRaw_price(RPrice);
            products.setSale_price(SPrice);
            products.setAgent_price(APrice);

            products.setTime_end_sale(TimeEnd);
            products.setAmount(amount);
            //TODO products.setSrc() );

            (new productsManager()).modifyProducts(products);
            this.product=products;
            this.setVisible(false);
        }

    }
    public Products getProduct() {
        return product;
    }
}
