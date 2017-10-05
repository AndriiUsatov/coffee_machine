package dao.impl;

import connection.ConnectionPool;
import dao.ItemDAO;
import entities.Fill;
import entities.items.Item;
import entities.items.impl.Stick;
import entities.items.impl.BigCup;
import entities.items.impl.LittleCup;
import entities.items.impl.MiddleCup;
import entities.CoffeeMachine;
import entities.users.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemDAOImpl implements ItemDAO {
    private static Properties queries = new Properties();
    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class);
    private static CoffeeMachine coffeeMachine = CoffeeMachine.getCoffeeMachineInstance();
    private static ItemDAOImpl itemDAOInstance;

    private ItemDAOImpl() {
        try {
            queries.load(getClass().getResourceAsStream("/queries.properties"));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    public static ItemDAOImpl getItemDAOInstance() {
        if (itemDAOInstance == null) {
            synchronized (ItemDAOImpl.class) {
                if (itemDAOInstance == null)
                    itemDAOInstance = new ItemDAOImpl();
            }
        }
        return itemDAOInstance;
    }

    @Override
    public synchronized Item[] getAllItems() {
        List<Item> list = new ArrayList<Item>(){
            {
                add(new Stick());
                add(new LittleCup());
                add(new MiddleCup());
                add(new BigCup());
            }
        };
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(queries.getProperty("coffee_machine.get_all_item"));
            while (resultSet.next()) {
                String itemName = resultSet.getString("name");
                for(Item item : list){
                    if(itemName.equals(item.getDBName()))
                        item.setCount(resultSet.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return list.toArray(new Item[list.size()]);
    }

    public synchronized Item getItemByName(String name) {
        Item item = null;
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queries.getProperty("coffee_machine.get_item"))) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("quantity");
                switch (name) {
                    case "sticks":
                        item = new Stick(count);
                        break;
                    case "little_cups":
                        item = new LittleCup(count);
                        break;
                    case "middle_cups":
                        item = new MiddleCup(count);
                        break;
                    case "big_cups":
                        item = new BigCup(count);
                        break;
                }
                item.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void noteItemUpdate(String itemName, int updateCount, User admin) {
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queries.getProperty("coffee_machine.add_item_fills"))) {
            Item item = getItemByName(itemName);
            preparedStatement.setInt(1, updateCount);
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, item.getId());
            preparedStatement.setLong(4, admin.getId());
            preparedStatement.setInt(5, coffeeMachine.getMachineId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean updateItem(String itemName, int count) {
        boolean result = true;
        if(itemName == null || itemName.equals(""))
            return false;
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queries.getProperty("coffee_machine.update_item"))) {
            Item item = getItemByName(itemName);
                preparedStatement.setInt(1, count);
                preparedStatement.setString(2, itemName);
                preparedStatement.executeUpdate();
            if (item.getCount() < count) {
                logger.log(Level.INFO, "Added " + (count - item.getCount()) +
                        " number of " + itemName.toUpperCase() + " into Coffee machine");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception", e);
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public Fill[] getItemFills() {
        List<Fill> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnector().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(queries.getProperty("coffee_machine.get_all_item_fills"));
            while (resultSet.next()){
                list.add(new Fill(resultSet.getString("name"),resultSet.getInt("quantity"),resultSet.getString("login"),resultSet.getDate("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Fill[list.size()]);
    }
}
