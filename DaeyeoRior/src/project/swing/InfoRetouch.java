package project.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import project.db.DB;

/**
 * 회원가입 정보를 SQL과 연동
 * 아이디 값 외에 다른 정보만 수정가능
 */

public class InfoRetouch extends JFrame implements ActionListener, MouseListener {
	private JLabel idTf;
	private JTextField nameTf, phoneTf;
	private JPasswordField pwTf;
	private JButton okBtn, cancleBtn;
	private Font rollLabFont, rollTfFont, balBtnFont;
	private Color colorMint, colorDownMint;

	public InfoRetouch(String setTitle, int width, int height) {
		
		JPanel panN = new JPanel();
		JPanel panS = new JPanel();
		
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		//컨텐트페인에 내부공백 설정
		
		colorMint = new Color(133, 197, 196);
		colorDownMint = new Color(109, 180, 176);
		
		((JComponent) getContentPane()).setBackground(Color.WHITE);
		panN.setBackground(Color.WHITE);
		panS.setBackground(Color.WHITE);
		
		rollLabFont = new Font("a뉴굴림3", Font.PLAIN, 15);
		rollTfFont = new Font("a뉴굴림2", Font.PLAIN, 15);
		balBtnFont = new Font("a발레리노", Font.PLAIN, 16);
		
		panN.setLayout(new GridLayout(5, 2));
		
		JLabel nameL = new JLabel("이름");
		JLabel idL = new JLabel("아이디");
		JLabel pwL = new JLabel("비밀번호");
		JLabel phoneL = new JLabel("전화번호");
		
		nameL.setFont(rollLabFont);
		idL.setFont(rollLabFont);
		pwL.setFont(rollLabFont);
		phoneL.setFont(rollLabFont);
		
		nameTf = new JTextField(10);
		idTf = new JLabel(Login.idTf.getText());
		//사용자 아이디 값 수정불가
		pwTf = new JPasswordField(10);
		phoneTf = new JTextField(10);
		
		nameTf.setFont(rollTfFont);
		idTf.setFont(rollTfFont);
		pwTf.setFont(rollTfFont);
		phoneTf.setFont(rollTfFont);
		
		nameTf.setToolTipText("이름은 영문 또는 한글 제한, 10자이하");
		idTf.setToolTipText("아이디는 수정불가");
		pwTf.setToolTipText("비밀번호는 영문 숫자 조합, 8자이상 12자이하");
		phoneTf.setToolTipText("전화번호는 숫자로 제한, 12자이하");

		okBtn = new JButton("정보수정");
		okBtn.setForeground(colorMint);
		okBtn.setFont(balBtnFont);
		okBtn.setBorderPainted(false);
		//버튼 테두리 제거
		okBtn.setContentAreaFilled(false);
		//버튼 채우기 제거
		okBtn.setFocusPainted(false);
		//버튼 선택시 생기는 테두리 제거
		
		okBtn.addActionListener(this);
		okBtn.addMouseListener(this);
		
		cancleBtn = new JButton("취소");
		cancleBtn.setForeground(colorMint);
		cancleBtn.setFont(balBtnFont);
		cancleBtn.setBorderPainted(false);
		//버튼 테두리 제거
		cancleBtn.setContentAreaFilled(false);
		//버튼 채우기 제거
		cancleBtn.setFocusPainted(false);
		//버튼 선택시 생기는 테두리 제거
		
		cancleBtn.addActionListener(this);
		cancleBtn.addMouseListener(this);
		
		panN.add(nameL);
		panN.add(nameTf);
		panN.add(idL);
		panN.add(idTf);
		panN.add(pwL);
		panN.add(pwTf);
		panN.add(phoneL);
		panN.add(phoneTf);
		panS.add(okBtn);
		panS.add(cancleBtn);
		
		add(panN, BorderLayout.NORTH);
		add(panS, BorderLayout.SOUTH);
		
		setTitle(setTitle);
		//제목 설정
		setSize(width, height);
		//크기 설정
		setResizable(false);
		//임의로 크기조절 불가
		setLocationRelativeTo(this);
		//중앙 배치
		setVisible(true);
		//창 띄우기
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == okBtn) {
			int result = JOptionPane.showConfirmDialog(null, "입력하신 정보로 정보수정 하시겠습니까?", "회원가입 여부",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			//다이얼로그 컨폼창
			if(result == JOptionPane.OK_OPTION) {
				validation();
			}else if(result == JOptionPane.CANCEL_OPTION) {
				dispose();
				//현재 창만 정상종료
			}
		} else if(obj == cancleBtn) {
			dispose();
			//현재 창만 정상종료
		}
	}

	private void validation() {
		if(chkEngAndKor(nameTf.getText()) == true && nameTf.getText().length() > 0 && nameTf.getText().length() <= 10) {
			//입력된 값이 있고 입력된 값이 10자이하면
				if(chkEngAndNum(pwTf.getText()) == true && pwTf.getText().length() >= 8 && pwTf.getText().length() <= 12) {
					//입력된 값이 8자이상 12이하면
					if(chkOnlyNum(phoneTf.getText()) == true && phoneTf.getText().length() > 0 && phoneTf.getText().length() <= 12) {
						//입력된 값이 있고 입력된 값이 12자이하면
						//DB 데이터 넣어줌
							DB.update("update information set name = '" + nameTf.getText() + "', pw = '" + pwTf.getText()
									+ "', phone = '" + phoneTf.getText() + "'" + "where id = '" + Login.idTf.getText() + "'");
							//모든 if문을 통과했다면
							JOptionPane.showMessageDialog(null, "정보수정이 완료되었습니다.", "정보수정 완료", 
									JOptionPane.INFORMATION_MESSAGE);
							//정보수정 완료 정보창 띄움
							dispose();
					} else if(chkOnlyNum(phoneTf.getText()) == false || phoneTf.getText().length() < 0 || phoneTf.getText().length() > 12) {
						JOptionPane.showMessageDialog(null, "전화번호는 숫자로 제한, 12자이하", "다시시도 하세요!", 
								JOptionPane.INFORMATION_MESSAGE);
						//전화번호 정보가 올바르지 않으면 정보창을 띄움
						}
				} else if(chkEngAndNum(pwTf.getText()) == false || pwTf.getText().length() < 8 || pwTf.getText().length() > 12) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문 숫자 조합, 8자이상 12자이하", "다시시도 하세요!", 
							JOptionPane.INFORMATION_MESSAGE);
					//비밀번호 정보가 올바르지 않으면 정보창을 띄움
					}
		} else if(chkEngAndKor(nameTf.getText()) == false || nameTf.getText().length() < 0 || nameTf.getText().length() > 10) {
			JOptionPane.showMessageDialog(null, "이름은 영문 또는 한글로 제한, 10자이하", "다시시도 하세요!", 
					JOptionPane.INFORMATION_MESSAGE);
			//이름 정보가 올바르지 않으면 정보창을 띄움
		}
	}
	
	public boolean chkOnlyNum(String input) {
		//숫자 검사
		return Pattern.matches("^[0-9]*$", input);
	}
	
	public boolean chkOnlyEng(String input) {
		//영문자 검사
		return Pattern.matches("^[a-zA-Z]*$", input);
	}
	
	public boolean chkEngAndNum(String input) {
		//영문자 또는 숫자 검사
		return Pattern.matches("^[0-9a-zA-Z]*$", input);
	}
	
	public boolean chkEngAndKor(String input) {
		//영문자 또는 한글 검사
		return Pattern.matches("^[a-zA-Z가-힣]*$", input);
	}
	

	@Override
	public void mouseEntered(MouseEvent e) {
		//마우스가 해당 컴포넌트 영역 안으로 들어오면
		if(okBtn == e.getComponent()) {
			okBtn.setForeground(colorDownMint);
		} else if(cancleBtn == e.getComponent()) {
			cancleBtn.setForeground(colorDownMint);
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		//마우스가 해당 컴포넌트 영역 밖으로 나가면
		if(okBtn == e.getComponent()) {
			okBtn.setForeground(colorMint);
		} else if(cancleBtn == e.getComponent()) {
			cancleBtn.setForeground(colorMint);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

}
