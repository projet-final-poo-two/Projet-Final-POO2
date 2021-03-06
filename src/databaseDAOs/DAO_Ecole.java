package databaseDAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objet.Ecole;
import objet.Resultat;

public class DAO_Ecole {

	public final static String TABLE_ECOLE = "Ecole";
	public final static String NOM_ECOLE = "nom_ecole";
	public final static String PASSWORD = "Password";

	public ArrayList<Ecole> getAllEcole() {
		// ArrayList<Ecole> ecole = new ArrayList<>();
		try {
			Connection connect = DBConnection.getConnection();
			Statement stm = connect.createStatement();
			ResultSet resultSet = stm.executeQuery("SELECT * FROM "
					+ TABLE_ECOLE);
			return commeListEcole(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return null;
	}

	public static Ecole getOneEcole(String nom_ecole) {
		ArrayList<Ecole> ecole = new ArrayList<>();
		Ecole ec = null;
		try {
			Connection connect = DBConnection.getConnection();
			Statement stm = connect.createStatement();
			ResultSet resultSet = stm.executeQuery("SELECT * FROM "
					+ TABLE_ECOLE + " WHERE " + NOM_ECOLE + "=" + nom_ecole);
			ecole = commeListEcole(resultSet);
			for (int i = 0; i < ecole.size(); i++) {
				if (ecole != null) {
					ec = ecole.get(i);
				}
			}
			return ec;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ec;

	}

	// **************************
	// Ajout Ecole

	public void ajoutEcole(Ecole ecole, String nom_inspection) {
		try {
			Connection connect = DBConnection.getConnection();
			Statement stm = connect.createStatement();
			stm.executeUpdate("INSERT INTO " + TABLE_ECOLE + " VALUES('"
					+ ecole.getNom() + "','" + ecole.getMotDePasse() + "','"
					+ nom_inspection + "')");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static ArrayList<Ecole> commeListEcole(ResultSet resultat) {
		ArrayList<Ecole> myEcole = new ArrayList<>();
		try {
			while (resultat.next()) {
				String nom_ecole = resultat.getString(NOM_ECOLE);
				String password = resultat.getString(PASSWORD);

				Ecole ecole = new Ecole(nom_ecole, password);
				myEcole.add(ecole);
			}
			return myEcole;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void delete(String nom) {
		try {
			Connection connect = DBConnection.getConnection();
			Statement stm = connect.createStatement();

			stm.executeUpdate("DELETE FROM " + TABLE_ECOLE + " WHERE "
					+ NOM_ECOLE + "='" + nom + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modifierEcole(String nomEcoleAmodifier,Ecole newEcole) {
		try {
			Connection connect = (Connection) DBConnection.getConnection();
			Statement stm = connect.createStatement();
			stm.executeUpdate("UPDATE " + TABLE_ECOLE + " SET " + NOM_ECOLE
					+ "='" + newEcole.getNom() + "'," + PASSWORD + "='"
					+ newEcole.getMotDePasse()+"' WHERE "+NOM_ECOLE+"='"+nomEcoleAmodifier+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
