package renderer.data;

import udp.update.server.ClientBulletFeature;

import renderer.data.entity.*;
import renderer.data.entity.Character;
import tcp.tcpClient.RealTcpClient;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public class DynamicObjectManager {
    private List<Character> characterList = new CopyOnWriteArrayList<>();
    private List<Item> itemList = new CopyOnWriteArrayList<>();
    private List<Entity> totalList = new CopyOnWriteArrayList<>();
    private List<Bullet> bulletList = new ArrayList<>();
    private List<Bullet> bulletTempList = new ArrayList<>();
    private static DynamicObjectManager instance = null;

    private DynamicObjectManager() {}

    public static DynamicObjectManager getInstance() {
        if (instance == null) {
            instance = new DynamicObjectManager();
        }
        return instance;
    }

    public void addVirtualCharacter(int clientno, String nickName) {
        assert clientno >= 0;
        Character c = new Character(clientno, nickName);
        this.characterList.add(c);
        this.totalList.add(c);
    }

    public void addItem(int itemType, int index, boolean isDead, int x, int y) {
        Item item = null;
        switch (itemType) {
            case 0:
                item = new FakeBox(itemType, index, isDead, x, y);
                break;
            case 1:
                item = new BloodPackage(itemType, index, isDead, x, y);
                break;
            case 2:
                item = new AmmoPackage(itemType, index, isDead, x, y);
                break;
            default:
                assert false;

        }
        totalList.add(item);
        this.itemList.add(item);
    }

    public void updateVirtualCharacter(int clientno, int weaponType,
            String nickname, int x, int y, double angle, int HP, int killCount,
            int deadCount, int bulletCount, boolean isAttack,
            boolean isAttacked, boolean isCollision, boolean isDead) {
        Character character = this.characterList.get(clientno);
        character.set(weaponType, nickname, x, y, angle, HP, killCount,
                deadCount, bulletCount, isAttack, isAttacked, isCollision,
                isDead);
    }

    public void updateItem(int index, boolean isDead, int owner, int x, int y) {

        Item item = this.itemList.get(index);
        item.update(isDead, owner, x, y);
    }

    synchronized public void updateBullets(List<ClientBulletFeature> originData){
        bulletTempList.clear();
        ArrayList<ClientBulletFeature> temp = new ArrayList<>(originData);
        for(ClientBulletFeature cbf : temp){
            bulletTempList.add(new Bullet(cbf.getLocX(),cbf.getLocY(),cbf.getFaceAngle()));
        }
    }

    synchronized public void setBullet(){
        bulletList = new ArrayList<>(bulletTempList);
    }

    public List<Entity> getAllDynamicObjects() {
        return totalList;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public List<Item> getItemList() {
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

    public List<Bullet> getBullets(){
        return bulletList;
    }

    public void keyGETPressed() {
        // TODO: unify this method with CDC
        /*
         * int localClientNo = 0; Character character = this.characterList.get(localClientNo);
         * 
         * for (Item item : this.itemList) { if (character.x == item.x && character.y == item.y) {
         * if (item.getShared()) { item.update(false, localClientNo);
         * //TCPClientModule.inputMoves("GET"); break; } } }
         */

    }

}
