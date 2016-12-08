
package ch.hearc.ig.odi.moviemanager.business;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author dardan.kastrati
 */
public class Movie implements Serializable {

    private Long id;
    private String name;
    private String producer;
    private List<Person> people;

    public Movie() {
    }

    public Movie(Long id, String name, String producer) {
        this.id = id;
        this.name = name;
        this.producer = producer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
    
    
}
