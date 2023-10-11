// Każde ograniczenie składa się z dwóch elementów:
//
//adnotacji definiującej ograniczenie,
//implementacji ograniczenia, czyli algorytmu weryfikującego jego poprawność.
//
//Adnotacja ograniczenia musi zawierać co najmniej trzy elementy:
//message - komunikat błędu, który może być użyty, gdy ograniczenie zostanie złamane.
//groups - grupy, dla których ograniczenie będzie aktywne. Grupy pozwalają określić kolejność,
// w jakiej ograniczenia będą weryfikowane, albo poddać walidacji tylko część pól obiektu.
//payload - dodatkowe informacje, które możemy przypisać do ograniczenia podczas jego definicji. Raczej rzadko używane.

//Oprócz tego w ramach adnotacji ograniczenia możemy dodawać dowolne elementy.
// Jedynym wymogiem jest to, żeby ich nazwy nie rozpoczynały się od słowa "valid".
// Często spotkasz się z wykorzystaniem elementu o nazwie value, który pozwala przekazać dodatkowe informacje
// do ograniczenia. Przykładowo ograniczenie typu @Min(5) ma element value ustawiony na 5.
package com.create.MyOwnValidators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Adnotacja @Documented jest opcjonalna, ale dzięki niej w dokumentacji pojawi się odnośnik do adnotacji,
// a nie zwykły tekst. W adnotacji @Constraint możemy wskazać walidator dla danego ograniczenia, czyli klasę,
// w której znajduje się algorytm weryfikujący poprawność pola, lub parametru, które będzie oznaczony
// adnotacją @CustomConstraint. W powyższym przykładzie będzie to klasa CustomValidator, którą należy zdefiniować.
// W adnotacji @Target wskazujemy, do których elementów można używać danej adnotacji.
// Walidacji można poddawać nie tylko pola klasy (FIELD), ale także parametry konstruktorów i metod (PARAMETER).
// Oprócz wartości FIELD i PARAMETER możemy także wykorzystać:
//
//METHOD - walidacja wszystkich parametrów metody i jej wyniku,
//CONSTRUCTOR - walidacja wszystkich parametrów konstruktora i tworzonego przez niego obiektu,
//ANNOTATION_TYPE - definiowanie adnotacji zbudowanych jako kompozycja kilku innych ograniczeń,
//TYPE - walidacji nie podlega składowa klasy/obiektu, tylko cały obiekt,
//TYPE_USE - walidacja typów generycznych.
//W większości przypadków będziemy weryfikowali poprawność pól obiektu, albo parametry metod i konstruktorów,
// dlatego do adnotacji @Target będziemy przekazywali przede wszystkim wartości @FIELD i @PARAMETER.
//
//Ostatnia adnotacja @Retention(RUNTIME) wskazuje, że adnotacja @CustomConstraint będzie zachowana
// po fazie kompilacji i będzie mogła być wykorzystana w fazie wykonania przez mechanizm refleksji.
// Brzmi to może skomplikowanie, ale chodzi o to, że część adnotacji jest w Javie potrzebna tylko
// na etapie pisania kodu (np. @Override) i nie musi być dostępna w fazie działania programu.

@Documented
@Constraint(validatedBy = NotBadWordValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface NotBadWord {

    String message() default "Contains bad words";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Lang[] lang() default Lang.PL;
}


