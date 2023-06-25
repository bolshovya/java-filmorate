package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Mpa {
    private int id;

    private String name;

    public Mpa() {
    }

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mpa mpa = (Mpa) o;
        return id == mpa.id && Objects.equals(name, mpa.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Mpa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
