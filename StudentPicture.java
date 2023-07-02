import javax.swing.*;
import java.io.*;
import java.awt.*;
/**
 * 显示学生照片
 */
public class StudentPicture extends JPanel {
	private File imageFile;// 存放图像文件的引用
	private Toolkit tool;// 负责创建Image对象
	/**
	 * 构造方法，初始化对象
	 */
	public StudentPicture() {
		tool = getToolkit();
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	/**
	 * 设置imageFile对象
	 */
	public void setImage(File imageFile) {
		this.imageFile = imageFile;
		repaint();
	}
	/**
	 * 显示照片
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getBounds().width;
		int h = getBounds().height;
		if (imageFile != null) {
			Image image = tool.getImage(imageFile.getAbsolutePath()); // 获得图像
			g.drawImage(image, 0, 0, w, h, this);// 绘制图像
		} else
			g.drawString("没有选择照片图像!", 20, 30);
	}
}
