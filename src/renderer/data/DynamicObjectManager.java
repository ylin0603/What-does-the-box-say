package renderer.data;

import renderer.data.entity.Character;
import renderer.data.entity.Entity;
import renderer.data.entity.Item;
import tcp.tcpClient.RealTcpClient;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DynamicObjectManager {
    private List<Character> characterList = Collections.synchronizedList(new ArrayList<Character>());
    private List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());

    private static DynamicObjectManager instance = null;

    private DynamicObjectManager() {
    }

    public static DynamicObjectManager getInstance() {
        if (instance == null) {
            instance = new DynamicObjectManager();
        }
        return instance;
    }

    public void addVirtualCharacter(int clientno, String nickName) {

        this.characterList.add(new Character(clientno,nickName));
    }

    public void addItem(int itemType, int index, boolean isDead, int x, int y) {
        this.itemList.add(new Item(itemType,index,isDead,x,y));
    }

    public void updateVirtualCharacter(int clientno, int weaponType, String nickname,
                                       int x, int y, double angle, int HP, int killCount,
                                       int deadCount, int bulletCount, boolean isAttack,
                                       boolean isAttacked, boolean isCollision,
                                       boolean isDead) {
        Character character = this.characterList.get(clientno);
        character.update(weaponType, nickname, x, y, angle, HP, killCount, deadCount,
                bulletCount, isAttack, isAttacked, isCollision, isDead);
    }

    public void updateItem(int index, boolean isDead, int owner) {

        Item item = this.itemList.get(index);
        item.update(isDead, owner);
    }

    public List<Entity> getAllDynamicObjects() {
        List<Entity> joinList = Stream.concat(characterList.stream(),
                itemList.stream()).collect(Collectors.toList());
        return joinList;
    }
    
    public List<Character> getCharacterList() {
        return characterList;
    }
    
    public List<Item> getItemList() {
        return itemList;
    }

    public Point getVirtualCharacterXY() {
        if (characterList.size() == 0) return new Point(0, 0);
        int localClientNo = RealTcpClient.getInstance().getClientNo();
        Character character = this.characterList.get(localClientNo);
        return new Point(character.x, character.y);
    }
    
    public Character getLocalCharacter() {
        assert characterList.size() > 0;
        int localClientNo = RealTcpClient.getInstance().getClientNo();
        return this.characterList.get(localClientNo);
    }

    public void keyGETPressed() {
        //TODO: unify this method with CDC
        /*int localClientNo = 0;
        Character character = this.characterList.get(localClientNo);

        for (Item item : this.itemList) {
            if (character.x == item.x && character.y == item.y) {
                if (item.getShared()) {
                    item.update(false, localClientNo);
                    //TCPClientModule.inputMoves("GET");
                    break;
                }
            }
        }*/

    }

}
