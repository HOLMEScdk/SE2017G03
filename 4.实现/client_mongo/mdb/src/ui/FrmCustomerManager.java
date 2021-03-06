package ui;

import control.CustomerManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmCustomerManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnStop = new Button("删除用户");
    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查找");
    private Button btnRefresh = new Button("刷新");

    private Object tblTitle[]={"ID","性别","密码","默认地址编号"};
    private Object tblData[][];
    List<String[]> cus=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
            cus=(new CustomerManager()).searchCustomer();
            tblData =new Object[cus.size()][4];
            for(int i=0;i<cus.size();i++){
                tblData[i][0]=cus.get(i)[0];
                tblData[i][1]=cus.get(i)[1];
                tblData[i][2]=cus.get(i)[2];
                tblData[i][3]=cus.get(i)[3];
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
    }

    public FrmCustomerManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnStop);
        toolBar.add(btnRefresh);
        //toolBar.add(edtKeyword);
        //toolBar.add(btnSearch);

        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        this.setSize(900, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnStop.addActionListener(this);
        this.btnRefresh.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==this.btnStop){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择用户","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String id = this.cus.get(i)[0];
            (new CustomerManager()).deleteCustomer(id);
            JOptionPane.showMessageDialog(null,  "删除成功","提示",JOptionPane.ERROR_MESSAGE);
            this.reloadTable();
        }
        else if(e.getSource()==this.btnSearch){
            // this.reloadTable();
        }
        else if(e.getSource()==this.btnRefresh){
            this.reloadTable();
        }
    }
}
