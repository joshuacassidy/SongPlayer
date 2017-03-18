import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.Song.Player.Song;
import com.Song.Player.SongList;


public class PlayerMenu {

	private SongList mSongList;
	private BufferedReader mReader;
	private Queue<Song> mSongQueue;
	
	private Map<String,String> nMenu;

	public PlayerMenu(SongList songlist){
		mSongList = songlist;
		mReader = new BufferedReader (new InputStreamReader(System.in));
		mSongQueue = new ArrayDeque<Song>();
		nMenu = new HashMap<String,String>();
		nMenu.put("Add","Add a new song to the list of songs to choose from.");
		nMenu.put("Play", "Play the next song in queue.");
		nMenu.put("PlayAll","Play all songs in queue.");
		nMenu.put("Choose","Add a song to the queue.");
		nMenu.put("Quit", " Close the program.");
	}
	
	private String promptAction() throws IOException {
		System.out.printf("There are %d songs available and %d in the queue. Your options are: %n", 
				mSongList.getSongCount(),mSongQueue.size());
		for(Map.Entry<String, String> option : nMenu.entrySet()){
			System.out.printf("%s - %s %n", option.getKey(), option.getValue());
		}
		System.out.print("What do you want to do:  ");
		String choice = mReader.readLine();
		return choice.trim().toLowerCase();
	}
	public void run(){
		String choice = "";
		do { 
		try{
			choice = promptAction();
			switch(choice){
			case "add":
				Song song = promptNewSong();
				mSongList.addSong(song);
				System.out.printf("%s added to songs to choose from! %n%n", song);
				
				
				break;
			
			case "choose":
				String artist = promptArtist();
				Song artistSong = promptSongForArtist(artist);
				mSongQueue.add(artistSong);
				System.out.printf("%n%n");
				System.out.printf("You chose: %s %n", artistSong);
				System.out.printf("%n%n");
				break;

				
			case "play":
				playNext();
		
				System.out.println("");
			
				break;
				
			case "playall":
				playAll();
				System.out.println("");
				break;
				
			case "quit":
				
				break;
				default:
					System.out.printf("Unknown choice: '%s'. Try again. %n%n%n", choice);
			
			}
		} catch(IOException ioe){
				System.out.println("Invalid input");
				ioe.printStackTrace();
			}

		}
		while( !choice.equals("quit"));
		
	}
	
	private Song promptNewSong() throws IOException{
		System.out.print("Enter the artists's name:  ");
		String artist = mReader.readLine();
		System.out.print("Enter the title:  ");
		String title = mReader.readLine();
		System.out.print("Enter the video URL: ");
		String videoUrl = mReader.readLine();
		return new Song(artist,title,videoUrl);
	}
	
	private String promptArtist() throws IOException{
		System.out.println("Available artists: ");
		List<String> artists =new ArrayList<>(mSongList.getArtists());
		int index = promptForIndex(artists);
		return artists.get(index);
	}

	private Song promptSongForArtist(String artist) throws IOException{
		List<Song> songs = mSongList.getSongsForArtist(artist);
		List<String> songTitles = new ArrayList<>();
		for(Song song : songs){
			songTitles.add(song.getTitle());
		}
		System.out.printf("Available songs for %s: %n", artist);
		int index = promptForIndex(songTitles);
		return songs.get(index);
	}
	
	private int promptForIndex(List<String> options) throws IOException{
		int counter = 1;
		for(String option : options){
			System.out.printf("%d.) %s %n", counter, option); 
			counter++;
		}
		System.out.printf("%n%n");
		System.out.print("Your choice: ");
		String optionAsString = mReader.readLine();
		System.out.printf("%n%n");
		int choice = Integer.parseInt(optionAsString.trim());

		return choice - 1;
	}
	
	public void playNext(){
		Song song = mSongQueue.poll();
		if(song == null){
			System.out.println("There are no songs in the queue. Use the menu to add a new song.");
		}
		else{
			String Url="open "+ song.getVideoUrl();
			try {
				Process child =Runtime.getRuntime().exec(Url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	
	}

		public void playAll() throws IOException{
			for(Song i : mSongQueue){
			Song song = mSongQueue.poll();
			if(song == null){
				System.out.println("There are no songs in the queue. Use the menu to add a new song.");
			}
			else{
				String Url="open "+ song.getVideoUrl();
				try {
					Process child =Runtime.getRuntime().exec(Url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		
			
			}
	}
	
}
