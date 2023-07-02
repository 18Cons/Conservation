import javax.swing.*;
import java.io.*;
import java.awt.*;
/**
 * ��ʾѧ����Ƭ
 */
public class StudentPicture extends JPanel {
	private File imageFile;// ���ͼ���ļ�������
	private Toolkit tool;// ���𴴽�Image����
	/**
	 * ���췽������ʼ������
	 */
	public StudentPicture() {
		tool = getToolkit();
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	/**
	 * ����imageFile����
	 */
	public void setImage(File imageFile) {
		this.imageFile = imageFile;
		repaint();
	}
	/**
	 * ��ʾ��Ƭ
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getBounds().width;
		int h = getBounds().height;
		if (imageFile != null) {
			Image image = tool.getImage(imageFile.getAbsolutePath()); // ���ͼ��
			g.drawImage(image, 0, 0, w, h, this);// ����ͼ��
		} else
			g.drawString("û��ѡ����Ƭͼ��!", 20, 30);
	}
}
