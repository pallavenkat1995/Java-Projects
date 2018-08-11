package com.mansoorhaqanee.readmusicmetadata;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.mansoorhaqanee.models.Song;
import com.mansoorhaqanee.sql.InsertMp3RecordsBatch;
import com.mansoorhaqanee.util.CreateDBConnection;
import com.mansoorhaqanee.util.StartHTTPServer;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	//must read in directory as argument requiring 1 arg
    	//reading a directory-->requires file API to turn a string to a path
    	//IO FILE API or PATHS API
    	//from NIO.PATHS API
    	Connection conn = null;
    	try{
    		//argument validation
	    		if (args.length != 1){
	    			throw new IllegalArgumentException("Please enter only one argument");
	    		}
    		
    			String directoryPath = args[0];
    			Path directoryPathObj = Paths.get(directoryPath);
    			if (Files.exists(directoryPathObj)){//path exists validation
			        //do stuff with that Path
    				//read in all mp3 or another extension in
    				//glob method
    				//Files class methods with glob param/globbing--> a syntax that accepts filtering files based on a "glob" patern
    				//glob syntax like "*.{java, class, jar}" or "*.{mp3}" or "*.{pdf}"
    				DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPathObj, "*.{mp3}");
    				//for each loop --for eahc path inside of the stream of all files in the directory
    				List<Song> songs = new ArrayList<Song>();
    				for(Path eachFile:stream){
    					System.out.println(eachFile.getFileName());
    					//metadata needs to be extracted from the files/mp3's
    					//custom library needed?
    					//maven:mp3 mp3agic
    					Mp3File mp3file = new Mp3File(eachFile);
    					ID3v1 songID3V1 = mp3file.getId3v2Tag();
    					System.out.println("length of " + eachFile.getFileName()+ " is " + mp3file.getLengthInSeconds());
    					//create a list of songs
    					Song song = new Song(songID3V1.getArtist(), songID3V1.getYear(), songID3V1.getAlbum(), songID3V1.getTitle());
    					//java 8 stream method?--> do not use Java 8
    						
    					//add song to the songs list.
    					songs.add(song);
    				}
    					//File directory = new File("./");
    					//System.out.println(directory.getAbsolutePath());
    				conn = CreateDBConnection.createConnection("jdbc:h2:~/mydatabase;AUTO_SERVER=TRUE;INIT=runscript from './sqlqueries/create.sql'");
    				System.out.println("printing connection obj" + conn);
    				InsertMp3RecordsBatch.batchInsertRecordsIntoTable(conn,(ArrayList<Song>) songs);
    				StartHTTPServer.startServer();
    				
    			}else{
    				//throw illegal argument exception if path does not exist.
    				throw new IllegalArgumentException("The path specified does not exist. Please enter a new one.");
    			}
    		
    	}catch(Exception e){
    		throw e;
    	}finally{
    		//do nothing
    		CreateDBConnection.closeConnection(conn);
    		
    	}
        
    }
}
