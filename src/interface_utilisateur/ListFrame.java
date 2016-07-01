package interface_utilisateur;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTable;

import tablemodels.EleveModel;

public class ListFrame extends JFrame {
	protected JTable table;

	private JFrame parent;
	private EleveModel eleveModel;

	/**
	 * Create the panel.
	 */
	public ListFrame(JFrame parent) {
		this.parent = parent;
		this.setSize(400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		this.table = new JTable();
		eleveModel = new EleveModel("S");
		table.setModel(eleveModel);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame frame = new ListFrame(new JFrame());
		frame.setVisible(true);
	}

}
