package mvc.models.entities.items;


public abstract class Item {
    private String name;
    private int count;
    private int id;

    public Item(String name, int count){
        this.name = name;
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public abstract String getCanonicalName();

    public abstract int getMaxCount();

    public void setId(int id){
        this.id = id;
    }

    public int filledInPercentage(){
        return (int) (((double) count / getMaxCount()) * 100);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + getName() + '\'' +
                ", count=" + count +
                '}';
    }

    public abstract String getDBName();

    public int getId() {
        return id;
    }
}
