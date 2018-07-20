package motoroi.bision;
import java.util.ArrayList;
public class Menu {
    public ArrayList<String> child;
    public String groupName;
    Menu(String name){
        groupName=name;
        child=new ArrayList<String>();
    }
}
