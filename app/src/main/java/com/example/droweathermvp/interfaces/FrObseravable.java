package com.example.droweathermvp.interfaces;

//отельно будем применять для фрагментов и их презентеров
public interface FrObseravable {
    //регистрирует наблюдателя
    void setObserver(FrObserver observer);

//    //удаляет наблюдателя
//    void removeObserver(FrObserver observer);

    //При изменении данных вызывается метод notifyObservers, который в свою очередь вызывает метод update
    //у всех слушателей, передавая им обновлённые данные
    void notifyFrObserver();

}
