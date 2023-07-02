import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
/**
 * 启动系统，完成主界面的初始化
 */
public class InformationWindow extends JFrame implements ActionListener {
	private InputStudentInformation inputStudentInformation; // 录入信息
	private ModifyStudentInformation modifyStudentInformation; // 修改信息
	private QueryStudentInformation queryStudentInformation; // 查询信息
	private DeleteStudentInformation deleteStudentInformation; // 删除信息
	private JMenuBar bar;// 菜单栏
	private JMenu fileMenu;
	private JMenuItem inputItem, modifyItem, queryItem, deleteItem,
			welcomeItem;// 各菜单项
	private HashMap<String, Student> informationTable = null; // 学生信息表
	private File file = null;
	private CardLayout card = null;
	private JLabel label = null;
	private JPanel pCenter;
	/**
	 * 构造方法，初始化主界面
	 */
	public InformationWindow() {
		informationTable = new HashMap<String,Student>();
		initFrame();
		setVisible(true);
		setBounds(100, 50, 380, 350);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showConfirmDialog(null, "确认退出吗?", "确认对话框",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setResizable(false);
		validate();
	}
	/**
	 * 初始化主界面的各个组件
	 */
	public void initFrame() {
		inputItem = new JMenuItem("录入");
		modifyItem = new JMenuItem("修改");
		queryItem = new JMenuItem("查询");
		deleteItem = new JMenuItem("删除");
		welcomeItem = new JMenuItem("欢迎界面");
		bar = new JMenuBar();
		fileMenu = new JMenu("菜单选项");
		fileMenu.add(inputItem);
		fileMenu.add(modifyItem);
		fileMenu.add(queryItem);
		fileMenu.add(deleteItem);
		fileMenu.add(welcomeItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
		label = new JLabel("学籍管理系统", JLabel.CENTER);
		label.setIcon(new ImageIcon("welcome.jpg"));
		label.setFont(new Font("隶书", Font.BOLD, 36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.red);
		informationTable = new HashMap<String, Student>();
		inputItem.addActionListener(this);
		modifyItem.addActionListener(this);
		queryItem.addActionListener(this);
		deleteItem.addActionListener(this);
		welcomeItem.addActionListener(this);
		card = new CardLayout();
		pCenter = new JPanel();
		pCenter.setLayout(card);
		file = new File("基本信息.txt");
		if (!file.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(file);
				ObjectOutputStream objectOut = new ObjectOutputStream(out);
				objectOut.writeObject(informationTable);
				objectOut.close();
				out.close();
			} catch (IOException e) {
			}
		}
		inputStudentInformation = new InputStudentInformation(file);
		modifyStudentInformation = new ModifyStudentInformation(file);
		queryStudentInformation = new QueryStudentInformation(file);
		deleteStudentInformation = new DeleteStudentInformation(file);
		pCenter.add("欢迎界面", label);
		pCenter.add("录入界面", inputStudentInformation);
		pCenter.add("删除界面", deleteStudentInformation);
		pCenter.add("查询界面", queryStudentInformation);
		pCenter.add("修改界面", modifyStudentInformation);
		add(pCenter, BorderLayout.CENTER);
	}
	/**
	 * 当点击录入、修改、查询、删除、欢迎菜单项时执行的操作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputItem) {
			inputStudentInformation.clearMessage();
			card.show(pCenter, "录入界面");
		} else if (e.getSource() == modifyItem) {
			modifyStudentInformation.clearMessage();
			card.show(pCenter, "修改界面");
		} else if (e.getSource() == queryItem) {
			queryStudentInformation.clearMessage();
			card.show(pCenter, "查询界面");
		} else if (e.getSource() == deleteItem)
			card.show(pCenter, "删除界面");
		else if (e.getSource() == welcomeItem)
			card.show(pCenter, "欢迎界面");
	}
	/**
	 * 启动系统
	 */
	public static void main(String args[]) {
		new InformationWindow();
	}
}
