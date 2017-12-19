package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class FrmMain extends JFrame implements ActionListener {
	private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_Manager=new JMenu("信息管理");
    private JMenu menu_search=new JMenu("信息统计");
    private JMenuItem  menuItem_UserManager=new JMenuItem("用户管理");
	private JMenuItem  menuItem_ProductManager=new JMenuItem("商品管理");
    private JMenuItem  menuItem_OrderManager=new JMenuItem("订单管理");

    private JMenuItem  menuItem_Money=new JMenuItem("收支管理");
    private JMenuItem  menuItem_Info=new JMenuItem("用户信息");
    

	private JPanel statusBar = new JPanel();
	public FrmMain(){
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("卖家事务管理");
		menu_Manager.add(menuItem_UserManager);
		menuItem_UserManager.addActionListener(this);
		menu_Manager.add(menuItem_OrderManager);
		menuItem_OrderManager.addActionListener(this);
		menu_Manager.add(menuItem_ProductManager);
		menuItem_ProductManager.addActionListener(this);
		menubar.add(menu_Manager);

		menu_search.add(this.menuItem_Money);
		menuItem_Money.addActionListener(this);
		menu_search.add(this.menuItem_Info);
		menuItem_Info.addActionListener(this);
	    menubar.add(this.menu_search);
	    this.setJMenuBar(menubar);
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));

	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuItem_UserManager){
//			FrmUserManager dlg=new FrmUserManager(this,"用户管理",true);
//			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ProductManager){
			FrmProductManager dlg=new FrmProductManager(this,"商品管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_OrderManager){
//			FrmReaderManager dlg=new FrmReaderManager(this,"订单管理",true);
//			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Money){
//			FrmPublisherManager dlg=new FrmPublisherManager(this,"收支管理",true);
//			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Info){
//			FrmBookManager dlg=new FrmBookManager(this,"用户信息",true);
//			dlg.setVisible(true);
		}
	}
}
