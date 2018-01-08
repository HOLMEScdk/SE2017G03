package ui;

import control.OrderManager;
import control.ProductsManager;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class FrmOrderManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();

    private Button btnModify = new Button("完成订单");
    private Button btnStop = new Button("删除订单");
    private TextField edtKeyword= new TextField(20);
    private Button btnSearch = new Button("查找");
    private Button btnRestore = new Button("订单作废");
    private Button btnRefresh = new Button("刷新");

    private Object tblPlanTitle[]={"订单编号","生成时间","总价","地址","用户名","状态"};
    private Object tblPlanData[][];
    DefaultTableModel tabPlanModel=new DefaultTableModel();
    private JTable dataTablePlan=new JTable(tabPlanModel);


    private Object tblStepTitle[]={"商品名称","尺寸","颜色","数量","单价"};
    private Object tblStepData[][];
    DefaultTableModel tabStepModel=new DefaultTableModel();
    private JTable dataTableStep=new JTable(tabStepModel);
    List<Order> orders=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);


    private void reloadPlanTable(){
        orders=(new OrderManager()).searchOrder();
        tblPlanData =  new Object[orders.size()][6];
        for(int i=0;i<orders.size();i++){
            tblPlanData[i][0]=orders.get(i).getId();
            tblPlanData[i][1]=orders.get(i).getDeal_time();
            tblPlanData[i][2]=orders.get(i).getTotal_money();
            tblPlanData[i][3]=orders.get(i).getAddress();
            tblPlanData[i][4]=orders.get(i).getUser_id();
            tblPlanData[i][5]=orders.get(i).getStatus();
        }
        tabPlanModel.setDataVector(tblPlanData,tblPlanTitle);
        this.dataTablePlan.validate();
        this.dataTablePlan.repaint();
    }
    private void reloadPlanStepTabel(int planIdx){
        if(planIdx<0) return;
        Order cur=orders.get(planIdx);
        tblStepData =new Object[cur.getProduct_name().size()][5];
        for(int i=0;i<cur.getProduct_name().size();i++){
            tblStepData[i][0]=cur.getProduct_name().get(i);
            tblStepData[i][1]=cur.getSize().get(i);
            tblStepData[i][2]=cur.getColor().get(i);
            tblStepData[i][3]=cur.getAmount().get(i);
            tblStepData[i][4]=cur.getSingle_price().get(i);
        }
        tabStepModel.setDataVector(tblStepData,tblStepTitle);
        this.dataTableStep.validate();
        this.dataTableStep.repaint();
    }

    public FrmOrderManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnModify);
        toolBar.add(btnStop);
//        toolBar.add(edtKeyword);
//        toolBar.add(btnSearch);
        toolBar.add(btnRestore);
        toolBar.add(btnRefresh);

        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadPlanTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();


        this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
        this.dataTablePlan.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmOrderManager.this.dataTablePlan.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmOrderManager.this.reloadPlanStepTabel(i);
            }
        });
        this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);

        this.btnModify.addActionListener(this);
        this.btnStop.addActionListener(this);
        this.btnSearch.addActionListener(this);
        this.btnRestore.addActionListener(this);
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
        if(e.getSource()==this.btnModify){
            int i=this.dataTablePlan.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择订单","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

           Order cur=this.orders.get(i);
            if(cur.getStatus().equals("已完成")){
                JOptionPane.showMessageDialog(null,  "无需重复操作","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(cur.getStatus().equals("作废")){
                JOptionPane.showMessageDialog(null,  "该订单已作废","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            (new OrderManager()).modifyOrder(cur,"已完成");
            //(new ProductsManager()).reduceProduct(cur);

            this.reloadPlanTable();
        }
        else if(e.getSource()==this.btnStop){
            int i=this.dataTablePlan.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择订单","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            Order cur=this.orders.get(i);
            (new OrderManager()).deleteOrder(cur.getId());
            JOptionPane.showMessageDialog(null,  "删除成功","提示",JOptionPane.ERROR_MESSAGE);
            this.reloadPlanTable();
        }
        else if(e.getSource()==this.btnSearch){
            // this.reloadTable();
        }
        else if(e.getSource()==this.btnRestore){
            int i=this.dataTablePlan.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择订单","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            Order cur=this.orders.get(i);
            if(cur.getStatus().equals("已完成")){
                JOptionPane.showMessageDialog(null,  "该订单不可恢复","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if(cur.getStatus().equals("作废")){
                JOptionPane.showMessageDialog(null,  "无需重复操作","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            (new OrderManager()).modifyOrder(cur,"作废");
            (new ProductsManager()).restoreProduct(cur);
            this.reloadPlanTable();
        }
        else if(e.getSource()==this.btnRefresh){
            this.reloadPlanTable();
        }
    }

}
