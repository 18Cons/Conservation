import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
/**
 * 删除学生信息类，负责提供删除学生信息的界面
 */
public class DeleteStudentInformation extends JPanel implements ActionListener {
	private HashMap<String, Student> informationTable = null; // 基本信息表
	private JTextField numberTField, nameTField, majorTField, gradeTField,
			birthdayTField;
	private JRadioButton maleRadioButton, femaleRadioButton;
	private JButton deleteButton;// 删除按钮
	private ButtonGroup buttonGroup = null;
	private FileInputStream fileInputStream = null;
	private ObjectInputStream objectInputStream = null;
	private FileOutputStream fileOutputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	private File systemFile = null;
	private JPanel messPanel;// 显示基本信息的容器
	/**
	 * 构造方法，初始化删除学生信息界面
	 */
	public DeleteStudentInformation(File file) {
		systemFile = file;
		informationTable = new HashMap<String,Student>();
		initMessPanel();
		add(messPanel);
		validate();
	}
	/**
	 * 初始化显示学生信息部分界面
	 */
	public void initMessPanel() {
		JLabel deleteLabel = new JLabel("学号:", JLabel.CENTER);
		numberTField = new JTextField(10);
		deleteButton = new JButton("删除");
		deleteButton.addActionListener(this);
		numberTField.addActionListener(this);
		Box box1 = Box.createHorizontalBox();
		box1.add(deleteLabel);
		box1.add(numberTField);
		box1.add(deleteButton);
		JLabel nameLabel = new JLabel("姓名:", JLabel.CENTER);
		nameTField = new JTextField(10);
		nameTField.setEditable(false);
		Box box2 = Box.createHorizontalBox();
		box2.add(nameLabel);
		box2.add(nameTField);
		JLabel sexLabel = new JLabel("性别:", JLabel.CENTER);
		maleRadioButton = new JRadioButton("男", false);
		femaleRadioButton = new JRadioButton("女", false);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(maleRadioButton);
		buttonGroup.add(femaleRadioButton);
		Box box3 = Box.createHorizontalBox();
		box3.add(sexLabel);
		box3.add(maleRadioButton);
		box3.add(femaleRadioButton);
		JLabel majorLabel = new JLabel("专业:", JLabel.CENTER);
		majorTField = new JTextField(10);
		majorTField.setEditable(false);
		Box box4 = Box.createHorizontalBox();
		box4.add(majorLabel);
		box4.add(majorTField);
		JLabel gradeLabel = new JLabel("年级:", JLabel.CENTER);
		gradeTField = new JTextField(10);
		gradeTField.setEditable(false);
		Box box5 = Box.createHorizontalBox();
		box5.add(gradeLabel);
		box5.add(gradeTField);
		JLabel birthdayLabel = new JLabel("出生:", JLabel.CENTER);
		birthdayTField = new JTextField(10);
		birthdayTField.setEditable(false);
		Box box6 = Box.createHorizontalBox();
		box6.add(birthdayLabel);
		box6.add(birthdayTField);
		Box boxH = Box.createVerticalBox();
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(box5);
		boxH.add(box6);
		boxH.add(Box.createVerticalGlue());
		messPanel = new JPanel();
		messPanel.add(boxH);
	}
	/**
	 * 当点击删除按钮和在学号文本框中回车时执行的操作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteButton || e.getSource() == numberTField) {
			String number = "";
			number = numberTField.getText();
			if (number.length() > 0) {
				try {
					fileInputStream = new FileInputStream(systemFile);
					objectInputStream = new ObjectInputStream(fileInputStream);
					informationTable = (HashMap) objectInputStream.readObject();
					fileInputStream.close();
					objectInputStream.close();
				} catch (Exception ee) {
				}
				if (informationTable.containsKey(number)) {
					Student stu = (Student) informationTable.get(number);
					nameTField.setText(stu.getName());
					majorTField.setText(stu.getMajor());
					gradeTField.setText(stu.getGrade());
					birthdayTField.setText(stu.getBirthday());
					if (stu.getSex().equals("男"))
						maleRadioButton.setSelected(true);
					else
						femaleRadioButton.setSelected(true);
					String m = "确定要删除该学号及全部信息吗?";
					int ok = JOptionPane.showConfirmDialog(this, m, "确认",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					;
					if (ok == JOptionPane.YES_OPTION) {
						informationTable.remove(number);
						try {
							fileOutputStream = new FileOutputStream(systemFile);
							objectOutputStream = new ObjectOutputStream(
									fileOutputStream);
							objectOutputStream.writeObject(informationTable);
							objectOutputStream.close();
							fileOutputStream.close();
							clearMessage();
						} catch (Exception ee) {
						}
					} else if (ok == JOptionPane.NO_OPTION) {
						clearMessage();
					}
				} else {
					String warning = "该学号不存在!";
					JOptionPane.showMessageDialog(this, warning, "警告",
							JOptionPane.WARNING_MESSAGE);
					numberTField.setText(null);
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
		majorTField.setText(null);
		gradeTField.setText(null);
		birthdayTField.setText(null);
	}
}
