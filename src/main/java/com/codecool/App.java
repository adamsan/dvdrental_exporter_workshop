package com.codecool;

import com.codecool.dao.RowMapper;
import com.codecool.settings.DBPropertiesReader;
import com.codecool.settings.DataSourceCreator;
import com.codecool.settings.Settings;
import com.codecool.settings.SettingsParser;
import lombok.Setter;
import model.Customer;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class App {

    @Setter
    private DataSourceCreator dataSourceCreator = new DataSourceCreator();
    @Setter
    private DBPropertiesReader dbPropertiesReader = new DBPropertiesReader();

    public static void main(String[] args) throws ArgumentParserException {
        new App().run(args);
    }

    RowMapper<Customer> mapper = rs -> new Customer(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getString("zip code"),
            rs.getString("phone"),
            rs.getString("city"),
            rs.getString("country"),
            rs.getString("notes"),
            rs.getInt("sid")
    );

    private void run(String[] args) throws ArgumentParserException {
        Properties dbProperties = dbPropertiesReader.getProperties("db.properties");
        DataSource ds = dataSourceCreator.getDataSource(dbProperties);
        try (Connection con = ds.getConnection()) {
            Settings settings = SettingsParser.parseArgs(args);
            new App().processCustomers(con, settings);

        } catch (HelpScreenException ignored) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void processCustomers(Connection con, Settings settings) throws SQLException {
        final String columns = "id, name, address , \"zip code\", phone, city, country , notes, sid";
        PreparedStatement s = getPreparedStatement(con, settings, columns);
        List<Customer> result = runQueryForCustomers(s);
        if (settings.out == null) {
            System.out.println(toCSV(columns, result));
        } else {
            try (var w = new PrintWriter(settings.out)) {
                w.write(toCSV(columns, result));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private PreparedStatement getPreparedStatement(Connection con, Settings settings, String columns) throws SQLException {
        if (0 < settings.name.length() && 0 < settings.country.length()) { // name and country can't be null, because they default to "", see SettingsParser.
            PreparedStatement s = con.prepareStatement("select %s from customer_list where name like ? and country like ?".formatted(columns));
            s.setString(1, settings.name);
            s.setString(2, settings.country);
            return s;
        }
        if (0 < settings.name.length()) {
            PreparedStatement s = con.prepareStatement("select %s from customer_list where name like ?".formatted(columns));
            s.setString(1, settings.name);
            return s;
        }
        if (0 < settings.country.length()) {
            PreparedStatement s = con.prepareStatement("select %s from customer_list where country like ?".formatted(columns));
            s.setString(1, settings.country);
            return s;
        }
        return con.prepareStatement("select %s from customer_list".formatted(columns));
    }

    private String toCSV(String columns, List<Customer> customers) {
        String result = columns + "\n";
        result += customers.stream()
                .map(c -> "%d, %s,%s, %s,%s, %s,%s, %s, %d".formatted(c.id, c.name, c.address, c.zipCode, c.phone, c.city, c.country, c.notes, c.sid))
                .collect(Collectors.joining("\n"));
        return result;
    }

    private List<Customer> runQueryForCustomers(PreparedStatement s) throws SQLException {
        List<Customer> result = new ArrayList<>();
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
            Customer c = mapper.map(rs);
            result.add(c);
        }
        return result;
    }

}
