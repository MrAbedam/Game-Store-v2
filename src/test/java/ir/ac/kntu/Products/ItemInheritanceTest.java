package ir.ac.kntu.Products;

import ir.ac.kntu.AdminPages.Admin;
import ir.ac.kntu.AdminPages.AdminGameList;
import ir.ac.kntu.UserPages.User;
import junit.framework.TestCase;

import java.util.concurrent.TimeUnit;

import static ir.ac.kntu.AdminPages.AdminGameList.listOfGames;
import static ir.ac.kntu.AdminPages.AdminGameList.outOfOrderGames;

public class ItemInheritanceTest extends TestCase {

    public void testItemInheritance() {
        Admin mainAdmin = new Admin("user","pass");
        mainAdmin.addMainRole();
        Game game =new Game("R6","dups","fps-shooter",59.99,1,false,mainAdmin);
        Monitor monitor = new Monitor("monitor","4k quality",150,2,10,10,10,10);
        Controller controller = new Controller("dualshock","good",10,1,"Ps4",false);
        assertEquals(game instanceof Item,true);
        assertEquals(monitor instanceof Item && monitor instanceof Device,true);
        assertEquals(controller instanceof Item && controller instanceof Device,true);
    }

    public void testOutOfOrder(){
        Admin mainAdmin = new Admin("user","pass");
        mainAdmin.addMainRole();
        Game game =new Game("R6","dups","fps-shooter",59.99,1,false,mainAdmin);
        game.flipIsOutOfOrder();
        assertEquals(listOfGames.contains(game),false);
        assertEquals(outOfOrderGames.contains(game),true);
    }

    public void testLevelUp(){
        Admin mainAdmin = new Admin("user","pass");
        mainAdmin.addMainRole();
        User user = new User("user","pass","email","09123456789");
        Game game =new Game("R6","dups","fps-shooter",59.99,2,false,mainAdmin);
        assertEquals(user.isLevelValid(game),false);
        user.setLevel(4);
        assertEquals(user.isLevelValid(game),true);
    }

    public void testRoles(){
        Admin mainAdmin = new Admin("user","pass");
        mainAdmin.addSellerRole();
        mainAdmin.addMainRole();
        assertEquals(mainAdmin.isMainAdmin(),true);
        assertEquals(mainAdmin.isSeller() && mainAdmin.isDeveloper(),true);
    }

    public void testTeams(){
        Admin mainAdmin = new Admin("user","pass");
        mainAdmin.addMainRole();
        Game game =new Game("R6","dups","fps-shooter",59.99,1,false,mainAdmin);
        Admin notDevAdmin = new Admin("notDev","notDev");
        Admin devAdmin = new Admin("dev","dev");
        devAdmin.addDeveloperRole();
        game.addDev(notDevAdmin);
        game.addDev(devAdmin);
        assertEquals(game.isPartOfTeam(notDevAdmin),false);
        assertEquals(game.isPartOfTeam(devAdmin),true);
    }
}