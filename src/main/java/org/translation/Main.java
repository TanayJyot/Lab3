package org.translation;

import java.io.StringBufferInputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    private static final String QUIT = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        Translator translator = new JSONTranslator();
        //        Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
        while (true) {
            String country = promptForCountry(translator);
            if (QUIT.equals(country)) {
                break;
            }
            // TODO Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            String language = promptForLanguage(translator, countryCodeConverter.fromCountry(country));
            if (QUIT.equals(language)) {
                break;
            }
            // TODO Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            System.out.println(country + " in " + language + " is " + translator
                    .translate(countryCodeConverter.fromCountry(country), languageCodeConverter.fromLanguage(language)));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        // TODO Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // TODO Task: convert the country codes to the actual country names before sorting
        ArrayList<String> countryNameList = new ArrayList<String>();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        StringBuilder countryBuilder = new StringBuilder();
        for (String countryName: countries) {
            countryNameList.add(countryCodeConverter.fromCountryCode(countryName));
        }
        Collections.sort(countryNameList);
        for (String country: countryNameList) {
            countryBuilder.append(country);
            countryBuilder.append("\n");
        }
        System.out.println(countryBuilder);
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        // TODO Task: replace the line below so that we sort the languages alphabetically and print them out;
        //  one per line
        // TODO Task: convert the language codes to the actual language names before sorting
        List<String> languages = translator.getCountryLanguages(country);
        ArrayList<String> languageNameList = new ArrayList<String>();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
        StringBuilder languageBuilder = new StringBuilder();
        for (String language: languages) {
            languageNameList.add(languageCodeConverter.fromLanguageCode(language));
        }
        Collections.sort(languageNameList);
        for (String lang: languageNameList) {
            languageBuilder.append(lang);
            languageBuilder.append("\n");
        }
        System.out.println(languageBuilder);
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
