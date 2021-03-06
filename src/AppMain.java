import java.sql.*;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Scanner;

public class AppMain {

	public static Connection connect;

  JFrame frame = new JFrame("CardLayout");
  JPanel panelCont = new JPanel();
  CardLayout cl = new CardLayout();

  JPanel Home = new Swing_Home(frame, panelCont, cl);
  JPanel profLogin = new Swing_ProfessorLogin(frame, panelCont, cl);
  JPanel studentLogin = new Swing_StudentLogin(frame, panelCont, cl);
  JPanel newProf = new Swing_NewProfessor(frame, panelCont, cl);
  JPanel vendorLogin = new Swing_VendorLogin(frame, panelCont, cl);
  JPanel newVendor = new Swing_NewVendor(frame, panelCont, cl);

	public AppMain() {
		panelCont.setLayout(cl);


		panelCont.add(profLogin, "ProfessorLogin");
		panelCont.add(studentLogin, "StudentLogin");
		panelCont.add(newProf, "NewProfessor");
		panelCont.add(vendorLogin, "VendorLogin"); //index 7
		panelCont.add(newVendor, "NewVendor"); //index 8

		panelCont.add(Home, "Home");

		cl.show(panelCont, "Home");


		frame.add(panelCont);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					connect.close();
				} catch (SQLException ex) {
					Database.printSQLException(ex);
				}
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// String jdbc = "jdbc:mysql://localhost:3306/project?user=nyee&useSSL=true";

			/*
			 * NOTE:
			 *
			 * HEY LOOK HERE FIRST BEFORE YOU CHANGE THE LINE ABOVE
			 *
			 * To stop having to change the db line above set an environment variable called
			 * APPMAINDB with the jdbc.
			 *
			 * Do this in the terminal:
			 * export APPMAINDB="YOUR DB"
			 *
			 * ex/ export APPMAINDB="jdbc:mysql://localhost:3306/project?user=nyee&useSSL=true"
			 *
			 * If this does not work ignore this and just set the jdbc variable like before and
			 * let me know
			 *
			 * Nathan ;)
			 *
			 */

			String jdbc = System.getenv("APPMAINDB");

			if (jdbc == null) {
			  System.out.print("URL to db: ");
			  Scanner scanner = new Scanner(System.in);
			  String db_url = scanner.nextLine();
			  jdbc = db_url;
			}

			connect = DriverManager.getConnection(jdbc);
			connect.setAutoCommit(false);

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new AppMain();
				}
			});

		} catch (ClassNotFoundException e) {
			System.err.println("Could not load JDBC driver");
			System.err.println("Exception: " + e);
			e.printStackTrace();
		} catch (SQLException e) {
			Database.printSQLException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
