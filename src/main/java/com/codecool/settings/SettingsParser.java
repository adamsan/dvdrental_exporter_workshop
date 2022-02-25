package com.codecool.settings;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class SettingsParser {
    public static Settings parseArgs(String[] args) throws ArgumentParserException {
        ArgumentParser parser = ArgumentParsers.newFor("dvd_rental_customers").build()
                .description("Filters and outputs the dvdrental databases customer_list view as CSV. See task description...");
        parser.addArgument("--name").dest("name").setDefault("").help("Filter first or last names with this option.").required(false);
        parser.addArgument("--country").dest("country").setDefault("").help("Filter customers based on country.").required(false);
        parser.addArgument("--out").dest("out").required(false);
        Namespace n = parser.parseArgs(args);
        return new Settings(n.getString("name"), n.getString("country"), n.getString("out"));
    }
}
