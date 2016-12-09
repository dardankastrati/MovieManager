package ch.hearc.ig.odi.moviemanager.business;

import ch.hearc.ig.odi.moviemanager.exception.NullParameterException;
import ch.hearc.ig.odi.moviemanager.exception.UniqueException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dardan.kastrati
 */
public class Person implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Movie> movies;

    /**
     * Constructeur vide
     */
    public Person() {
    }

    /**
     * Constructeur avec l'ID, le prénom et le nom
     * @param id ID de la perosnne
     * @param firstName Prénom de la personne
     * @param lastName Nom de la personne
     */
    public Person(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        movies = new ArrayList<Movie>();
    }

    /**
     * On ajoute un film à la personne
     * @param movie Film
     * @throws UniqueException
     * @throws NullParameterException 
     */
    public void addMovie(Movie movie) throws UniqueException, NullParameterException {

        int index = movies.indexOf(movie);

        if (movies == null) {
            movies = new ArrayList<Movie>();
        }

        if (movie == null) {
            throw new NullParameterException("Le paramètre est null");
        }

        if (movies.size() > 1) {
            if (index != -1) {
                if (movies.get(index) == null) {
                    movies.add(movie);
                } else {
                    throw new UniqueException("Le film existe déjà dans la liste");
                }
            }
        } else {
            movies.add(movie);
        }
    }

    /**
     * Getter retourne l'ID de la personne
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * On indique l'ID de la personne
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter retourne le prénom de la personne
     * @return Prénom
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * On indique le prénom de la personne
     * @param firstName Prénom
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter retourne le nom de la personne
     * @return Nom
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * On indique le nom de la personne
     * @param lastName Nom
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter retourne la liste des films de la personne
     * @return Liste des films
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * On indique la liste de films de la personne
     * @param movies Liste de films
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Efface le film de la personne
     * @param movie Film
     */
    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
    }
}
