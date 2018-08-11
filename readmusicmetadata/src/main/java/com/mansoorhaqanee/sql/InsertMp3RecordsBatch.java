package com.mansoorhaqanee.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mansoorhaqanee.models.Song;

public class InsertMp3RecordsBatch {
	public static void batchInsertRecordsIntoTable(Connection dbConnection, ArrayList<Song> songList) throws SQLException {
		PreparedStatement preparedStatement = null;
		
		String insertTableSQL = "INSERT INTO SONGS"
				+ "(ARTIST, YEAR, ALBUM, TITLE) VALUES"
				+ "(?,?,?,?)";
		
		preparedStatement = dbConnection.prepareStatement(insertTableSQL);
		//Connection dbConnection = null;
		for(Song mp3: songList){
			
			preparedStatement.setString(1, mp3.getArtist());
			preparedStatement.setString(2, mp3.getYear());
			preparedStatement.setString(3, mp3.getAlbum());
			preparedStatement.setString(4, mp3.getTitle());
			preparedStatement.addBatch();
		}
		int[] updates = preparedStatement.executeBatch();
		System.out.println("Amount of songs inserted to the table as records: " + updates.length);
	}
}
