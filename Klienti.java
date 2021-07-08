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
		

