package ui;

import control.productsManager;
import model.Products;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


public class FrmProductManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("新增商品");
    private Button btnModify = new Button("修改商品");
    private Button btnStop = new Button("删除商品");
    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查找");
    private Object tblTitle[]={"商品编号","商品编号","商品类别","价格","数量"," ", " "," "," "," ", " "};
    private Object tblData[][];
    List<String[]> products=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        products = (new productsManager()).searchProducts();
        tblData =new Object[products.size()][11];
        for(int i=0;i<products.size();i++){
            tblData[i][0]=products.get(i)[0];
            tblData[i][1]=products.get(i)[1];
            tblData[i][2]=products.get(i)[2];
            tblData[i][3]=products.get(i)[3];
            tblData[i][4]=products.get(i)[4];
            tblData[i][5]=products.get(i)[5];
            tblData[i][6]=products.get(i)[6];
            tblData[i][7]=products.get(i)[7];
            tblData[i][8]=products.get(i)[8];
            tblData[i][9]=products.get(i)[9];
            tblData[i][10]=products.get(i)[10];
        }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
    }

    public FrmProductManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(btnStop);
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
            FrmProductManager_Add dlg=new FrmProductManager_Add(this,"添加商品",true);
            dlg.setVisible(true);
            if(dlg.getProduct()!=null){
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            //Products product=this.products.get(i);
            Products product=null;
            FrmProductManager_Modify dlg=new FrmProductManager_Modify(this,"修改成功",true,product);
            dlg.setVisible(true);
            if(dlg.getProduct()!=null){
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnStop){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = this.products.get(i)[0];
            (new productsManager()).deleteProducts(id);
                    this.reloadTable();
        }
        else if(e.getSource()==this.btnSearch){
           // this.reloadTable();
        }

    }
}
