package databaseDAOs;


import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import objet.Ecole;
import objet.Eleve;
import objet.Resultat;
import utilitaire.Outil;

public class DAO_Eleve {
	public final static String TABLE_ELEVE = "Eleve";
	public final static String COLONNE_NUM_TABLE = "numeroTable";
	public final static String COLONNE_PRENOM = "Prenom";
	public final static String COLONNE_NOM = "Nom";
	public final static String COLONNE_DATE_NAISSANCE = "dateNaissance";
	public final static String COLONNE_LIEU_NAISSANCE = "lieu_Naissance";
	public final static String COLONNE_SEXE = "sexe";
	public final static String COLONNE_EMAIL = "email";
	public final static String COLONNE_NOM_ECOLE = "nom_ecole" ;
	

	Connection connect = (Connection) DBConnection.getConnection();

	// **************************
	// liste de tous les eleves 
	
	public ArrayList<Eleve> getAllEleves() throws ParseException {
		ArrayList<Eleve> eleve = new ArrayList<>();
		try {
			Statement stm = connect.createStatement();
			ResultSet resultSet = stm.executeQuery("SELECT * FROM " + TABLE_ELEVE);
			eleve = commeListeEleve(resultSet);
			return eleve ;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eleve ;

	}

	// **************************
	// ajouter un eleve
	
	public void ajoutEleve(int num_table, String nom, String prenom, String lieu_naissance, Calendar date_naissance,
			char sexe, String email) {
		try {
			Statement stm = connect.createStatement();
			stm.executeUpdate("INSERT INTO " + TABLE_ELEVE + " VALUES(" + num_table + ",'" + prenom + "','" + nom + "','"
					+ Outil.calendarToString(date_naissance) + "','" + lieu_naissance + "','" + sexe + "','" + email + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// **************************
	//supprimer un eleve
	
	public void deleteEleve(int num_table) {
		try {
			Statement stm = connect.createStatement();
			int row = stm.executeUpdate("DELETE FROM " + TABLE_ELEVE + " WHERE " + COLONNE_NUM_TABLE + "=" + num_table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// **************************
	// information sur un eleve
	
	public Eleve infoEleve(int num_table) throws ParseException{
		ArrayList<Eleve> myEcole = new ArrayList<>() ;
		Eleve el = null ;

		try{
			Statement stm = connect.createStatement() ;
			ResultSet resultSet = stm.executeQuery("SELECT FROM "+TABLE_ELEVE+" WHERE "+COLONNE_NUM_TABLE+"="+num_table) ;
			myEcole = commeListeEleve(resultSet);
			for(int i=0;i<myEcole.size();i++){
				if(myEcole!=null){
					el = myEcole.get(i) ;
				}
			}
			return el ;
		}catch(SQLException e){
			e.printStackTrace();
			//TODO
		}
		return el ;
	}

	public ArrayList<Eleve> commeListeEleve(ResultSet resultSet) throws ParseException {
		ArrayList<Eleve> myEcole = new ArrayList<>() ;
		try {
			while (resultSet.next()) {
				int numeroTable = resultSet.getInt(COLONNE_NUM_TABLE);
				String prenom = resultSet.getString(COLONNE_PRENOM);
				String nom = resultSet.getString(COLONNE_NOM);
				String date_Naissance = resultSet.getString(COLONNE_DATE_NAISSANCE);
				String lieu_Naissance = resultSet.getString(COLONNE_LIEU_NAISSANCE);
				String sexe = resultSet.getString(COLONNE_SEXE);
				String email = resultSet.getString(COLONNE_EMAIL);
				Resultat resultat = DAO_Resultat.getOneResultat(numeroTable) ;
				Ecole ecole = DAO_Ecole.getOneEcole(COLONNE_NOM_ECOLE) ;
				String nom_ecole = ecole.getNom() ;
				Calendar dateNaissance = Outil.stringToCalendar(date_Naissance) ;
				
				Eleve eleve = new Eleve(numeroTable,nom, prenom,lieu_Naissance, dateNaissance, email, resultat, nom_ecole);
				char a = sexe.charAt(0);
				eleve.setSex(a);
				myEcole.add(eleve) ;
			}
			return myEcole ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myEcole ;
	}

}