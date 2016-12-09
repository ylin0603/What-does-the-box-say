package renderer.data;

import renderer.data.entity.Character;
import renderer.data.entity.Entity;
import renderer.data.entity.Item;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DynamicObjectManager {
    public List<Character> characterList = Collections.synchronizedList(new ArrayList<Character>());
    public List<Item> itemList = Collections.synchronizedList(new ArrayList<Item>());

    private static DynamicObjectManager instance = null;

    private DynamicObjectManager() {
    }

    public static DynamicObjectManager getInstance() {
        if (instance == null) {
            instance = new DynamicObjectManager();
        }
        return instance;
    }

    public void addVirtualCharacter(int clientno) {

        this.characterList.add(new Character(clientno));
    }

    public void addItem(String name, int index, Boolean shared, int x, int y) {
        this.itemList.add(new Item(name, index, shared, x, y));
    }

    public void updateVirtualCharacter(int clientno, double dir, int speed, int x, int y) {
        Character character = this.characterList.get(clientno);
        character.update(dir, speed, x, y);
    }

    public void updateItem(int index, Boolean shared, int owner) {

        Item item = this.itemList.get(index);
        item.update(shared, owner);
    }

    public List<Entity> getAllDynamicObjects() {
        List<Entity> joinList = Stream.concat(characterList.stream(),
                itemList.stream()).collect(Collectors.toList());
        return joinList;
    }

    public Point getVirtualCharacterXY() {
        int localClientNo = 0; //TODO: localClientNo should get from TCP?
        Character character = this.characterList.get(localClientNo);
        return new Point(character.x, character.y);
    }

    public void keyGETPressed() {
        int localClientNo = 0;
        Character character = this.characterList.get(localClientNo);

        for (Item item : this.itemList) {
            if (character.x == item.x && character.y == item.y) {
                if (item.getShared()) {
                    item.update(false, localClientNo);
                    //TCPClientModule.inputMoves("GET");
                    break;
                }
            }
        }

    }

}
