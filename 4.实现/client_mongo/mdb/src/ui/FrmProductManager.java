package ui;

import control.productsManager;
import model.Products;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class FrmProductManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("新增商品");
    private Button btnModify = new Button("修改商品");
    private Button btnStop = new Button("删除商品");
    private JComboBox cmbState=new JComboBox(new String[]{"L","XX","X"});
    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查找");
    private Object tblTitle[]={"商品编号","商品编号","商品类别","价格","数量"};
    private Object tblData[][];
    List<Products> products=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
//        try {
//            products=(new productsManager()).searchproducts(this.edtKeyword.getText(), this.cmbState.getSelectedItem().toString());
//            tblData =new Object[products.size()][5];
//            for(int i=0;i<products.size();i++){
//                tblData[i][0]=products.get(i).getBarcode();
//                tblData[i][1]=products.get(i).getproductsname();
//                tblData[i][2]=products.get(i).getPubName();
//                tblData[i][3]=products.get(i).getPrice()+"";
//                tblData[i][4]=products.get(i).getState();
//            }
//            tablmod.setDataVector(tblData,tblTitle);
//            this.dataTable.validate();
//            this.dataTable.repaint();
//        } catch (BaseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    public FrmProductManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(btnStop);
        toolBar.add(cmbState);
        toolBar.add(edtKeyword);
        toolBar.add(btnSearch);


        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);


        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnStop.addActionListener(this);
        this.btnSearch.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==this.btnAdd){
//            FrmproductsManager_Addproducts dlg=new FrmproductsManager_Addproducts(this,"Ìí¼ÓÍ¼Êé",true);
//            dlg.setVisible(true);
//            if(dlg.getproducts()!=null){//Ë¢ÐÂ±í¸ñ
//                this.reloadTable();
//            }
        }
        else if(e.getSource()==this.btnModify){
//            int i=this.dataTable.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null,  "ÇëÑ¡ÔñÍ¼Êé","ÌáÊ¾",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            Beanproducts product=this.products.get(i);
//
//            FrmproductsManager_Modifyproducts dlg=new FrmproductsManager_Modifyproducts(this,"ÐÞ¸ÄÍ¼Êé",true,product);
//            dlg.setVisible(true);
//            if(dlg.getproducts()!=null){//Ë¢ÐÂ±í¸ñ
//                this.reloadTable();
//            }
        }
        else if(e.getSource()==this.btnStop){
//            int i=this.dataTable.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null,  "ÇëÑ¡ÔñÍ¼Êé","ÌáÊ¾",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            Beanproducts product=this.products.get(i);
//            if(!"ÔÚ¿â".equals(product.getState())){
//                JOptionPane.showMessageDialog(null,  "µ±Ç°Í¼ÊéÃ»ÓÐ¡®ÔÚ¿â¡¯","ÌáÊ¾",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if(JOptionPane.showConfirmDialog(this,"È·¶¨É¾³ý"+product.getproductsname()+"Âð£¿","È·ÈÏ",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
//                product.setState("ÒÑÉ¾³ý");
//                try {
//                    (new productsManager()).modifyproducts(product);
//                    this.reloadTable();
//                } catch (BaseException e1) {
//                    JOptionPane.showMessageDialog(null, e1.getMessage(),"´íÎó",JOptionPane.ERROR_MESSAGE);
//                }
//            }
        }
        else if(e.getSource()==this.btnSearch){
           // this.reloadTable();
        }

    }
}
