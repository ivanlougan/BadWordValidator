package com.create.MyOwnValidators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.Arrays;


// Drugi krok przy definicji ograniczenia to stworzenie walidatora,
// który będzie w stanie sprawdzić poprawność elementu oznaczonego adnotacją ograniczenia.
// Walidator powinien implementować interfejs ConstraintValidator, który posiada dwie metody:
//
//isValid() - jest to metoda abstrakcyjna, więc musimy ją zaimplementować.
// W niej znajdzie się logika weryfikująca poprawność danych,
//initialize() - opcjonalna metoda, w której możemy np. pobrać wartości przekazane do adnotacji ograniczenia.

//Interfejs ConstraintValidator jest generyczny i należy do niego przekazać dwa argumenty.
// Pierwszym będzie adnotacja ograniczenia, a drugim typ obiektów, które będą tą adnotacją oznaczone.
// Poniższy walidator służy do walidacji pól obiektu, które są typu Integer
// i zostały oznaczone adnotacją @CustomConstraint.


public class NotBadWordValidator implements ConstraintValidator<NotBadWord, String> {


    private Lang[] languages;

    // Klasa walidatora jest mocno uproszczona w stosunku do tego jak powinna wyglądać w rzeczywistości,
    // ale oddaje ogólną ideę, o którą chodzi. Implementując interfejs ConstraintValidator<NotBadWord, String>
    // wskazujemy, że walidator ten powinien być wykorzystany do weryfikacji pól typu String
    // oznaczonych ograniczeniem @NotBadWord.
    //
    //Metoda containsBadWord() sprawdza, czy text zawiera jakiekolwiek ze słów zdefiniowanych w tablicy badWords.
    // Przed sprawdzeniem tekstu zamieniam go na małe litery. Drugą opcją byłoby wykorzystanie tutaj
    // metody equalsIgnoreCase() klasy String.
    //
    //Metoda initialize() została wykorzystana do tego, żeby wczytać języki, dla których ma być wykonana walidacja.
    // Jeżeli sprawdzane będzie pole oznaczone adnotacją @NotBadWord(lang = {Lang.PL, Lang.EN}),
    // to tablica languages będzie zawierała dwie wartości PL i EN, natomiast jeżeli użyjemy adnotacji
    // @NotBadWord(lang = Lang.EN), to tablica languages będzie zawierała tylko jedną wartość, czyli EN.
    //
    //Tablica languages jest używana w metodzie isValid(). Do parametru value będzie przypisana wartość pola String,
    // nad którym użyjemy adnotacji @NotBadWord. Będzie to więc tekst, w którym będziemy szukali przekleństw.
    // Jeżeli w tekście znajduje się chociaż jedno zakazane słowo, to metoda isValid() zwróci false, wskazując,
    // że ograniczenie zostało złamane.

    @Override
    public void initialize(NotBadWord annotation) {
        this.languages = annotation.lang();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (Lang lang : languages) {
            if (containsBadWord(value, lang.getBadWords())) {
                return false;
            }
        }
        return true;
    }

    private boolean containsBadWord(String text, String[] badWords) {
        String lowerCase = text.toLowerCase();
        return Arrays.stream(badWords)
                .anyMatch(lowerCase::contains);
    }
}
