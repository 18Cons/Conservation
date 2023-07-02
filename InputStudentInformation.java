import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
/**
 * 录入学生信息类，负责提供录入学生信息的界面
 */
public class InputStudentInformation extends JPanel implements ActionListener {
	private Student student = null;// 学生对象
	private StudentPicture studentPicture;// 学生图像
	private HashMap<String, Student> informationTable = null;
	private JTextField numberTField, nameTField, gradeTField, birthdayTField;
	private JButton picButton;// 选择照片按钮
	private JLabel promptLabel;//提示信息
	private JComboBox<String> majorComBox; // 专业列表框
	private JRadioButton maleRButton, femaleRButton;// 单选按钮，选择男或者女
	private ButtonGroup buttonGroup = null;
	private JButton inputButton, resetButton;// 输入按钮、重置按钮
	private FileInputStream fileInputStream = null;// 文件输入流对象
	private ObjectInputStream objectInputStream = null;// 对象输入流对象
	private FileOutputStream fileOutputStream = null;// 文件输出流对象
	private ObjectOutputStream objectOutputStream = null;// 对象输出流对象
	private File systemFile, imagePic;
	private JPanel putButtonPanel;//录入和重置按钮的容器
	private JPanel messPanel,picPanel;//基本信息和照片的容器
	/**
	 *构造方法,初始化录入界面
	 */
	public InputStudentInformation(File file) {
		systemFile = file;
		studentPicture = new StudentPicture();		
		informationTable = new HashMap<String,Student>();
		promptLabel = new JLabel("请输入以下信息：",JLabel.LEFT);
		promptLabel.setFont(new Font("宋体",Font.BOLD,13));//设置提示信息的字体
		promptLabel.setForeground(Color.RED);
		promptLabel.setOpaque(true);//设置为不透明
		promptLabel.setBackground(new Color(216,224,231));//设置背景颜色	
		initMessPanel();
		initPutButtonJPanel();
		initPicPanel();
		setLayout(new BorderLayout());
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				messPanel, picPanel);	
		add(promptLabel,BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
		add(putButtonPanel, BorderLayout.SOUTH);
		validate();
	}
	/**
	 * 初始化显示信息界面
	 */
	public void initMessPanel(){
		JLabel numberLabel = new JLabel("学号:", JLabel.CENTER);
		numberTField = new JTextField(5);
		Box numberBox = Box.createHorizontalBox(); // 添加水平box
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		JLabel nameLabel = new JLabel("姓名:", JLabel.CENTER);
		nameTField = new JTextField(5);
		Box nameBox = Box.createHorizontalBox(); // 添加水平box
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel sexLabel = new JLabel("性别:", JLabel.CENTER);
		maleRButton = new JRadioButton("男", true);
		femaleRButton = new JRadioButton("女", false);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(maleRButton);
		buttonGroup.add(femaleRButton);
		Box sexBox = Box.createHorizontalBox(); // 添加水平box
		sexBox.add(sexLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel = new JLabel("专业:", JLabel.CENTER);
		majorComBox = new JComboBox<String>();
		try {
			// 从文件中读入专业名称，加入到组合框中
			FileReader fileReader = new FileReader("专业.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String s = null;
			int i = 0;
			while ((s = bufferedReader.readLine()) != null)
				majorComBox.addItem(s);
			fileReader.close();
			bufferedReader.close();
		} catch (IOException exp) {// 如有异常，将数学和计算机科学与技术加入组合框中
			majorComBox.addItem("数学");
			majorComBox.addItem("计算机科学与技术");
		}
		Box majorBox = Box.createHorizontalBox(); // 添加水平box
		majorBox.add(majorLabel);
		majorBox.add(majorComBox);
		JLabel gradeLabel = new JLabel("年级:", JLabel.CENTER);
		gradeTField = new JTextField(5);
		Box gradeBox = Box.createHorizontalBox(); // 添加水平box
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("出生:", JLabel.CENTER);
		birthdayTField = new JTextField(5);
		Box birthdayBox = Box.createHorizontalBox(); // 添加水平box
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
		boxH.add(numberBox);
		boxH.add(nameBox);
		boxH.add(sexBox);
		boxH.add(majorBox);
		boxH.add(gradeBox);
		boxH.add(birthdayBox);
		boxH.add(Box.createVerticalGlue()); // 添加垂直胶水
		messPanel = new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * 初始化照片部分的界面
	 */
	public void initPicPanel(){
		JLabel picLabel = new JLabel("照片：",JLabel.LEFT);
		picButton = new JButton("选择照片");
		picButton.addActionListener(this);	
		picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel,BorderLayout.NORTH);
		picPanel.add(studentPicture,BorderLayout.CENTER);
		picPanel.add(picButton,BorderLayout.SOUTH);
	}
	/**
	 * 初始化录入、重置按钮界面
	 */
	public void initPutButtonJPanel(){
		inputButton = new JButton("录入");
		resetButton = new JButton("重置");
		inputButton.addActionListener(this); // 添加事件监听对象
		resetButton.addActionListener(this); // 添加事件监听对象
		putButtonPanel = new JPanel();
		putButtonPanel.setBackground(new Color(216,224,231));
		putButtonPanel.add(inputButton);
		putButtonPanel.add(resetButton);
	}
	/**
	 * 当点击录入按钮、重置按钮和选择照片按钮时执行的操作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputButton) {// 如果点击录入按钮
			String number = "";
			number = numberTField.getText();// 读取学号信息
			if (number.length() > 0) {
				try {// 从文件中读取信息
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap<String, Student>) objectInputStream.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) { // 如果该学号存在 ，显示警告信息
					String warning = "该生基本信息已存在,请到修改页面修改!";
					JOptionPane.showMessageDialog(this, warning, "警告",
							JOptionPane.WARNING_MESSAGE);
				} else { // 如果信息不存在，将输入的数据保存
					String m = "确定录入该生信息？";
					int ok = JOptionPane.showConfirmDialog(this, m, "确认",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					if (ok == JOptionPane.YES_OPTION) {
						String name = nameTField.getText();
						String major = (String) majorComBox.getSelectedItem();
						String grade = gradeTField.getText();
						String birth = birthdayTField.getText();
						String sex = null;
						if (maleRButton.isSelected())
							sex = maleRButton.getText();
						else
							sex = femaleRButton.getText();
						student = new Student();
						student.setNumber(number);
						student.setName(name);
						student.setMajor(major);
						student.setGrade(grade);
						student.setBirthday(birth);
						student.setSex(sex);
						student.setImagePic(imagePic);
						try { // 将信息保存在文件中
							fileOutputStream = new FileOutputStream(systemFile);
							objectOutputStream = new ObjectOutputStream(
									fileOutputStream);
							informationTable.put(number, student);
							objectOutputStream.writeObject(informationTable);
							objectOutputStream.close();
							fileOutputStream.close();
							clearMessage();
						} catch (Exception ee) {
						}
					}
				}
			} else {
				String warning = "必须要输入学号!";
				JOptionPane.showMessageDialog(this, warning, "警告",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == picButton) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");
			chooser.setFileFilter(filter);
			int state = chooser.showOpenDialog(null);
			File choiceFile = chooser.getSelectedFile();
			if (choiceFile != null && state == JFileChooser.APPROVE_OPTION) {
				picButton.setText("重新选择");
				imagePic = choiceFile;
				studentPicture.setImage(imagePic);
				studentPicture.repaint();// 显示照片
			}
		} else if (e.getSource() == resetButton) {
			clearMessage();
		}
	}
	/**
	 * 将显示的信息清空
	 */
	public void clearMessage() {
		numberTField.setText(null);
		nameTField.setText(null);
		gradeTField.setText(null);
		birthdayTField.setText(null);
		picButton.setText("选择照片");
		imagePic = null;
		studentPicture.setImage(imagePic);
		studentPicture.repaint();
	}
}
