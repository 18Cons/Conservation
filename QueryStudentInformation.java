import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.*;
/**
 * 查询学生信息类，负责提供查询学生信息的界面
 */
public class QueryStudentInformation extends JPanel implements ActionListener {
	private Student student = null;// 学生对象
	private StudentPicture studentPicture;// 学生图像
	private HashMap<String, Student> informationTable = null;
	private JTextField numberTField, nameTField, sexTField, majorTField,
			gradeTField, birthdayTField;
	private JButton queryButton;// 查询按钮
	private FileInputStream fileInputStream = null;// 文件输入流对象
	private ObjectInputStream objectInputStream = null;// 对象输入流对象
	private File systemFile, imagePic;
	private JPanel messPanel = null;// 显示基本信息的容器
	/**
	 * 构造方法，初始化查询学生信息界面
	 */
	public QueryStudentInformation(File file) {
		systemFile = file;
		studentPicture = new StudentPicture();
		informationTable = new HashMap<String,Student>();
		JLabel numberLabel = new JLabel("输入要查询的学生学号:", JLabel.CENTER);
		numberTField = new JTextField(10);
		queryButton = new JButton("查询");
		queryButton.addActionListener(this);
		numberTField.addActionListener(this);
		Box numberBox = Box.createHorizontalBox(); // 添加水平box
		numberBox.add(numberLabel);
		numberBox.add(numberTField);
		numberBox.add(queryButton);
		initMessPanel();
		JLabel picLabel = new JLabel("照片： ", JLabel.LEFT);
		JPanel picPanel = new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel, BorderLayout.NORTH);
		picPanel.add(studentPicture, BorderLayout.CENTER);
		JSplitPane splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				messPanel, picPanel);
		setLayout(new BorderLayout());
		add(numberBox, BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
		validate();
	}
	/**
	 * 初始化显示学生信息部分界面
	 */
	public void initMessPanel() {
		JLabel nameLabel = new JLabel("姓名:", JLabel.CENTER);
		nameTField = new JTextField(10);
		nameTField.setEditable(false);
		Box nameBox = Box.createHorizontalBox(); // 添加水平box
		nameBox.add(nameLabel);
		nameBox.add(nameTField);
		JLabel sexLabel = new JLabel("性别:", JLabel.CENTER);
		sexTField = new JTextField(10);
		sexTField.setEditable(false);
		Box sexBox = Box.createHorizontalBox(); // 添加水平box
		sexBox.add(sexLabel);
		sexBox.add(sexTField);
		JLabel majorLabel = new JLabel("专业:", JLabel.CENTER);
		majorTField = new JTextField(10);
		majorTField.setEditable(false);
		Box majorBox = Box.createHorizontalBox(); // 添加水平box
		majorBox.add(majorLabel);
		majorBox.add(majorTField);
		JLabel gradeLabel = new JLabel("年级:", JLabel.CENTER);
		gradeTField = new JTextField(10);
		gradeTField.setEditable(false);
		Box gradeBox = Box.createHorizontalBox(); // 添加水平box
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeTField);
		JLabel birthdayLabel = new JLabel("出生:", JLabel.CENTER);
		birthdayTField = new JTextField(10);
		birthdayTField.setEditable(false);
		Box birthdayBox = Box.createHorizontalBox(); // 添加水平box
		birthdayBox.add(birthdayLabel);
		birthdayBox.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
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
	 * 当点击查询按钮和在学号文本框中回车时执行的操作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == queryButton || e.getSource() == numberTField) {
			String number = "";
			number = numberTField.getText();
			if (number.length() > 0) {
				try {
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap<String, Student>) objectInputStream
							.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) {
					student = informationTable.get(number);
					nameTField.setText(student.getName());
					majorTField.setText(student.getMajor());
					gradeTField.setText(student.getGrade());
					birthdayTField.setText(student.getBirthday());
					sexTField.setText(student.getSex());
					studentPicture.setImage(student.getImagePic());
					studentPicture.repaint();
				} else {
					String warning = "该学号不存在!";
					JOptionPane.showMessageDialog(this, warning, "警告",
							JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
			} else {
				String warning = "必须要输入学号!";
				JOptionPane.showMessageDialog(this, warning, "警告",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	/**
	 * 将显示的信息清空
	 */
	public void clearMessage() {
		numberTField.setText(null);
		nameTField.setText(null);
		gradeTField.setText(null);
		sexTField.setText(null);
		birthdayTField.setText(null);
		majorTField.setText(null);
		studentPicture.setImage(null);
		studentPicture.repaint();
	}
}
