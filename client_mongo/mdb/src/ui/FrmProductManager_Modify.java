package ui;

import control.ProductsManager;
import model.Products;
import util.Upload;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class FrmProductManager_Modify extends JDialog implements ActionListener {
    private Products product=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确认");
    private Button btnCancel = new Button("取消");
    private Button btnUpdate = new Button("上传图片");

    private JLabel labelName = new JLabel("商品名称");
    private JLabel labelDesc = new JLabel("描        述");
    private JLabel labelClass = new JLabel("类        型");
    //private JLabel labelSize = new JLabel("尺        寸");
    //private JLabel labelColor = new JLabel("颜        色");
    private JLabel labelRPrice = new JLabel("进  货  价");
    private JLabel labelSPrice = new JLabel("售        价");
    private JLabel labelAPrice = new JLabel("代  理  价");
    private JLabel labelTimeImport = new JLabel("上架时间");
    private JLabel labelTimeEnd = new JLabel("结束销售时间");
    private JLabel labelAmount = new JLabel("颜色，尺码，对应数量");
    private JLabel labelSrc = new JLabel("图片路径");//TODO

    private JTextField edtName = new JTextField(20);
    private JTextField edtDesc = new JTextField(20);
    private JTextField edtClass = new JTextField(20);
    //private JTextField edtSize = new JTextField(20);
   // private JTextField edtColor = new JTextField(20);
    private JTextField edtRPrice = new JTextField(20);
    private JTextField edtSPrice = new JTextField(20);
    private JTextField edtAPrice = new JTextField(20);
    private JTextField edtTimeImport = new JTextField(20);
    private JTextField edtTimeEnd = new JTextField(18);
    private JTextField edtAmount = new JTextField(25);
    private JTextField edtSrc = new JTextField(15);



    private JComboBox cmbPub=null;
    private String oldSrc="";
    public FrmProductManager_Modify(JDialog f, String s, boolean b,String[] products) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        edtName.setText(products[1]);
        workPane.add(labelDesc);
        workPane.add(edtDesc);
        edtDesc.setText(products[2]);
        workPane.add(labelClass);
        workPane.add(edtClass);
        edtClass.setText(products[3]);
//        workPane.add(labelSize);
//        workPane.add(edtSize);
//        edtSize.setText(products[4]);
//        workPane.add(labelColor);
//        workPane.add(edtColor);
//        edtColor.setText(products[5]);
        workPane.add(labelRPrice);
        workPane.add(edtRPrice);
        edtRPrice.setText(products[6]);
        workPane.add(labelSPrice);
        workPane.add(edtSPrice);
        edtSPrice.setText(products[7]);
        workPane.add(labelAPrice);
        workPane.add(edtAPrice);
        edtAPrice.setText(products[8]);
        workPane.add(labelTimeEnd);
        workPane.add(edtTimeEnd);
        edtTimeEnd.setText(products[11]);
        workPane.add(labelAmount);
        workPane.add(edtAmount);
        edtAmount.setText(products[9]);
        workPane.add(labelSrc);
        workPane.add(edtSrc);
        edtSrc.setText(products[12]);
        workPane.add(btnUpdate);
        oldSrc = products[12];

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 480);

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.btnUpdate.addActionListener(this);
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
//            String strSize = this.edtSize.getText();
//            String[] Size = strSize.split("','|'，'|\t|\n|\r|\f");
//            String strColor = this.edtColor.getText();
//            String[] Color = strColor.split("','|'，'|\t|\n|\r|\f");
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

            String s = this.edtAmount.getText();
            String[] str=s.split(",|@| ");
            System.out.println(str.length);
            ArrayList<Integer> x= new ArrayList<Integer>();
            ArrayList<String> y=new ArrayList<String>();
            ArrayList<String> z=new ArrayList<String>();
            try {
                for(int i=0; i<str.length; i+=3){
                    y.add(str[i]);
                    z.add(str[i+1]);
                    x.add(Integer.parseInt(str[i+2]));
                    System.out.println(str[i]+" "+str[i+1]+" "+str[i+2]);
                }
                int amount[]=new int[x.size()];
                String[] Color =new String[y.size()];
                String[] Size =new String[z.size()];
                for(int i = 0; i < x.size();i++){
                    Color[i]=y.get(i);
                    Size[i]=z.get(i);
                    amount[i]=x.get(i);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "请输入合法数据", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String temp = edtSrc.getText();

            Products products=new Products();
            products.setName(name);
            products.setDescription(Desc);
            products.setClass_belong(Class);
            products.setSize(z);
            products.setColor(y);
            products.setRaw_price(RPrice);
            products.setSale_price(SPrice);
            products.setAgent_price(APrice);
            products.setTime_end_sale(TimeEnd);
            products.setAmount(x);

            String src="";
            if(oldSrc.equals(temp)) {
                products.setSrc(temp);
            }
            else{
                String[] ans = temp.split("\\\\");
                src = "product\\" + ans[ans.length - 1];
                products.setSrc(src);
            }

            try{
                Upload.upload(temp,src);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "图片上传失败", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            (new ProductsManager()).modifyProducts(products);
            this.product=products;
            this.setVisible(false);
        }
        else if(e.getSource()==this.btnUpdate){
            JFileChooser jfc=new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            this.edtSrc.setText(file.getAbsolutePath());

        }
    }
    public Products getProduct() {
        return product;
    }

}
