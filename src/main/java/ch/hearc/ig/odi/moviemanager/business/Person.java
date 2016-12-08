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

    public Person() {
    }

    public Person(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        movies = new ArrayList<Movie>();
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
    }
}
