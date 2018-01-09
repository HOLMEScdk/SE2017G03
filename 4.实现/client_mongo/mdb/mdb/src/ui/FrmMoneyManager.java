//package ui;
//
//import util.BaseException;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.*;
//
//public class FrmMoneyManager  extends JFrame implements ActionListener {
//    private JMenuBar menubar=new JMenuBar(); ;
//    private JMenu menu_plan=new JMenu("计划管理");
//    private JMenu menu_step=new JMenu("步骤管理");
//    private JMenu menu_static=new JMenu("查询统计");
//    private JMenu menu_more=new JMenu("更多");
//
//    private JMenuItem  menuItem_AddPlan=new JMenuItem("新建计划");
//    private JMenuItem  menuItem_DeletePlan=new JMenuItem("删除计划");
//    private JMenuItem  menuItem_AddStep=new JMenuItem("添加步骤");
//    private JMenuItem  menuItem_DeleteStep=new JMenuItem("删除步骤");
//    private JMenuItem  menuItem_startStep=new JMenuItem("开始步骤");
//    private JMenuItem  menuItem_finishStep=new JMenuItem("结束步骤");
//
//    private JMenuItem  menuItem_modifyPwd=new JMenuItem("密码修改");
//
//    private JMenuItem  menuItem_static1=new JMenuItem("统计1");
//
//    private JPanel statusBar = new JPanel();
//
//    private Object tblPlanTitle[]=BeanPlan.tableTitles;
//    private Object tblPlanData[][];
//    DefaultTableModel tabPlanModel=new DefaultTableModel();
//    private JTable dataTablePlan=new JTable(tabPlanModel);
//
//
//    private Object tblStepTitle[]=BeanStep.tblStepTitle;
//    private Object tblStepData[][];
//    DefaultTableModel tabStepModel=new DefaultTableModel();
//    private JTable dataTableStep=new JTable(tabStepModel);
//
//    private BeanPlan curPlan=null;
//    List<BeanPlan> allPlan=null;
//    List<BeanStep> planSteps=null;
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
//    public FrmMoneyManager(){
//
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
//        this.setTitle("个人计划管理系统");
//        //菜单
//        this.menu_plan.add(this.menuItem_AddPlan); this.menuItem_AddPlan.addActionListener(this);
//        this.menu_plan.add(this.menuItem_DeletePlan); this.menuItem_DeletePlan.addActionListener(this);
//        this.menu_step.add(this.menuItem_AddStep); this.menuItem_AddStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_DeleteStep); this.menuItem_DeleteStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_startStep); this.menuItem_startStep.addActionListener(this);
//        this.menu_step.add(this.menuItem_finishStep); this.menuItem_finishStep.addActionListener(this);
//        this.menu_static.add(this.menuItem_static1); this.menuItem_static1.addActionListener(this);
//        this.menu_more.add(this.menuItem_modifyPwd); this.menuItem_modifyPwd.addActionListener(this);
//
//        menubar.add(menu_plan);
//        menubar.add(menu_step);
//        menubar.add(menu_static);
//        menubar.add(menu_more);
//        this.setJMenuBar(menubar);
//
//        this.getContentPane().add(new JScrollPane(this.dataTablePlan), BorderLayout.WEST);
//        this.dataTablePlan.addMouseListener(new MouseAdapter(){
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int i=FrmMain.this.dataTablePlan.getSelectedRow();
//                if(i<0) {
//                    return;
//                }
//                FrmMain.this.reloadPlanStepTabel(i);
//            }
//
//        });
//        this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);
//
//        this.reloadPlanTable();
//        //状态栏
//        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//        JLabel label=new JLabel("您好!");//修改成   您好！+登陆用户名
//        statusBar.add(label);
//        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
//        this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });
//        this.setVisible(true);
//    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource()==this.menuItem_AddPlan){
//            FrmAddPlan dlg=new FrmAddPlan(this,"添加计划",true);
//            dlg.setVisible(true);
//        }
//        else if(e.getSource()==this.menuItem_DeletePlan){
//            if(this.curPlan==null) {
//                JOptionPane.showMessageDialog(null, "请选择计划", "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.planManager.deletePlan(this.curPlan);
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_AddStep){
//            FrmAddStep dlg=new FrmAddStep(this,"添加步骤",true);
//            dlg.plan=curPlan;
//            dlg.setVisible(true);
//        }
//        else if(e.getSource()==this.menuItem_DeleteStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.deleteStep(this.planSteps.get(i));
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_startStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.startStep(this.planSteps.get(i));
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_finishStep){
//            int i=FrmMain.this.dataTableStep.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null, "请选择步骤", "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                PersonPlanUtil.stepManager.finishStep(this.planSteps.get(i));
//            } catch (BaseException e1) {
//                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//        }
//        else if(e.getSource()==this.menuItem_static1){
//
//        }
//        else if(e.getSource()==this.menuItem_modifyPwd){
//            FrmModifyPwd dlg=new FrmModifyPwd(this,"密码修改",true);
//            dlg.setVisible(true);
//        }
//    }
//}
