
import org.testng.annotations.*;

public class tcs {

    @Test
    public void checkSearch(){
        Notes session = new Notes();
        session.browserInit();
        session.enterBeatport();
        session.closeWelcome();
        session.search("guru net");
        session.checkSearchFail();
        session.backHome();
        session.search("Kevin Gates");
        session.checkSearchOk();
        session.selectFromSearch();
        session.quit();
    }

    @Test
    public void checkPlayer(){
        Notes session = new Notes();
        session.browserInit();
        session.enterBeatport();
        session.closeWelcome();
        session.navigateToSong();
        session.checkPlaylist();
        session.clearPlaylist();
        session.quit();
    }
}
