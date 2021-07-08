package Projekti;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import com.github.sarxos.webcam.Webcam;

//import SP_projekti2.KlientiStart;

//import com.github.sarxos.webcam.Webcam;

//import SP_projekti2.frameKlienti.CaptureThread;
//import SP_projekti2.frameKlienti.PlayThread;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JScrollBar;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import javax.swing.border.LineBorder;


public class Klienti extends JFrame {
	
	private JComboBox<String> cmbViti = new JComboBox<>();
	private JComboBox<String> cmbYear = new JComboBox<>();
	private JComboBox<String> cmbAfatet = new JComboBox<>();
	private JPanel contentPane;
	private JTable tblResult;
	private static Connection connection= null;
	private static DbFunc
	dbfunc = new DbFunc();
	private JTextField txtFillimi;
	private static JTextField txtChat;
	private static JTextArea textArea;
	private static JTextArea txtMsgArea;
	private  JLabel lblCam;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
	
	static DataInputStream din;// deklarimi i stream per input te dhenat
	static DataOutputStream dout;// ----per output e dhenat
	static ObjectOutputStream outA;
	static ObjectInputStream oinA;
	static ServerSocket ss_data = null;
	static Socket s_data = null;
	static ServerSocket ss = null;
	static Socket s = null;
	static BufferedOutputStream out = null;
	static BufferedInputStream in = null;
	static BufferedReader br = null;
	static DataInputStream din_chat;
	static DataOutputStream dout_chat;
	static AudioFormat audioFormat;
	static TargetDataLine targetDataLine;
	static SourceDataLine sourceDataLine;
	static ByteArrayOutputStream byteArrayOutputStream;
	

	/**e
	 * Launch the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Klienti frame = new Klienti();
					frame.setExtendedState(frame.MAXIMIZED_BOTH);					
					frame.setVisible(true); 
					frame.setResizable(false);
					
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
			
		});	
		
		
		try {			
			// lidhja me server per kerkim te afateve
			s_data = new Socket("localhost", 4443);	
			oinA = new ObjectInputStream(s_data.getInputStream());
			System.out.println("Lidhja me klientin u realizua");
			din = new DataInputStream(s_data.getInputStream());
			dout = new DataOutputStream(s_data.getOutputStream());
			//lidhja me server per chat
			s = new Socket("localhost", 4444);			
			din_chat=new DataInputStream(s.getInputStream());//din per chat
			dout_chat=new DataOutputStream(s.getOutputStream());//dout per chat
			String msgin="";
			while(!msgin.equals("exit"))
			{
				msgin=din_chat.readUTF();
				System.out.println("Nklienti texti i prnauar:"+msgin);
				txtMsgArea.setText(txtMsgArea.getText().trim()+"\nServeri: \t"+msgin); 
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public void shfaqProvimet()
	{		
		try {
		String afati =  String.valueOf(cmbAfatet.getSelectedItem()); // merre afatin nga comboboxi cmbAfatet psht JANAR
		int viti = Integer.parseInt(String.valueOf(cmbYear.getSelectedItem())); //merre vitin PSH. 2019
		int lendaViti =Integer.parseInt(String.valueOf(cmbViti.getSelectedItem())); // merre vitin e lendeve pshe viti i 1
		List<LendaEntity> lendet_list = new ArrayList<LendaEntity>();
		LocalDate fillimi_afatit = null;
		String [] teDhenat;

		dout.writeUTF(afati + ":" + viti); // dergo tek serveri emrin dhe vitin e afatit qe po e kerkon klienti psh "Janar:2019"
		String msgin =din.readUTF(); //merr mesazhin nga serveri psht "false:false" ose "true:2019-06-11"
	
		teDhenat = msgin.split(":");
        
		if(teDhenat[0].equals("true")) // nese afati egziston beje thirrjen e dyt tek server qe ti kthej te gjitha lendet e vitit te caktuar psh 1
		{			
			fillimi_afatit =LocalDate.parse(teDhenat[1]); //merre daten e fillimit te afatit nga pergjigjja e serverit true:2019-06-11
			dout.writeUTF(String.valueOf(lendaViti)); // degojm tek serveri lendet e cilit viti deshirojm ti shohim psh 1,2,3
		 lendet_list = (List<LendaEntity>) oinA.readObject();//lexo lendet qe i ka kthyer serveri 
			
			if(lendet_list.size()==0) { //nese lista eshte e zbrazet shfaq mesazhin 
				JOptionPane.showMessageDialog(null, "Lista eshte e zbrazet!");		
			}
			txtFillimi.setText(fillimi_afatit.toString()); ///tregon kur fillon afati
			UpdateTable(lendet_list,fillimi_afatit); // mbush tabelen me te dhena nese lista eshte e zbrazet tabela zbrazet
		}
		else {
		//nese eshte false pergjigja nga serveri trego qe afati nuk egziston ne databaze
			JOptionPane.showMessageDialog(null, "Afati " + afati + "-"+viti +" nuk egziston!");
			UpdateTable(lendet_list,LocalDate.now()); //pastro tabelen 
			txtFillimi.setText(""); //fshi textboxin
		}	
			
		}
		
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);

		}
	}
	
	
	public void UpdateTable(List<LendaEntity> lendet_list,LocalDate data) //data = kur fillon afati perkatese
	{
		DefaultTableModel dm = new DefaultTableModel(0, 0);
		 //lenda, java, dita, viti
		    String header[] = new String[] { "Lenda", "Viti", "Java","Dita","Data","Ora"};
		    dm.setColumnIdentifiers(header);
		    tblResult.setModel(dm);

		    for (LendaEntity ae : lendet_list) {
		    	int days = (ae.getJava()-1) * 7 + ae.getDita() -1; // formual qe llogarit daten e provimit duke u bazuar ne daten e fillimit te afatit dhe javes dhe dite se provimet
		    	ae.setData_Provimit(data.plusDays(days));	//	 data e fillimit te afati plus ditet e formules    
		        dm.addRow(new Object[] {ae.getLenda(),ae.getViti(),ae.getJava(),dbfunc.getEmriDites(ae.getDita()),ae.getData_Provimit(),ae.getOra()});
		    }	
	}
	/**
	 * Create the frame.
	 */
	public Klienti() {
		setTitle("Klienti");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1524, 902);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAfati = new JLabel("Afati");
		lblAfati.setForeground(new Color(0, 102, 51));
		lblAfati.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		lblAfati.setBounds(37, 400, 46, 14);
		contentPane.add(lblAfati);
		
		JLabel lblViti = new JLabel("Viti");
		lblViti.setForeground(new Color(0, 102, 51));
		lblViti.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		lblViti.setBounds(37, 439, 46, 14);
		contentPane.add(lblViti);
		cmbViti.setForeground(new Color(0, 102, 51));
		cmbViti.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		
	
		cmbViti.setModel(new DefaultComboBoxModel<>(new String[] {"1", "2", "3"}));
		cmbViti.setBounds(79, 436, 74, 20);
		contentPane.add(cmbViti);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 467, 662, 246);
		contentPane.add(scrollPane_1);
		
		tblResult = new JTable();
		scrollPane_1.setViewportView(tblResult);
		tblResult.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Emri i Lendes", "Viti", "Java", "Dita", "Ora", "Data"
			}
		));
		tblResult.setForeground(new Color(0, 102, 51));
		tblResult.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		tblResult.setBackground(SystemColor.menu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(423, 307, 2, 2);
		contentPane.add(scrollPane);
		
		JButton btnKerko = new JButton("Kerko");
		btnKerko.setForeground(new Color(0, 102, 51));
		btnKerko.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnKerko.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread t_Kerko = new Thread(new Runnable() {
					@Override 
					public void run() {
						shfaqProvimet();
					}
		});t_Kerko.start();}});
		btnKerko.setBounds(590, 434, 89, 23);
		contentPane.add(btnKerko);
		cmbAfatet.setForeground(new Color(0, 102, 51));
		cmbAfatet.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		
		cmbAfatet.setModel(new DefaultComboBoxModel<>(new String[] {"Janar", "Qershor", "Shtator"}));
		cmbAfatet.setBounds(79, 397, 163, 20);
		contentPane.add(cmbAfatet);
		
		JLabel label_1 = new JLabel("Viti");
		label_1.setForeground(new Color(0, 102, 51));
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		label_1.setBounds(264, 400, 46, 14);
		contentPane.add(label_1);
		cmbYear.setForeground(new Color(0, 102, 51));
		cmbYear.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		
		cmbYear.setModel(new DefaultComboBoxModel<>(new String[] {"2017", "2018", "2019"}));
		cmbYear.setBounds(364, 397, 74, 20);
		contentPane.add(cmbYear);
		
		JLabel lblAfatiFillonMe = new JLabel("Afati fillon me:");
		lblAfatiFillonMe.setForeground(new Color(0, 102, 51));
		lblAfatiFillonMe.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		lblAfatiFillonMe.setBounds(264, 439, 108, 14);
		contentPane.add(lblAfatiFillonMe);
		
		txtFillimi = new JTextField();
		txtFillimi.setEditable(false);
		txtFillimi.setBounds(364, 437, 132, 20);
		contentPane.add(txtFillimi);
		txtFillimi.setColumns(10);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(139, 39, 1, 1);
		contentPane.add(desktopPane);
		
		JScrollPane scrollPaneArea = new JScrollPane();
		scrollPaneArea.setBounds(980, 191, 471, 344);
		contentPane.add(scrollPaneArea);
		
		JButton btnSubmit = new JButton("");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try 
				{
					String msgout="";
					msgout=txtChat.getText().trim();
					dout_chat.writeUTF(msgout);
					txtMsgArea.setText(txtMsgArea.getText().trim()+"\n You :\t"+ msgout); 
					txtChat.setText("");
				} 
				catch (Exception e3) 
				{
					
				}
			
			}
		});
		btnSubmit.setIcon(new ImageIcon(Klienti.class.getResource("/resources/sendIcon1.png")));
		btnSubmit.setForeground(new Color(0, 102, 51));
		btnSubmit.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		btnSubmit.setBounds(1362, 707, 89, 41);
		contentPane.add(btnSubmit);
		
		
		JLabel label = new JLabel("");
        Image label1 = new ImageIcon(this.getClass().getResource("/resources/up.gif")).getImage();
		label.setIcon(new ImageIcon(label1));
        label.setBounds(79, 39, 171, 153);
        contentPane.add(label);
        
		JLabel label_2 = new JLabel("Orari i Provimeve per Fakultetin e Inxhinierise Elektrike dhe Kompjuterike");
		label_2.setForeground(new Color(0, 102, 102));
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 25));
		label_2.setBounds(310, 39, 1037, 63);
		contentPane.add(label_2);
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setBounds(79, 39, 185, 147);
		contentPane.add(lblFoto);
		
		txtChat = new JTextField();
		txtChat.setText("");
		txtChat.setBounds(986, 707, 366, 41);
		contentPane.add(txtChat);
		txtChat.setColumns(10);
		
		
		txtMsgArea = new JTextArea();
		txtMsgArea.setBounds(988, 191, 463, 201);
		contentPane.add(txtMsgArea);
		txtMsgArea.setEditable(false);
		txtMsgArea.setForeground(new Color(0, 102, 51));
		txtMsgArea.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 12));
		txtMsgArea.setBackground(SystemColor.menu);
		
		JLabel label_3 = new JLabel("Chat");
		label_3.setForeground(new Color(0, 102, 102));
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 16));
		label_3.setBounds(1196, 141, 46, 14);
		contentPane.add(label_3);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBackground(Color.WHITE);
		panel.setBounds(958, 111, 516, 667);
		contentPane.add(panel);
		
	        
}



}
