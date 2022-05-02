package size;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Random;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class operation {
	JTextField text_1,text_2,text_3,text_4,text_set,text_range,text_num,text_choose,text_max,text_min;
	JTextArea text;
	JButton button_1,button_2,button_3;
	JCheckBox checkBox_1,checkBox_2,checkBox_3,checkBox_4,checkBox_5,checkBox_kh;
	JRadioButton radiobutton_1,radiobutton_2,radiobutton_3,radiobutton_4;
	Random random = new Random();
	String[] xsws = new String[]{"0","0.0","0.00","0.000"};
	int count[]=new int[]{0,0,0,0,0,0,0};
	String[] operator = new String[10];
	int[] num = new int[10];
	String topic[] = new String[1000];
	String topic_[] = new String[1000];
	String max="",min="";
	String path="";
	String question="";
	int n=0,n_=0;
	int s=0,m=1;
	int t=0,N=0;
	int g=0,h=0;
	int ok=0;
	double s_1=0.0;
	boolean order_1=true;
	
//创建用户界面
	public operation(){
		JFrame frame=new JFrame("四则运算练习题");
		frame.setSize(1000,1000);
		frame.setLocation(100, 50);
		frame.getContentPane().setLayout(new GridLayout(2,1));
		
		JPanel pane_1=new JPanel();
		pane_1.setLayout(new GridLayout(1,1)); 
		frame.getContentPane().add(pane_1);
		
		text=new JTextArea();
		text.setEnabled(true);
		text.setLineWrap(true);
		
		JScrollPane scrollpane=new JScrollPane();
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
    	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        pane_1.add(scrollpane);
        scrollpane.setViewportView(text);
		
		JPanel pane_2=new JPanel();
		pane_2.setLayout(new GridLayout(6,1)); 
		frame.getContentPane().add(pane_2);
		
		JPanel pane_2_1=new JPanel();
		pane_2_1.setLayout(new GridLayout(1,2)); 
		pane_2.add(pane_2_1);
		
		text_num=new JTextField("数量");
		text_num.setEditable(false);
		pane_2_1.add(text_num);
		
		text_1=new JTextField();
		text_1.setEditable(true);
		pane_2_1.add(text_1);
		text_1.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {			
				if(e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9){}
				else{
					e.consume();
				}
			}
		});
		
		JPanel pane_2_2=new JPanel();
		pane_2_2.setLayout(new GridLayout(1,4)); 
		pane_2.add(pane_2_2);
		
		text_choose=new JTextField("选择数类型");
		text_choose.setEditable(false);
		pane_2_2.add(text_choose);
		
		ButtonGroup buttongroup1 = new ButtonGroup();         
        radiobutton_3 = new JRadioButton("整数");       
        buttongroup1.add(radiobutton_3);                    
        pane_2_2.add(radiobutton_3); 
        radiobutton_3.setSelected(true);
        
        radiobutton_4 = new JRadioButton("小数");
        buttongroup1.add(radiobutton_4);
        pane_2_2.add(radiobutton_4);
		
		checkBox_5 = new JCheckBox("允许负数情况");
		pane_2_2.add(checkBox_5);
		
		JPanel pane_2_3=new JPanel();
		pane_2_3.setLayout(new GridLayout(1,5)); 
		pane_2.add(pane_2_3);
		
		text_set=new JTextField("设置数范围");
		text_set.setEditable(false);
		pane_2_3.add(text_set);
		
		text_min=new JTextField("最小值");
		text_min.setEditable(false);
		pane_2_3.add(text_min);
		
		text_2=new JTextField();
		text_2.setEditable(true);
		pane_2_3.add(text_2);
		text_2.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {			
				if(e.getKeyChar()>= KeyEvent.VK_0 &&e.getKeyChar()<= KeyEvent.VK_9){}
				else if(checkBox_5.isSelected()&&text_2.getCaretPosition()==0&&e.getKeyChar()=='-'){}
				else if(radiobutton_4.isSelected()&&!text_2.getText().isEmpty()&&!text_2.getText().contains(".")&&e.getKeyChar()=='.'){}
				else{
					e.consume();
				}
			}
		});
		
		text_max=new JTextField("最大值");
		text_max.setEditable(false);
		pane_2_3.add(text_max);
		
		text_3=new JTextField();
		text_3.setEditable(true);
		pane_2_3.add(text_3);
		text_3.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {			
				if(e.getKeyChar()>= KeyEvent.VK_0 &&e.getKeyChar()<= KeyEvent.VK_9){}
				else if(checkBox_5.isSelected()&&text_3.getCaretPosition()==0&&e.getKeyChar()=='-'){}
				else if(radiobutton_4.isSelected()&&!text_3.getText().isEmpty()&&!text_3.getText().contains(".")&&e.getKeyChar()=='.'){}
				else{
					e.consume();
				}
			}
		});
		
		JPanel pane_2_4=new JPanel();
		pane_2_4.setLayout(new GridLayout(1,6)); 
		pane_2.add(pane_2_4);
		
		text_choose=new JTextField("选择运算符");
		text_choose.setEditable(false);
		pane_2_4.add(text_choose);
		
		checkBox_1 = new JCheckBox("＋");
		pane_2_4.add(checkBox_1);
		
		checkBox_2 = new JCheckBox("－");
		pane_2_4.add(checkBox_2);
		
		checkBox_3 = new JCheckBox("×");
		pane_2_4.add(checkBox_3);
		
		checkBox_4 = new JCheckBox("÷");
		pane_2_4.add(checkBox_4);
		
		checkBox_kh = new JCheckBox("括号");
		pane_2_4.add(checkBox_kh);
		
		JPanel pane_2_5=new JPanel();
		pane_2_5.setLayout(new GridLayout(1,3)); 
		pane_2.add(pane_2_5);
		
		ButtonGroup buttongroup = new ButtonGroup();         
        radiobutton_1 = new JRadioButton("输出到文件");       
        buttongroup.add(radiobutton_1);                    
        pane_2_5.add(radiobutton_1); 
        radiobutton_1.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		text_4.setEditable(true);
        		
        	}
        	
        });
        
        text_4=new JTextField("输入文件地址");
		text_4.setEditable(false);
		pane_2_5.add(text_4);
		text_4.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {if(radiobutton_1.isSelected()&&e.getClickCount()==1){text_4.setText("");}}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
				});
        
        radiobutton_2 = new JRadioButton("输出到打印机");
        buttongroup.add(radiobutton_2);
        pane_2_5.add(radiobutton_2);
        radiobutton_2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		text_4.setText("输入文件地址");
        		text_4.setEditable(false);
        	}
        });
		
		JPanel pane_2_6=new JPanel();
		pane_2_6.setLayout(new GridLayout(1,3)); 
		pane_2.add(pane_2_6);
		
		button_1=new JButton("生成");
		button_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				int option=0;
				text.setText("");
				rest();
				if(text_1.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "请输入运算题数量", "提示", JOptionPane.INFORMATION_MESSAGE);
				    option++;
				}
				if(text_2.getText().isEmpty()||text_3.getText().equals("")){
					JOptionPane.showMessageDialog(null, "请输入数最大值或最小值", "提示", JOptionPane.INFORMATION_MESSAGE);
					option++;
		        }
				else{
					if(Double.parseDouble(text_2.getText())>Double.parseDouble(text_3.getText())){
						JOptionPane.showMessageDialog(null, "请输入数的正确范围", "提示", JOptionPane.INFORMATION_MESSAGE);
					    option++;
					    }
					}
				if(!checkBox_1.isSelected()&&!checkBox_2.isSelected()&&!checkBox_3.isSelected()&&!checkBox_4.isSelected()){
					JOptionPane.showMessageDialog(null, "请选择运算符", "提示", JOptionPane.INFORMATION_MESSAGE);
					option++;
				}
				if(option==0){
					creat_operator();
					if(m==1)
						JOptionPane.showMessageDialog(null, "该情况无法生成练习题", "提示", JOptionPane.INFORMATION_MESSAGE);
					else{N=Integer.parseInt(text_1.getText());build();}
				}
			}
		});
		pane_2_6.add(button_1);
		
		button_2=new JButton("清除");
		button_2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				text.setText("");
				rest();
			}
		});
		pane_2_6.add(button_2);
		
		button_3=new JButton("输出");
		button_3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!radiobutton_1.isSelected()&&!radiobutton_2.isSelected())
					JOptionPane.showMessageDialog(null, "请选择输出方式", "提示", JOptionPane.INFORMATION_MESSAGE);
				else if(text.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "请先生成运算题", "提示", JOptionPane.INFORMATION_MESSAGE);
				else if(text_4.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "文件地址为空", "提示", JOptionPane.INFORMATION_MESSAGE);
				else{
				try {
					output_choose();
				} catch (IOException e1) {
					if(radiobutton_1.isSelected())
						JOptionPane.showMessageDialog(null, "请输入正确文件地址", "提示", JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}}
			         
		   }
		});
		pane_2_6.add(button_3);
		
		frame.setVisible(true);
		
	}
	//输出选择
	void output_choose() throws IOException{
		if(radiobutton_1.isSelected()){
			path=text_4.getText();
			writeTxtFile(text.getText(),path);
		}
		else if(radiobutton_2.isSelected()){
		String name=URLDecoder.decode(operation.class.getProtectionDomain().getCodeSource().getLocation().getFile(),"UTF-8")+"/四则运算练习题.docx";
		creatTxtFile(name);
		writeTxtFile(text.getText(),name);
		printer(name);
	}
		
}
	//创建docx文件
	public void creatTxtFile(String filenameTemp) throws IOException {		
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
		}
		else{
			filename.delete();
			filename.createNewFile();
		}
	}
	//输出到打印机
	void printer(String name){
		
		             File file = new File(name); //获取选择的文件  
		             //构建打印请求属性集  
		             HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();  
		             //设置打印格式，因为未确定类型，所以选择autosense  
		             DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;  
		             //查找所有的可用的打印服务  
		             PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);  
		             //定位默认的打印服务  
		             PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();  
		             //显示打印对话框  
		             PrintService service = ServiceUI.printDialog(null, 200, 200, printService,defaultService, flavor, pras);  
		             if(service != null){  
		                 try {  
	                     DocPrintJob job = service.createPrintJob(); //创建打印作业  
		                     FileInputStream fis = new FileInputStream(file); //构造待打印的文件流  
		                     DocAttributeSet das = new HashDocAttributeSet();  
		                     Doc doc = new SimpleDoc(fis, flavor, das);  
		                     job.print(doc, pras);  
		                 } catch (Exception e) {  
		                     e.printStackTrace();  
		                 }  
		             }
	}
	//输出到文件
	public void writeTxtFile(String newStr,String filenameTemp) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		String filein = newStr + "\r\n";
		String temp = "";
 
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
 
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
 
			// 保存该文件原有的内容
			for (int j = 1; (temp = br.readLine()) != null; j++) {
				buf = buf.append(temp);
				// System.getProperty("line.separator")
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}
			buf.append(filein);
 
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
		} catch (IOException e1) {
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}
	//重置数值
	void rest(){
		 g=0;h=0;
		 t=0;
		 n=0;
		 order_1=true;
		 N=0;
		 m=1;
		 question="";
		 max=text_3.getText();
		 min=text_2.getText();
	}
	//创建符号数组
	void creat_operator(){
		m=1;
		if(checkBox_1.isSelected()){operator[m]=checkBox_1.getText();m++;}
		if(checkBox_2.isSelected()){operator[m]=checkBox_2.getText();m++;}
		if(checkBox_3.isSelected()){operator[m]=checkBox_3.getText();m++;}
		if(checkBox_4.isSelected()){
			if(Double.parseDouble(text_2.getText())==0&&Double.parseDouble(text_3.getText())==0){}
			else{operator[m]=checkBox_4.getText();m++;}
			}
		if(checkBox_kh.isSelected()){operator[m]="(";operator[m+1]=")";m+=2;}
	}
	//判断是否是数字
	boolean judge_sz(int j){
		if(!judge_jsfh(j)&&!judge_kh1(j)&&!judge_kh2(j))
			return true;
		return false;
	}
	//判断是否是计算符号
	boolean judge_jsfh(int j){
		if(judge_jiajian(j)||judge_chengchu(j))
			return true;
		else 
			return false;
	}
	//判断是否是加减符号
	boolean judge_jiajian(int j){
		if(topic[j].equals("＋")||topic[j].equals("－"))
			return true;
		else 
			return false;
	}
	//判断是否是乘除符号
	boolean judge_chengchu(int j){
		if(topic[j].equals("×")||topic[j].equals("÷"))
			return true;
		else 
			return false;
	}
	//判断是否是左括号
	boolean judge_kh1(int j){
		if(topic[j].equals("("))
			return true;
		else 
			return false;
	}
	//判断是否是右括号
	boolean judge_kh2(int j){
		if(topic[j].equals(")"))
			return true;
		else 
			return false;
	}
	//移动练习题数组
	void move(int j){
		if(j==1){
		n-=2;
		if(radiobutton_4.isSelected())
			topic[g-1]=String.valueOf(s_1);
		else
			topic[g-1]=Integer.toString(s);
		for(int i=g;i<n;i++){
			topic[i]=topic[i+2];
		}}
		else if(j==2){
			for(int i=g;i<n-1;i++){
				topic[i]=topic[i+1];
			}
			n--;
		}
	}
	//加减计算
	boolean jiajian(){
		if(judge_sz(g))
		     g++;
		if(topic[g].equals("＋")){
			num[h]=g;
			h++;
			g++;
			if(g!=n-1&&kh()){}
			else if(g!=n-1&&chengchu()){}
				h--;
				g=num[h];
				if(radiobutton_4.isSelected())
					s_1=Double.parseDouble(topic[g-1])+Double.parseDouble(topic[g+1]);
				else
					s=Integer.parseInt(topic[g-1])+Integer.parseInt(topic[g+1]);
				move(1);
				return true;
		}
		else if(topic[g].equals("－")){
			num[h]=g;
			h++;
			g++;
			if(g!=n-1&&kh()){}
			else if(g!=n-1&&chengchu()){}
				h--;
				g=num[h];
				if(radiobutton_4.isSelected())
					s_1=Double.parseDouble(topic[g-1])-Double.parseDouble(topic[g+1]);
				else
					s=Integer.parseInt(topic[g-1])-Integer.parseInt(topic[g+1]);
			    move(1);
			    if(!radiobutton_4.isSelected()&&!checkBox_5.isSelected()&&s<0)
			    	order_1=false;
			    else if(radiobutton_4.isSelected()&&!checkBox_5.isSelected()&&s_1<0)
			    	order_1=false;
			    return true;
		}
		return false;
		
	}
	//乘除计算
	boolean chengchu(){
		if(judge_sz(g))
		     g++;
		if(topic[g].equals("×")){
			num[h]=g;
			h++;
			g++;
			if(g!=n-1&&kh()){}
				h--;
				g=num[h];
				if(radiobutton_4.isSelected())
					s_1=Double.parseDouble(topic[g-1])*Double.parseDouble(topic[g+1]);
				else
					s=Integer.parseInt(topic[g-1])*Integer.parseInt(topic[g+1]);
			    move(1);
			    if(g!=n-1){
			    	if(judge_chengchu(g+1)&&chengchu())
			    		return true;
			    }
			    return true;
			
		}
		else if(topic[g].equals("÷")){
			num[h]=g;
			h++;
			g++;
			if(g!=n-1&&kh()){}
				h--;
				g=num[h];
				if(!radiobutton_4.isSelected()&&Integer.parseInt(topic[g+1])==0||radiobutton_4.isSelected()&&Double.parseDouble(topic[g+1])==0)
					order_1=false;
				else{
					if(radiobutton_4.isSelected())
						s_1=Double.parseDouble(topic[g-1])/Double.parseDouble(topic[g+1]);
					else
						s=Integer.parseInt(topic[g-1])/Integer.parseInt(topic[g+1]);
				    move(1);
				    if(g!=n-1){
				    	if(judge_chengchu(g+1)&&chengchu())
				    		return true;
				    }
				    return true;
			}
		}
		return false;
	}
	//括号类型计算
	boolean kh(){
		if(judge_sz(g))
		     g++;
		if(g!=n-1&&topic[g].equals("(")){
			move(2);
			while(order_1){
				if(judge_sz(g))
				     g++;
			    if(topic[g].equals("("))
					kh();
				else if(judge_chengchu(g))
					chengchu();
				else if(judge_jiajian(g))
						jiajian();
			    else if(topic[g].equals(")"))
				    {
			    	move(2);
			    	return true;
			    	}
			}
		}
		return false;
	}
	//计算练习题结果
	void answer(){
		g=0;h=0;
		while(n!=1){
			if(judge_sz(g))
			     g++;
		    if(topic[g].equals("("))
				kh();
			else if(judge_chengchu(g))
				chengchu();
			else if(judge_jiajian(g))
					jiajian();
			if(!order_1)
				break;
			if(g==n-1)
				g=0;
		}
	}
	//保存练习题
	void keep(){
		n_=n;
		for(int i=0;i<n_;i++){
			topic_[i]=topic[i];
		}
	}
	//判断计算是否正确
	boolean saomiao_js(){
		order_1=true;
		keep();
		answer();
		if(!order_1)
			return false;
		else
			return true;
	}
	//判断左括号是否正确
	boolean kh_rule1(int j){
		for(h=j;h<n;h++){
			if(judge_kh1(h)){
				if(!kh_rule1(h+1))
					return false;
			}
			else if(judge_kh2(h)){
				if(ok==1){
				num[s]=h;
				s+=1;
				}
				return true;
			}
		}
		return false;	
	}
	//判断右括号是否正确
	boolean kh_rule2(int j){
		for(h=j;h>=0;h--){
			if(judge_kh2(h)){
				if(!kh_rule2(h-1))
					return false;
			}
			else if(judge_kh1(h))
				return true;
		}
		return false;	
	}
	//删除重复括号
	boolean delete_dykh_1(){
		int x,y,z;
		ok=1;
			for(int i=0;i<n;i++){
				 if(i!=n-1&&judge_kh1(i)&&judge_kh1(i+1)){
					 x=0;y=0;z=0;
					 s=0;
					 kh_rule1(i+1);
					 x=s;
					 z=num[s-1];
					 s=0;
					 kh_rule1(i+2);
					 y=s;
					 if(x-y==1&&judge_kh2(z-1)){
						 System.out.println();
						g=z;move(2);count[m-1]--;
						 g=i;move(2);count[m-2]--; 
						 i--;
						 }
					 } 
				  }
			ok=0;
			return true;
	}
	//删除首尾多余括号
	boolean delete_dykh_2(){
		if(judge_kh1(0)&&judge_kh2(n-1)){
			s=0;ok=1;
			if(kh_rule1(1)&&num[s-1]==n-1){
				g=0;move(2);count[m-2]--;
				g=n-1;move(2);count[m-1]--;
				}
			ok=0;
		}
			return true;
	}
	//删除括号
	boolean delete_kh(){
		if(checkBox_kh.isSelected()){
		   if(delete_dykh_1()&&delete_dykh_2()){}
		   if(count[m-1]==0&&count[m-2]==0)
			return false;
		}
		return true;
	}
	//统计计算符号或括号数量
	void symbol_num(){
		for(int i=1;i<m;i++){
			count[i]=0;
		}
		for(int i=1;i<m;i++){
			for(int j=0;j<n;j++){
			if(topic[j].equals(operator[i]))
				count[i]++;
		}}
	}
	//判断计算符号或括号数量是否正确
	boolean saomiao_symbol(){
		symbol_num();
		for(int i=1;i<m;i++){
			if(count[i]==0)
				return false;
		}
		if(checkBox_kh.isSelected()&&count[m-1]!=count[m-2])
			return false;
		return true;
	}
	//练习题创造规则
	boolean creat_rule(int j){
		if(judge_sz(j)&&j!=0){
			if(judge_kh2(j-1)||judge_sz(j-1))
				return false;
		}
		else if(judge_jsfh(j)){
			if(j==0)
				return false;
			else{
			if(judge_jsfh(j-1)||judge_kh1(j-1))
				return false;
			}
		}
		else if(judge_kh1(j)&&j!=0){
			if(judge_sz(j-1)||judge_kh2(j-1))
					return false;
			
		}
		else if(judge_kh2(j)){
			if(j==0)
				return false;
			else{
			if(judge_kh1(j-1)||judge_jsfh(j-1)||!kh_rule2(j-1)||judge_kh1(j-2))
					return false;	
			}
		}
		
	return true;
	
}
	//创造随机数
	void creat_random(){
		if(radiobutton_3.isSelected()){
			operator[0]=Integer.toString(random.nextInt(Integer.parseInt(max)-Integer.parseInt(min)+1)+Integer.parseInt(min));
		}
		else if(radiobutton_4.isSelected()){
			double c=random.nextDouble()*(Double.parseDouble(max)-Double.parseDouble(min))+Double.parseDouble(min);
			DecimalFormat df = new DecimalFormat(xsws[random.nextInt(4)]);
			operator[0]=df.format(c);
		}
	}
	//创造练习题
	void creat(){
		  g=0;
		  while(true){ 
			  if(checkBox_kh.isSelected())
					  n=random.nextInt(11)+5;
			  else
				  n=random.nextInt(8)+(m-1)*2+1;
			 if(n%2==0)
				 n++;
			while(g<n){
				creat_random();
				topic[g]=operator[random.nextInt(m)];
				if(creat_rule(g))
					g++;
				}
			if(saomiao_symbol()){
				if(delete_kh()&&saomiao_js())
					break;
				else
					g=0;	
			}
			else
				g=0;	
			}
	}
	//按规定生成练习题
	void build(){
		while(t<N){
		creat();
		question=question.concat(Integer.toString(t+1)).concat(".     ");
		for(int j=0;j<n_;j++){
			question=question.concat(topic_[j]);
			}
		question=question.concat("=\n");
		text.setText(text.getText()+question);
		order_1=true;g=0;question="";
		t++;
		}
	}
	//主程序
	public static void main(String[] args) {
	    
		new operation();
	}
}
