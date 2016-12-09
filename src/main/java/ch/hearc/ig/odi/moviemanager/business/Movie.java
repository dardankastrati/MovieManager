
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

    /**
     * Constructeur vide
     */
    public Movie() {
    }

    /**
     * Constructeur avec l'ID, le nom et le producteur du film
     * @param id ID
     * @param name Nom
     * @param producer Producteur 
     */
    public Movie(Long id, String name, String producer) {
        this.id = id;
        this.name = name;
        this.producer = producer;
    }

    /**
     * Getter retourne l'ID du film
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter onindique l'ID de la personne
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter retourne le nom du film
     * @return Nom
     */
    public String getName() {
        return name;
    }

    /**
     * On indique le nom duf ilm
     * @param name Nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter retourne le producteur du film
     * @return Producteur
     */
    public String getProducer() {
        return producer;
    }

    /**
     * Setter on indique le producteur du film
     * @param producer Producteur
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * Retourne la liste des personnes du film
     * @return Liste de personnes
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * Setter on indique la liste des personnes du film
     * @param people Liste des personnes
     */
    public void setPeople(List<Person> people) {
        this.people = people;
    }
    
    
}
