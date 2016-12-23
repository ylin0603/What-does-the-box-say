package renderer.data;

import renderer.data.entity.*;
import renderer.data.entity.Character;
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
        assert clientno >=0;
        this.characterList.add(new Character(clientno,nickName));
    }

    public void addItem(int itemType, int index, boolean isDead, int x, int y) {
        Item item = null;
        switch (itemType){
            case 0:
                item = new FakeBox(itemType,index,isDead,x,y);
                break;
            case 1:
                item = new BloodPackage(itemType,index,isDead,x,y);
                break;
            case 2:
                item = new AmmoPackage(itemType,index,isDead,x,y);
                break;
            default:
                assert false;

        }
        this.itemList.add(item);
    }

    public void updateVirtualCharacter(int clientno, int weaponType, String nickname,
                                       int x, int y, double angle, int HP, int killCount,
                                       int deadCount, int bulletCount, boolean isAttack,
                                       boolean isAttacked, boolean isCollision,
                                       boolean isDead) {
        Character character = this.characterList.get(clientno);
        character.set(weaponType, nickname, x, y, angle, HP, killCount, deadCount,
                bulletCount, isAttack, isAttacked, isCollision, isDead);
    }

    public void updateItem(int index, boolean isDead, int owner, int x, int y) {

        Item item = this.itemList.get(index);
        item.update(isDead, owner, x, y);
    }

    public List<Entity> getAllDynamicObjects() {
        List<Entity> joinList = Stream.concat(characterList.stream(),
                itemList.stream()).collect(Collectors.toList());
        return joinList;
    }
    
    public List<Character> getCharacterList() {
        assert characterList.size() > 0;
        return characterList;
    }
    
    public List<Item> getItemList() {
        assert itemList.size() > 0;
        return itemList;
    }

    public Point getVirtualCharacterXY() {
        assert characterList.size() > 0;
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
