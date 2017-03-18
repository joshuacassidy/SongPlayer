import com.Song.Player.Song;
import com.Song.Player.SongList;


public class Player {

	public static void main(String[] args){
		SongList songlist = new SongList();
		songlist.importFrom("songs.txt");
		PlayerMenu machine = new PlayerMenu(songlist);
		machine.run();
		songlist.exportTo("songs.txt");

	}

}
