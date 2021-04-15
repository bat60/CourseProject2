import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.channels.Channels;

public class Window extends JFrame {

	private JPanel contentPane;
	private static Storage storage;
	private static GoogleCredentials credentials;
	private static final String projectID = "my-first-project-308422";
	private static final String bucketName = "bkt_first_bucket_123";
	private static final String jsonFilePath = "/usr/src/myapp/json/my-first-project-308422-d5003d73c7f6.json";
	
	private static DefaultTableModel model = new DefaultTableModel();
	
	String fileNames = "";
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					authExplicit(
							jsonFilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void authExplicit(String jsonPath) throws IOException {
		// You can specify a credential file by providing a path to GoogleCredentials.
		// Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS
		// environment variable.
		credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

		System.out.println("Buckets:");
		Page<Bucket> buckets = storage.list();
		for (Bucket bucket : buckets.iterateAll()) {
			System.out.println(bucket.toString());
		}
		
	    Page<Blob> blobs = storage.list(bucketName);

//	    for (Blob blob : blobs.iterateAll()) {
//	      System.out.println(blob.getName());
//	    }

	}

	private void constructInvertedIndices() {
		displayTable();
		mergeFiles(); 
	}
	
	private void displayTable() {
		JTableSearchTest table = new JTableSearchTest();
		table.setVisible(true);
	}
	
	public static class JTableSearchTest extends JFrame {
		   private JTextField jtf;
		   private JLabel searchLbl;
		   private JTable table;
		   private TableRowSorter sorter;
		   private JScrollPane jsp;
		   public JTableSearchTest() {
		      setTitle("Inverted Indices and Search");
		      jtf = new JTextField(15);
		      searchLbl = new JLabel("Search");
		      String[] columnNames = { "Word", "Folder", "Document", "Frequency" };  
		      Object[][] rowData = {};
		      model = new DefaultTableModel(rowData, columnNames);
		      sorter = new TableRowSorter<>(model);
		      table = new JTable(model);
		      table.setRowSorter(sorter);
		      setLayout(new FlowLayout(FlowLayout.CENTER));
		      jsp = new JScrollPane(table);
		      add(searchLbl);
		      add(jtf);
		      add(jsp);
		      jtf.getDocument().addDocumentListener(new DocumentListener() {
		         @Override
		         public void insertUpdate(DocumentEvent e) {
		            search(jtf.getText());
		         }
		         @Override
		         public void removeUpdate(DocumentEvent e) {
		            search(jtf.getText());
		         }
		         @Override
		         public void changedUpdate(DocumentEvent e) {
		            search(jtf.getText());
		         }
		         public void search(String str) {
		            if (str.length() == 0) {
		               sorter.setRowFilter(null);
		            } else {
		               sorter.setRowFilter(RowFilter.regexFilter("^"+str+"$", 0));
		            }
		         }
		      });
		      setSize(475, 300);
//		      setDefaultCloseOperation(EXIT_ON_CLOSE);
		      setLocationRelativeTo(null);
		      setResizable(true);
		      setVisible(false);
		   }
	}
	

	private void mergeFiles() {
		// TODO Auto-generated method stub
		String objectName = "";
		if(fileNames.contains("Tolstoy") && fileNames.contains("shakespeare") && fileNames.contains("Hugo")) {
			objectName = "AllFilesOutput";
		}
		else if(fileNames.contains("Tolstoy")){
		    objectName = "TolstoyFilesOutput";
		}
		else if(fileNames.contains("Hugo")){
		    objectName = "HugoFilesOutput";
		}
        else if(fileNames.contains("shakespeare")){
            objectName = "ShakespeareFilesOutput";
        }
        else{
            
        }

        
		for(int i=0; i<7; i++) {
			try {
				downloadObject(projectID, bucketName, objectName + "/part-r-0000" + i, objectName + Double.toString(Math.random()) + ".txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void top_n_search(Integer n) {
		
	}

	static void downloadObject(String projectId, String bucketName, String objectName, String destFilePath) throws IOException {
		// The ID of your GCP project
		// String projectId = "your-project-id";

		// The ID of your GCS bucket
		// String bucketName = "your-unique-bucket-name";

		// The ID of your GCS object
		// String objectName = "your-object-name";

		// The path to which the file should be downloaded
		// String destFilePath = "/local/path/to/file.txt";
		
		
		credentials = GoogleCredentials.fromStream(new FileInputStream(jsonFilePath))
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();		

			FileWriter writer = new FileWriter(new File(destFilePath), true);
			Blob blob = storage.get(BlobId.of(bucketName, objectName));
			
			ReadChannel readChannel = blob.reader();
			
			BufferedReader br = new BufferedReader(Channels.newReader(readChannel, "UTF-8"));
			BufferedWriter bw = new BufferedWriter(writer);
			String line; 
			while((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
				
				String[] tokens = line.split("\\s+");
				String word = tokens[0];
				String details = tokens[1];
				
				String[] detailsToken = details.split(":");
				String folder = detailsToken[0];
				String docName = detailsToken[1];
				String count = detailsToken[2];
				
				model.addRow(new Object[] {word, folder, docName, count});
				
//				System.out.println(word + details);
				
				line = br.readLine();
				
			}
			bw.flush();
			bw.close();
			System.out
					.println("Downloaded object " + objectName + " from bucket name " + bucketName + " to " + destFilePath);
			}
	

	/**
	 * Create the frame.
	 */
	public Window() {
		final JTextArea log = new JTextArea();
		setTitle("Search Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 535, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setForeground(Color.WHITE);
		contentPane.add(panel, BorderLayout.WEST);

		JTextArea txtrLoadMyEngine = new JTextArea();
		txtrLoadMyEngine.setBackground(Color.PINK);
		txtrLoadMyEngine.setForeground(Color.BLACK);
		txtrLoadMyEngine.setText("Load My Engine");
		txtrLoadMyEngine.setEditable(false);

		
		
		final JButton btnNewButton = new JButton("Choose Files");
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				int returnVal = fc.showOpenDialog(btnNewButton);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File[] files = fc.getSelectedFiles();
					for (File file : files) {
						fileNames += file.getName() + "\n";
					}
					log.append(fileNames);
				}
			}
		});

		JButton btnNewButton_1 = new JButton("Construct Inverted Indices");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileNames.isEmpty()) {
					System.out.println("please select a file");
				}
				else {
					constructInvertedIndices();
				}
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(Color.GRAY);

		log.setEditable(false);
		log.setBackground(Color.PINK);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(212).addComponent(txtrLoadMyEngine,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addGap(196).addComponent(btnNewButton)).addGroup(
								gl_panel.createSequentialGroup().addGap(157)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(log, GroupLayout.PREFERRED_SIZE, 256,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btnNewButton_1))))
						.addContainerGap(112, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(61)
						.addComponent(txtrLoadMyEngine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(33).addComponent(btnNewButton).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(log, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton_1).addContainerGap()));
		panel.setLayout(gl_panel);
	}
	
}
