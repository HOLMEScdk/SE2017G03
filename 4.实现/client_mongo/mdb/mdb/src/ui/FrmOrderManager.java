//package ui;
//
//import model.Order;
//import util.BaseException;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.List;
//
//
//public class FrmOrderManager extends JDialog implements ActionListener {
//    private JPanel toolBar = new JPanel();
//
//    private Button btnModify = new Button("处理订单");
//    private Button btnStop = new Button("删除订单");
//    private TextField edtKeyword= new TextField(20);
//    private Button btnSearch = new Button("查找");
//    private Object tblTitle[]={"订单编号","订单编号","订单类别","价格","数量"};
//    private Object tblData[][];
//    List<Order> orders=null;
//    DefaultTableModel tablmod=new DefaultTableModel();
//    private JTable dataTable=new JTable(tablmod);
//    private void reloadPlanTable(){//这是测试数据，需要用实际数替换
//        try {
//            allPlan=PersonPlanUtil.planManager.loadAll();
//        } catch (BaseException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        tblPlanData =  new Object[allPlan.size()][BeanPlan.tableTitles.length];
//        for(int i=0;i<allPlan.size();i++){
//            for(int j=0;j<BeanPlan.tableTitles.length;j++)
//                tblPlanData[i][j]=allPlan.get(i).getCell(j);
//        }
//        tabPlanModel.setDataVector(tblPlanData,tblPlanTitle);
//        this.dataTablePlan.validate();
//        this.dataTablePlan.repaint();
//    }
//    private void reloadPlanStepTabel(int planIdx){
//        if(planIdx<0) return;
//        curPlan=allPlan.get(planIdx);
//        try {
//            planSteps=PersonPlanUtil.stepManager.loadSteps(curPlan);
//        } catch (BaseException e) {
//            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        tblStepData =new Object[planSteps.size()][BeanStep.tblStepTitle.length];
//        for(int i=0;i<planSteps.size();i++){
//            for(int j=0;j<BeanStep.tblStepTitle.length;j++)
//                tblStepData[i][j]=planSteps.get(i).getCell(j);
//        }
//
//        tabStepModel.setDataVector(tblStepData,tblStepTitle);
//        this.dataTableStep.validate();
//        this.dataTableStep.repaint();
//    }
//
//    public FrmOrderManager(Frame f, String s, boolean b) {
//        super(f, s, b);
//        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//        toolBar.add(btnModify);
//        toolBar.add(btnStop);
//        toolBar.add(edtKeyword);
//        toolBar.add(btnSearch);
//
//
//        this.getContentPane().add(toolBar, BorderLayout.NORTH);
//
//        this.reloadTable();
//        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
//
//        this.setSize(800, 600);
//        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//        this.setLocation((int) (width - this.getWidth()) / 2,
//                (int) (height - this.getHeight()) / 2);
//
//        this.validate();
//
//        this.btnModify.addActionListener(this);
//        this.btnStop.addActionListener(this);
//        this.btnSearch.addActionListener(this);
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                //System.exit(0);
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        // TODO Auto-generated method stub
//        if(e.getSource()==this.btnModify){
//            int i=this.dataTable.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null,  "请选择订单","错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//           Order order=this.orders.get(i);
//
////            FrmOrderManager_Modify dlg=new FrmOrderManager_Modify(this,"处理订单",true,order);
////            dlg.setVisible(true);
////            if(dlg.getOrder()!=null){
////                this.reloadTable();
////            }
//        }
//        else if(e.getSource()==this.btnStop){
////            int i=this.dataTable.getSelectedRow();
////            if(i<0) {
////                JOptionPane.showMessageDialog(null,  "ÇëÑ¡ÔñÍ¼Êé","ÌáÊ¾",JOptionPane.ERROR_MESSAGE);
////                return;
////            }
////            Beanproducts product=this.products.get(i);
////            if(!"ÔÚ¿â".equals(product.getState())){
////                JOptionPane.showMessageDialog(null,  "µ±Ç°Í¼ÊéÃ»ÓÐ¡®ÔÚ¿â¡¯","ÌáÊ¾",JOptionPane.ERROR_MESSAGE);
////                return;
////            }
////            if(JOptionPane.showConfirmDialog(this,"È·¶¨É¾³ý"+product.getproductsname()+"Âð£¿","È·ÈÏ",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
////                product.setState("ÒÑÉ¾³ý");
////                try {
////                    (new productsManager()).modifyproducts(product);
////                    this.reloadTable();
////                } catch (BaseException e1) {
////                    JOptionPane.showMessageDialog(null, e1.getMessage(),"´íÎó",JOptionPane.ERROR_MESSAGE);
////                }
////            }
//        }
//        else if(e.getSource()==this.btnSearch){
//            // this.reloadTable();
//        }
//
//    }
//
//}
